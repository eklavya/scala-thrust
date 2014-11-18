package com.eklavya.thrust

object Sender {
  var nextId = 1

  def sendCommand(action: Action,
                  method: Method,
                  target: Option[Int],
                  args: List[Argument]): Unit = {

  }
}

case class Command(action: Action,
                   method: Method,
                   target: Option[Int],
                   args: String)