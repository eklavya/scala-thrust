package com.eklavya.thrust

import argonaut.Argonaut._
import argonaut._

object Arguments {

  sealed abstract class Argument {
    def toJson: (Json.JsonField, Json)
  }

  implicit def argumentToJson(a: Argument): (Json.JsonField, Json) = a.toJson

  case class RootUrl(v: String) extends Argument {
    override def toJson = "root_url" -> jString(v)
  }

  case class Focus(v: Boolean) extends Argument {
    override def toJson = "focus" -> jBool(v)
  }

  case class Title(v: String) extends Argument {
    override def toJson = "title" -> jString(v)
  }

  case class Value(v: Boolean) extends Argument {
    override def toJson = "value" -> jBool(v)
  }

  case class CookieStore(v: Boolean) extends Argument {
    override def toJson = "cookie_store" -> jBool(v)
  }

  case class OffTheRecord(v: Boolean) extends Argument {
    override def toJson = "off_the_record" -> jBool(v)
  }

  case class Size(width: Int, height: Int) extends Argument {
    override def toJson = "size" -> (("width" := width) ->: ("height" := height) ->: jEmptyObject)
  }

  case class Position(x: Int, y: Int) extends Argument {
    override def toJson = "position" -> (("x" := x) ->: ("y" := y) ->: jEmptyObject)
  }

  case class IconPath(v: String) extends Argument {
    override def toJson = "icon_path" -> jString(v)
  }
}
