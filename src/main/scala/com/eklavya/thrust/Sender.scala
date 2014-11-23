package com.eklavya.thrust

import java.util.concurrent.atomic.AtomicInteger

import argonaut.Argonaut._
import com.eklavya.thrust.Actions._
import com.eklavya.thrust.Arguments._
import com.eklavya.thrust.Methods._

object Sender {
  var nextId = new AtomicInteger(1)
  val boundary = "\n--(Foo)++__THRUST_SHELL_BOUNDARY__++(Bar)--\n"

  def sendCommand(action: Action,
                  method: Method,
                  _type: Option[String],
                  target: Option[WinId],
                  args: List[Argument]): MessageId = {
    val id = nextId.getAndIncrement
    val jsonCommand = ("_args" -> args.foldRight(jEmptyObject)(_ ->: _)) ->: ("_target" :=? target.map(_.id)) ->?: ("_type" :=? _type) ->?: method ->: action ->: ("_id" -> jNumber(id)) ->: jEmptyObject
    ThrustShell.out.write((jsonCommand.toString() + boundary).getBytes())
    ThrustShell.out.flush()
    MessageId(id)
  }
}
