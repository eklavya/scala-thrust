package com.eklavya.thrust

import argonaut._,Argonaut._

object Actions {

abstract class Action

case object Create extends Action

case object Call extends Action

  implicit def createToJson(c: Create.type): Json = Json("_action" -> jString("create"))

  implicit def callToJson(c: Call.type): Json = Json("_action" -> jString("call"))
}
