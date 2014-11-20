package com.eklavya.thrust

import argonaut._,Argonaut._

object Arguments {

  sealed abstract class Argument {
    def toJson: Json
  }

  implicit def argumentToJson(a: Argument): Json = a.toJson

  case class RootUrl(v: String) extends Argument {
    def toJson: Json = Json("root_url" -> jString(v))
  }

  case class Focus(v: Boolean) extends Argument {
    def toJson: Json = Json("focus" -> jBool(v))
  }

  case class Title(v: String) extends Argument {
    def toJson: Json = Json("title" -> jString(v))
  }

  case class Value(v: Boolean) extends Argument {
    def toJson: Json = Json("value" -> jBool(v))
  }

  case class CookieStore(v: Boolean) extends Argument {
    def toJson: Json = Json("cookie_store" -> jBool(v))
  }

  case class OffTheRecord(v: Boolean) extends Argument {
    def toJson: Json = Json("off_the_record" -> jBool(v))
  }

  case class Size(width: Int, height: Int) extends Argument {
    def toJson: Json = Json("size" -> (("width" := width) ->: ("height" := height) ->: jEmptyObject))
  }

  case class Position(x: Int, y: Int) extends Argument {
    def toJson: Json = Json("position" -> (("x" := x) ->: ("y" := y) ->: jEmptyObject))
  }
}
