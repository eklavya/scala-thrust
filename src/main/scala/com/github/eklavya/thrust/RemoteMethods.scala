package com.github.eklavya.thrust

import com.github.eklavya.thrust.Replies.Args

import scala.collection.concurrent.TrieMap
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by eklavya on 12/13/14.
 */

object RemoteMethods {

  val handlersMap = TrieMap.empty[(Int, RemoteMethod), Function1[Args, Unit]]

  def fromString(s: String): RemoteMethod = {
    s match {
      case "cookies_load" => COOKIES_LOAD
      case "cookies_load_for_key" => COOKIES_LOAD_FOR_KEY
      case "cookies_flush" => COOKIES_FLUSH
      case "cookies_add" => COOKIES_ADD
      case "cookies_update_access_time" => COOKIES_UPDATE_ACCESS_TIME
      case "cookies_delete" => COOKIES_DELETE
      case "cookies_force_keep_session_state" => COOKIES_FORCE_KEEP_SESSION_STATE
    }
  }

  def callback(id: Int, rm: RemoteMethod, args: Args): Unit = {
    Future {
      handlersMap.get((id, rm)).foreach(_(args))
    }
  }

  def setCallback(id: TargetId, rm: RemoteMethod, f: Function1[Args, Unit]): Unit = {
    handlersMap.update((id.id, rm), f)
  }

  sealed abstract class RemoteMethod

  case object COOKIES_LOAD extends RemoteMethod

  case object COOKIES_LOAD_FOR_KEY extends RemoteMethod

  case object COOKIES_FLUSH extends RemoteMethod

  case object COOKIES_ADD extends RemoteMethod

  case object COOKIES_UPDATE_ACCESS_TIME extends RemoteMethod

  case object COOKIES_DELETE extends RemoteMethod

  case object COOKIES_FORCE_KEEP_SESSION_STATE extends RemoteMethod
}
