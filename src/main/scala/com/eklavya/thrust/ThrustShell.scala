package com.eklavya.thrust

import argonaut._,Argonaut._
import com.eklavya.thrust.JsonUtils.{Event, Reply}

object JsonUtils {

  case class Result(target: Option[Int],
                    size: Option[Size],
                    position: Option[Position],
                    maximize: Option[Boolean],
                     kiosk: Option[Boolean],
                     minimized: Option[Boolean],
                     fullScreen: Option[Boolean],
                     devToolsOpened: Option[Boolean],
                     closed: Option[Boolean])

  implicit def jsonToResult: DecodeJson[Result] = {
    DecodeJson(r => for {
      target <- (r --\ "_target").as[Option[Int]]
      size <- (r --\ "size").as[Option[Size]]
      position <- (r --\ "position").as[Option[Position]]
      maximize <- (r --\ "maximize").as[Option[Boolean]]
      kiosk <- (r --\ "kiosk").as[Option[Boolean]]
      minimized <- (r --\ "minimized").as[Option[Boolean]]
      fullscreen <- (r --\ "fullscreen").as[Option[Boolean]]
      devToolsOpened <- (r --\ "devToolsOpened").as[Option[Boolean]]
      closed <- (r --\ "closed").as[Option[Boolean]]
    } yield Result(target, size, position, maximize, kiosk, minimized, fullscreen, devToolsOpened, closed))
  }

  case class Reply(action: String, error: Option[String], id: Int, result: Result)

  implicit def jsonToReply: DecodeJson[Reply] = {
    DecodeJson(r => for {
      action <- (r --\ "_action").as[String]
      error <- (r --\ "_error").as[Option[String]]
      id <- (r --\ "_id").as[Int]
      result <- (r --\ "_result").as[Result]
    } yield Reply(action, error, id, result))
  }
//"_action":"event","_event":{},"_id":1,"_target":1,"_type":"focus"
  case class Event(action: String, event: Option[String], id: Int, target: Int, _type: String)

  implicit def jsonToEvent: DecodeJson[Event] = {
    DecodeJson(e => for {
      action <- (e --\ "_action").as[String]
      event <- (e --\ "_event").as[Option[String]]
      id <- (e --\ "_id").as[Int]
      target <- (e --\ "_target").as[Int]
      _type <- (e --\ "_type").as[String]
    } yield Event(action, event, id, target, _type))
  }
}



object ThrustShell {

  val process = {
    val pb = new ProcessBuilder("/Users/eklavya/Documents/thrust/out/Debug/ThrustShell.app/Contents/MacOS/ThrustShell")
    pb.redirectErrorStream(true)
    pb.start
  }

  val in = io.Source.fromInputStream(process.getInputStream).bufferedReader

  val out = process.getOutputStream

  {
    new Thread(new Runnable {
      def run: Unit = {
        var continue = true
        while (continue) {
          Option(in.readLine()).map { reply =>
            if (reply.contains("reply")) {
                val r = reply.decode[Reply].toOption.get
                val l = List(r.result.closed, r.result.devToolsOpened, r.result.fullScreen, r.result.kiosk, r.result.maximize, r.result.minimized).flatten
                l.headOption.map(MessageBox.getBooleanPromise(r.id).success)
                if (r.result.position.isDefined) r.result.position.map(MessageBox.getPositionPromise(r.id).success)
                if (r.result.size.isDefined) r.result.size.map(MessageBox.getSizePromise(r.id).success)
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
}