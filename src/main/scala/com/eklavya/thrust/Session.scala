package com.eklavya.thrust

import java.util.Date

import com.eklavya.thrust.Actions._
import com.eklavya.thrust.Arguments._
import com.eklavya.thrust.Methods.{EMPTY, IS_OFF_THE_RECORD, VISITED_LINK_ADD, VISITED_LINK_CLEAR}
import com.eklavya.thrust.RemoteArguments.Cookie
import com.eklavya.thrust.RemoteMethods.{COOKIES_DELETE, COOKIES_LOAD, COOKIES_LOAD_FOR_KEY, COOKIES_UPDATE_ACCESS_TIME}

import scala.collection.mutable.{HashMap, MultiMap, Set}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}

case class Session(id: SessionId, offTheRecord: Boolean, path: String, cookieStore: Boolean) {

  private val cookies = new HashMap[String, Set[Cookie]] with MultiMap[String, Cookie]

  //  Adds the specified url to the list of visited links for this session
  def visitedlinkAdd(url: String): Unit = {
    Sender.sendCommand(CALL, VISITED_LINK_ADD, None, Some(id), List(Url(url)))
  }


  //  Clears the visited links storage for this session
  def visitedlinkClear = {
    Sender.sendCommand(CALL, VISITED_LINK_CLEAR, None, Some(id), List())
  }


  //  Returns whether the session is off the record or not
  def isOffTheRecord: Future[Boolean] = {
    val mid = Sender.sendCommand(action = CALL,
      method = IS_OFF_THE_RECORD,
      _type = None,
      target = Some(id),
      args = List())
    val p = Promise[Boolean]
    val f = p.future
    MessageBox.addPromise(mid, p)
    f
  }

  def cookiesLoadForKey(domain: String) = {
    cookies.get(domain)
  }

  def cookies_flush = {

  }

  def cookies_add(ck: Cookie) = {
    cookies.synchronized {
      cookies.get(ck.domain).foreach(_ += ck)
    }
  }

  def cookies_delete(cookie: Cookie) = {
    cookies.synchronized {
      cookies.get(cookie.domain).map(_.remove(cookie))
    }
  }

  def cookies_update_access_time(c: Cookie) = {
    cookies.synchronized {
      cookies.get(c.domain).map { s =>
        s.remove(c)
        s += c.copy(last_access = new Date())
      }
    }
  }

  {
    RemoteMethods.setCallback(id, COOKIES_LOAD_FOR_KEY, args =>
      cookiesLoadForKey(args.key.get))
    RemoteMethods.setCallback(id, COOKIES_LOAD, args =>
      cookies_add(args.cookie.get))
    RemoteMethods.setCallback(id, COOKIES_UPDATE_ACCESS_TIME, args =>
      cookies_update_access_time(args.cookie.get))
    RemoteMethods.setCallback(id, COOKIES_DELETE, args =>
      cookies_delete(args.cookie.get))

  }
}

object Session {
  def create(offTheRecord: Boolean, path: String, cookieStore: Boolean) = {
    val args = List[Argument](OffTheRecord(offTheRecord), Path(path), CookieStore(cookieStore))
    val id = Sender.sendCommand(CREATE, EMPTY, Some("session"), None, args)
    val p = Promise[Int]
    val f = p.future
    MessageBox.addPromise(id, p)
    f.map { sessionId =>
      MessageBox.removePromise(id)
      Session(SessionId(sessionId), offTheRecord, path, cookieStore)
    }
  }
}