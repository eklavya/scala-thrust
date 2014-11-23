package com.eklavya.thrust

import argonaut.Argonaut._
import argonaut._
import com.eklavya.thrust.Arguments.{Position, Size}
import com.eklavya.thrust.Replies.{Event, Reply}
import com.typesafe.config.ConfigFactory

case class MessageId(id: Int) extends AnyVal

object ThrustShell {

  val process = {
    val config = ConfigFactory.load()
    val pb = new ProcessBuilder(config.getString("thrust_path"))
    pb.redirectErrorStream(true)
    pb.start
  }

  val in = io.Source.fromInputStream(process.getInputStream).bufferedReader()

  val out = process.getOutputStream

  {
    new Thread(new Runnable {
      def run(): Unit = {
        var continue = true
        while (continue) {
          Option(in.readLine()).map { reply =>
            if (reply.contains("reply")) {
              handleReply(reply)
            } else if (reply.contains("event")) {
              reply.decode[Event]
            }
          }.getOrElse {
            continue = false
          }
        }
      }
    }).start()
  }

  def handleReply(reply: String) = {
    val r = reply.decode[Reply].toOption.get
    val res = r.result
    val l = List(res.closed, res.devToolsOpened, res.fullScreen, res.kiosk, res.maximize, res.minimized).flatten
    res.target.foreach(x => MessageBox.getPromise(r.id).success(x.id))
    l.headOption.foreach(h => MessageBox.getPromise(r.id).success(h))
    res.position.foreach(p => MessageBox.getPromise(r.id).success(p))
    res.size.foreach(s => MessageBox.getPromise(r.id).success(s))
  }

  def handleEvent(event: String) = {
    val e = event.decode[Event].toOption.get

  }
}