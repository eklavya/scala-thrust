package com.github.eklavya.thrust

import argonaut.Argonaut._
import argonaut._

object Arguments {

  implicit def argumentToJson(a: Argument): (Json.JsonField, Json) = a.toJson

  sealed abstract class Argument {
    def toJson: (Json.JsonField, Json)
  }

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

  case class Url(u: String) extends Argument {
    override def toJson = "url" -> jString(u)
  }

  case class Arg_CommandId(id: CommandId) extends Argument {
    override def toJson = "command_id" -> jNumber(id.id)
  }

  case class Label(l: String) extends Argument {
    override def toJson = "label" -> jString(l)
  }

  case class Arg_GroupId(id: GroupId) extends Argument {
    override def toJson = "group_id" -> jNumber(id.id)
  }

  case class Arg_MenuId(id: MenuId) extends Argument {
    override def toJson = "menu_id" -> jNumber(id.id)
  }

  case class Accelerator(acc: String) extends Argument {
    override def toJson = "accelerator" -> jString(acc)
  }

  case class Arg_WindowId(id: WinId) extends Argument {
    override def toJson = "window_id" -> jNumber(id.id)
  }

  case class Path(s: String) extends Argument {
    override val toJson = "path" -> jString(s)
  }

}
