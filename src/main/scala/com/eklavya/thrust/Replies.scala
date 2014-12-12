package com.eklavya.thrust

import argonaut.Argonaut._
import argonaut._
import com.eklavya.thrust.Arguments.{Position, Size}

/**
 * Created by eklavya on 11/22/14.
 */
private object Replies {
  case class Result(target: Option[WinId],
                    size: Option[Size],
                    position: Option[Position],
                    maximize: Option[Boolean],
                    kiosk: Option[Boolean],
                    minimized: Option[Boolean],
                    fullScreen: Option[Boolean],
                    devToolsOpened: Option[Boolean],
                    closed: Option[Boolean])

  implicit def jsonToSize: DecodeJson[Size] = {
    DecodeJson(s => for {
      width <- (s --\ "width").as[Int]
      height <- (s --\ "height").as[Int]
    } yield Size(width, height))
  }

  implicit def jsonToPosition: DecodeJson[Position] = {
    DecodeJson(p => for {
      x <- (p --\ "x").as[Int]
      y <- (p --\ "y").as[Int]
    } yield Position(x, y))
  }

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
    } yield Result(target.map(WinId), size, position, maximize, kiosk, minimized, fullscreen, devToolsOpened, closed))
  }

  case class Reply(action: String, error: Option[String], id: MessageId, result: Result)

  implicit def jsonToReply: DecodeJson[Reply] = {
    DecodeJson(r => for {
      action <- (r --\ "_action").as[String]
      error <- (r --\ "_error").as[Option[String]]
      id <- (r --\ "_id").as[Int]
      result <- (r --\ "_result").as[Result]
    } yield Reply(action, error, MessageId(id), result))
  }


  //_event":{"command_id":1,"event_flags":16},"
  case class EventFields(commandId: Option[Int], eventFlags: Option[Int])

  implicit def jsonToEventFields: DecodeJson[EventFields] = {
    DecodeJson(ef => for {
      commandId <- (ef --\ "command_id").as[Option[Int]]
      eventFlags <- (ef --\ "event_flags").as[Option[Int]]
    } yield EventFields(commandId, eventFlags))
  }

  //"_action":"event","_event":{},"_id":1,"_target":1,"_type":"focus"
//  case class EventReply(action: String, event: Option[JsonObject], id: MessageId, target: WinId, _type: String)
  case class EventReply(action: String, ef: EventFields, id: MessageId, target: Int, _type: String)

  implicit def jsonToEvent: DecodeJson[EventReply] = {
    DecodeJson(e => for {
      action <- (e --\ "_action").as[String]
      event <- (e --\ "_event").as[EventFields]
      id <- (e --\ "_id").as[Int]
      target <- (e --\ "_target").as[Int]
      _type <- (e --\ "_type").as[String]
    } yield EventReply(action, event, MessageId(id), target, _type))
  }
}
