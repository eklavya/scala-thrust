package com.github.eklavya.thrust

import java.util.concurrent.atomic.AtomicInteger

import argonaut.Argonaut._
import com.github.eklavya.thrust.Actions._
import com.github.eklavya.thrust.Arguments._
import com.github.eklavya.thrust.Methods._

sealed trait TargetId extends Any {
  val id: Int
}

case class WinId(id: Int) extends AnyVal with TargetId

case class MenuId(id: Int) extends AnyVal with TargetId

case class SessionId(id: Int) extends AnyVal with TargetId

private object Sender {
  val boundary = "\n--(Foo)++__THRUST_SHELL_BOUNDARY__++(Bar)--\n"
  var nextId = new AtomicInteger(1)

  def sendCommand(action: Action,
                  method: Method,
                  _type: Option[String],
                  target: Option[TargetId],
                  args: List[Argument]): MessageId = {
    val id = nextId.getAndIncrement
    val jsonCommand = ("_args" -> args.foldRight(jEmptyObject)(_ ->: _)) ->: ("_target" :=? target.map(_.id)) ->?: ("_type" :=? _type) ->?: method ->: action ->: ("_id" -> jNumber(id)) ->: jEmptyObject
    ThrustShell.out.write((jsonCommand.toString() + boundary).getBytes())
    ThrustShell.out.flush()
    MessageId(id)
  }
}
