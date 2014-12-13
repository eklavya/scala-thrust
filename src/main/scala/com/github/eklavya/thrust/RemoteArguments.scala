package com.github.eklavya.thrust

import java.util.Date

/**
 * Created by eklavya on 12/13/14.
 */
object RemoteArguments {

  sealed abstract class RemoteArgument

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
                     last_access: Date,
                     //is the cookie secure
                     secure: Boolean,
                     //is the cookie only valid for HTTP
                     http_only: Boolean,
                     //internal priority information
                     priority: String) extends RemoteArgument

  case class Key(key: String) extends RemoteArgument

}
