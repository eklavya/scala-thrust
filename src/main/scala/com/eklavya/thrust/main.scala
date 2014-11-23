package com.eklavya.thrust

import com.eklavya.thrust.Arguments.Size

import scala.concurrent.ExecutionContext.Implicits.global

object Main extends App {
  Window.create("http://pramati.com").foreach { w =>
    w.show
    w.maximize
    w.openDevtools
    w.focus(true)
  }
  while (true) {}
}