package com.github.eklavya.thrust

import argonaut.Argonaut._
import argonaut._

object Methods {

  implicit def methodToJson(m: Method): (Json.JsonField, Json) = "_method" -> jString(m.name)

  sealed abstract class Method {
    val name: String
  }

  case object EMPTY extends Method {
    override val name = ""
  }

  case object SHOW extends Method {
    override val name = "show"
  }

  case object FOCUS extends Method {
    override val name = "focus"
  }

  case object MAXIMIZE extends Method {
    override val name = "maximize"
  }

  case object MINIMIZE extends Method {
    override val name = "minimize"
  }

  case object RESTORE extends Method {
    override val name = "restore"
  }

  case object SET_TITLE extends Method {
    override val name = "set_title"
  }

  case object SET_FULLSCREEN extends Method {
    override val name = "set_fullscreen"
  }

  case object SET_KIOSK extends Method {
    override val name = "set_kiosk"
  }

  case object OPEN_DEV_TOOLS extends Method {
    override val name = "open_devtools"
  }

  case object CLOSE_DEV_TOOLS extends Method {
    override val name = "close_devtools"
  }

  case object MOVE extends Method {
    override val name = "move"
  }

  case object RESIZE extends Method {
    override val name = "resize"
  }

  case object IS_CLOSED extends Method {
    override val name = "is_closed"
  }

  case object SIZE extends Method {
    override val name = "size"
  }

  case object POSITION extends Method {
    override val name = "position"
  }

  case object IS_MAXIMIZED extends Method {
    override val name = "is_maximized"
  }

  case object IS_MINIMIZED extends Method {
    override val name = "is_minimized"
  }

  case object IS_FULLSCREEN extends Method {
    override val name = "is_fullscreen"
  }

  case object IS_KIOSED extends Method {
    override val name = "is_kiosked"
  }

  case object IS_DEV_TOOLS_OPENED extends Method {
    override val name = "is_devtools_opened"
  }

  case object VISITED_LINK_ADD extends Method {
    override val name = "visitedlink_add"
  }

  case object VISITED_LINK_CLEAR extends Method {
    override val name = "visitedlink_clear"
  }

  case object PROXY_SET extends Method {
    override val name = "proxy_set"
  }

  case object PROXY_CLEAR extends Method {
    override val name = "proxy_clear"
  }

  case object IS_OFF_THE_RECORD extends Method {
    override val name = "is_off_the_record"
  }

  case object ADD_ITEM extends Method {
    override val name = "add_item"
  }

  case object ADD_CHECK_ITEM extends Method {
    override val name = "add_check_item"
  }

  case object ADD_RADIO_ITEM extends Method {
    override val name = "add_radio_item"
  }

  case object ADD_SEPARATOR extends Method {
    override val name = "add_separator"
  }

  case object SET_CHECKED extends Method {
    override val name = "set_checked"
  }

  case object SET_ENABLED extends Method {
    override val name = "set_enabled"
  }

  case object SET_VISIBLE extends Method {
    override val name = "set_visible"
  }

  case object SET_ACCELERATOR extends Method {
    override val name = "set_accelerator"
  }

  case object ADD_SUBMENU extends Method {
    override val name = "add_submenu"
  }

  case object CLEAR extends Method {
    override val name = "clear "
  }

  case object POPUP extends Method {
    override val name = "popup"
  }

  case object SET_APPLICATION_MENU extends Method {
    override val name = "set_application_menu"
  }

}
