package com.eklavya.thrust

import argonaut._,Argonaut._
import com.eklavya.thrust.JsonUtils.Reply

object JsonUtils {

  case class Result(target: Option[Int])

  implicit def jsonToResult: DecodeJson[Result] = {
    DecodeJson(r => for {
      target <- (r --\ "_target").as[Option[Int]]
    } yield Result(target))
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
            reply.decode[Reply]
          }.getOrElse {
            continue = false
          }
        }
      }
    }).start()
  }
}