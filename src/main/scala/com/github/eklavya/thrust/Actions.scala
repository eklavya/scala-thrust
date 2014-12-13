package com.github.eklavya.thrust

import argonaut.Argonaut._
import argonaut._

object Actions {

  implicit def actionToJson(a: Action): (Json.JsonField, Json) = a.toJson

  sealed abstract class Action {
    def toJson: (Json.JsonField, Json)
  }

  case object CREATE extends Action {
    override def toJson: (Json.JsonField, Json) = ("_action" -> jString("create"))
  }

  case object CALL extends Action {
    override def toJson: (Json.JsonField, Json) = ("_action" -> jString("call"))
  }

}
