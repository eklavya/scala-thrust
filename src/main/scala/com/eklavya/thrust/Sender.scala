package com.eklavya.thrust

import java.util.concurrent.atomic.AtomicInteger

import Arguments._
import Actions._
import Methods._
import argonaut._,Argonaut._

object Sender {
  var nextId = new AtomicInteger(1)

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