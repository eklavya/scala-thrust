package com.github.eklavya.thrust

import java.text.SimpleDateFormat

import argonaut.Argonaut._
import argonaut._
import com.github.eklavya.thrust.Arguments.{Position, Size}
import com.github.eklavya.thrust.RemoteArguments.Cookie
import com.github.eklavya.thrust.RemoteMethods.RemoteMethod

/**
 * Created by eklavya on 11/22/14.
 */
private[thrust] object Replies {

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

  implicit def jsonToCookie: DecodeJson[Cookie] = {
    DecodeJson(c => for {
      source <- (c --\ "source").as[String]
      name <- (c --\ "name").as[String]
      value <- (c --\ "value").as[String]
      domain <- (c --\ "domain").as[String]
      path <- (c --\ "path").as[String]
      creation <- (c --\ "creation").as[String]
      expiry <- (c --\ "expiry").as[String]
      last_access <- (c --\ "last_access").as[String]
      secure <- (c --\ "secure").as[Boolean]
      http_only <- (c --\ "http_only").as[Boolean]
      priority <- (c --\ "priority").as[String]
    } yield Cookie(source, name, value,
        domain, path, stampToDate(creation), stampToDate(expiry), stampToDate(last_access), secure, http_only, priority))
  }

  def stampToDate(stamp: String) = {
    val df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz")
    df.parse(stamp)
  }

  implicit def jsonToArgs: DecodeJson[Args] = {
    DecodeJson(a => for {
      cookie <- (a --\ "cookie").as[Option[RemoteArguments.Cookie]]
      key <- (a --\ "key").as[Option[String]]
    } yield Args(cookie, key))
  }

  implicit def jsonToReply: DecodeJson[Reply] = {
    DecodeJson(r => for {
      action <- (r --\ "_action").as[String]
      method <- (r --\ "_method").as[Option[String]]
      args <- (r --\ "_args").as[Option[Args]]
      error <- (r --\ "_error").as[Option[String]]
      id <- (r --\ "_id").as[Int]
      result <- (r --\ "_result").as[Result]
    } yield Reply(action, method.map(RemoteMethods.fromString), args, error, MessageId(id), result))
  }

  implicit def jsonToEventFields: DecodeJson[EventFields] = {
    DecodeJson(ef => for {
      commandId <- (ef --\ "command_id").as[Option[Int]]
      eventFlags <- (ef --\ "event_flags").as[Option[Int]]
    } yield EventFields(commandId, eventFlags))
  }

  implicit def jsonToEvent: DecodeJson[EventReply] = {
    DecodeJson(e => for {
      action <- (e --\ "_action").as[String]
      event <- (e --\ "_event").as[EventFields]
      id <- (e --\ "_id").as[Int]
      target <- (e --\ "_target").as[Int]
      _type <- (e --\ "_type").as[String]
    } yield EventReply(action, event, MessageId(id), target, _type))
  }

  case class Result(target: Option[WinId],
                    size: Option[Size],
                    position: Option[Position],
                    maximize: Option[Boolean],
                    kiosk: Option[Boolean],
                    minimized: Option[Boolean],
                    fullScreen: Option[Boolean],
                    devToolsOpened: Option[Boolean],
                    closed: Option[Boolean])

  case class Args(cookie: Option[Cookie], key: Option[String])

  case class Reply(action: String, method: Option[RemoteMethod], args: Option[Args], error: Option[String], id: MessageId, result: Result)

  //_event":{"command_id":1,"event_flags":16},"
  case class EventFields(commandId: Option[Int], eventFlags: Option[Int])

  //"_action":"event","_event":{},"_id":1,"_target":1,"_type":"focus"
  //  case class EventReply(action: String, event: Option[JsonObject], id: MessageId, target: WinId, _type: String)
  case class EventReply(action: String, ef: EventFields, id: MessageId, target: Int, _type: String)

}
