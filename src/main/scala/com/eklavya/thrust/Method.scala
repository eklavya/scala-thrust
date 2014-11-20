package com.eklavya.thrust

import argonaut._,Argonaut._

object Methods {

  implicit def methodToJson(m: Method): Json = Json("_method" -> jString(m.name))

  abstract class Method {
    val name: String
  }

  case object Empty extends Method {
    override val name = ""
  }

  case object Show extends Method {
    override val name = "show"
  }

  case object Focus extends Method {
    override val name = "focus"
  }

  case object Maximize extends Method {
    override val name = "maximize"
  }

  case object Minimize extends Method {
    override val name = "minimize"
  }

  case object Restore extends Method {
    override val name = "restore"
  }

  case object SetTitle extends Method {
    override val name = "set_title"
  }

  case object SetFullscreen extends Method {
    override val name = "set_fullscreen"
  }

  case object SetKiosk extends Method {
    override val name = "set_kiosk"
  }

  case object OpenDevtools extends Method {
    override val name = "open_devtools"
  }

  case object CloseDevtools extends Method {
    override val name = "close_devtools"
  }

  case object Move extends Method {
    override val name = "move"
  }

  case object Resize extends Method {
    override val name = "resize"
  }

  case object IsClosed extends Method {
    override val name = "is_closed"
  }

  case object Size extends Method {
    override val name = "size"
  }

  case object Position extends Method {
    override val name = "position"
  }

  case object IsMaximized extends Method {
    override val name = "is_maximized"
  }

  case object IsMinimized extends Method {
    override val name = "is_minimized"
  }

  case object IsFullscreen extends Method {
    override val name = "is_fullscreen"
  }

  case object IsKiosed extends Method {
    override val name = "is_kiosked"
  }

  case object IsDevtoolsOpened extends Method {
    override val name = "is_devtools_opened"
  }
}
