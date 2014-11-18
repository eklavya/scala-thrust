package com.eklavya.thrust

abstract class Argument

case class RootUrl(url: String) extends Argument

case class Focus(b: Boolean) extends Argument

case class Title(t: String) extends Argument

case class Value(b: Boolean) extends Argument

case class CookieStore(b: Boolean) extends Argument

case class OffTheRecord(b: Boolean) extends Argument

case class Size(width: Int, height: Int) extends Argument
