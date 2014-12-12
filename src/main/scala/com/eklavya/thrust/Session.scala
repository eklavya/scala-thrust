package com.eklavya.thrust

import java.security.Timestamp
import java.util.Date

import com.eklavya.thrust.Actions.CALL
import com.eklavya.thrust.Arguments.Url
import com.eklavya.thrust.Methods.{IS_OFF_THE_RECORD, VISITED_LINK_ADD, VISITED_LINK_CLEAR}

import scala.collection.mutable.{HashMap, MultiMap, Set}
import scala.concurrent.{Future, Promise}

case class Cookie(
                   //source url
                   source: String,
                   //the cookie name
                   name: String,
                   //the cookie value
                   value: String,
                   //the cookie domain
                   domain: String,
                   //the cookie path
                   path: String,
                   //the creation date
                   creation: Date,
                   //the expiration date
                   expiry: Date,
                   //the last time the cookie was accessed
                   last_access: Timestamp,
                   //is the cookie secure
                   secure: Boolean,
                   //is the cookie only valid for HTTP
                   http_only: Boolean,
                   //internal priority information
                   priority: String)

case class Session(offTheRecord: Boolean, path: String, cookieStore: Boolean) {

  private val cookies = new HashMap[String, Set[Cookie]] with MultiMap[String, Cookie]

  //  Adds the specified url to the list of visited links for this session
  def visitedlinkAdd(url: String): Unit = {
    Sender.sendCommand(CALL, VISITED_LINK_ADD, None, None, List(Url(url)))
  }


  //  Clears the visited links storage for this session
  def visitedlinkClear = {
    Sender.sendCommand(CALL, VISITED_LINK_CLEAR, None, None, List())
  }


  //  Returns whether the session is off the record or not
  def isOffTheRecord: Future[Boolean] = {
    val id = Sender.sendCommand(action = CALL,
      method = IS_OFF_THE_RECORD,
      _type = None,
      target = None,
      args = List())
    val p = Promise[Boolean]
    val f = p.future
    MessageBox.addPromise(id, p)
    f
  }

  def cookiesLoadForKey(domain: String) = {
    cookies.get(domain)
  }

  def cookies_flush = {

  }

  def cookies_add(ckies: Set[Cookie]) = {
    cookies.synchronized {
      cookies ++: ckies.groupBy(_.domain)
    }
  }

  def cookies_delete(cookie: Cookie) = {
    cookies.synchronized {
      cookies.get(cookie.domain).map(_.remove(cookie))
    }
  }
}