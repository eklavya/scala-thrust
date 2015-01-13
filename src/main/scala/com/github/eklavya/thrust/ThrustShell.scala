package com.github.eklavya.thrust

import argonaut.Argonaut._
import com.github.eklavya.thrust.Replies.{EventReply, Reply}
import com.typesafe.config.ConfigFactory


case class MessageId(id: Int) extends AnyVal

private object ThrustShell {

  val process = {
    val config = ConfigFactory.load()
    val pb = new ProcessBuilder(config.getString("thrust_path"))
    pb.redirectErrorStream(true)
    pb.start
  }

  def cleanup = process.destroy()

  val in = io.Source.fromInputStream(process.getInputStream).bufferedReader()

  val out = process.getOutputStream

  {
    new Thread(new Runnable {
      def run(): Unit = {
        var continue = true
        while (continue) {
          Option(in.readLine()).map { reply =>
//            println(reply)
            if (reply.contains("reply")) {
              val r = reply.decode[Reply].toOption.get
              if (r.method.isDefined)
                handleRemoteMethods(r)
              else
                handleReply(r)
            } else if (reply.contains("event")) {
              handleEvent(reply)
            }
          }.getOrElse {
            continue = false
          }
        }
      }
    }).start()
  }

  def handleReply(r: Reply) = {
    val res = r.result
    val l = List(res.closed, res.devToolsOpened, res.fullScreen, res.kiosk, res.maximize, res.minimized).flatten
    res.target.foreach(x => MessageBox.getPromise(r.id).success(x.id))
    l.headOption.foreach(h => MessageBox.getPromise(r.id).success(h))
    res.position.foreach(p => MessageBox.getPromise(r.id).success(p))
    res.size.foreach(s => MessageBox.getPromise(r.id).success(s))
  }

  def handleEvent(event: String) = {
    event.decode[EventReply].toOption.map { e =>
      Events.callback(e.target, Events.fromString(e._type), e.ef)
    }
  }

  def handleRemoteMethods(r: Reply) = {
    val method = r.method
  }
}