package com.eklavya.thrust

import com.eklavya.thrust.Replies.EventFields

import scala.collection.concurrent.TrieMap
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by eklavya on 11/23/14.
 */
private object Events {

  val callbackMap = TrieMap.empty[(Int, Event), Function1[EventFields, Unit]]

  def fromString(s: String): Event = {
    s match {
      case "blur" => BLURRED
      case "focus" => FOCUSED
      case "closed" => CLOSED
      case "unresponsive" => UNRESPONSIVE
      case "responsive" => RESPONSIVE
      case "worker_crashed" => WORKER_CRASHED
      case "execute" => EXECUTE
    }
  }

  def callback(id: Int, e: Event, ef: EventFields): Unit = {
    Future {
      callbackMap((id, e))(ef)
    }
  }

  def setCallback(id: TargetId, e: Event, f: Function1[EventFields, Unit]): Unit = {
    callbackMap.update((id.id, e), f)
  }

  def removeForWindow(id: WinId): Unit = {
    List(BLURRED, FOCUSED, CLOSED, UNRESPONSIVE, RESPONSIVE, WORKER_CRASHED) foreach { e =>
      callbackMap.remove((id.id, e))
    }
  }

  sealed abstract class Event

  case object BLURRED extends Event

  case object FOCUSED extends Event

  case object CLOSED extends Event

  case object UNRESPONSIVE extends Event

  case object RESPONSIVE extends Event

  case object WORKER_CRASHED extends Event

  case object EXECUTE extends Event
}
