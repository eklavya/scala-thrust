package com.github.eklavya.thrust

import java.util.concurrent.atomic.AtomicInteger

import com.github.eklavya.thrust.Actions.{CALL, CREATE}
import com.github.eklavya.thrust.Arguments._
import com.github.eklavya.thrust.Events.EXECUTE
import com.github.eklavya.thrust.Methods._

import scala.collection.concurrent.TrieMap
import scala.collection.mutable.Seq
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Promise

case class CommandId(id: Int) extends AnyVal

case class GroupId(id: Int) extends AnyVal

sealed trait Item {
  val cmdId: CommandId
  val label: String
  val onExecute: Function1[Int, Unit]
}

class MenuItem(override val cmdId: CommandId, override val label: String, override val onExecute: Function1[Int, Unit]) extends Item

object MenuItem {
  def apply(lbl: String, onExecute: Function1[Int, Unit]) = {
    new MenuItem(CommandId(Menu.getCommandId), lbl, onExecute)
  }
}

class CheckItem(override val cmdId: CommandId, override val label: String, override val onExecute: Function1[Int, Unit]) extends Item

object CheckItem {
  def apply(lbl: String, onExecute: Function1[Int, Unit]) = {
    new CheckItem(CommandId(Menu.getCommandId), lbl, onExecute)
  }
}

class RadioItem(override val cmdId: CommandId, override val label: String, val groupId: GroupId, override val onExecute: Function1[Int, Unit]) extends Item

object RadioItem {
  def apply(lbl: String, id: GroupId, onExecute: Function1[Int, Unit]) = {
    new RadioItem(CommandId(Menu.getCommandId), lbl, id, onExecute)
  }
}

case class Menu(id: MenuId, cmdId: CommandId, label: String, items: TrieMap[CommandId, Item], subMenus: Seq[Menu]) {

  def addItem(i: Item) = {
    items.synchronized {
      items += (i.cmdId -> i)
    }
    Sender.sendCommand(CALL, ADD_ITEM, Some("menu"), Some(id),
      List(Arg_CommandId(i.cmdId), Label(i.label)))
  }

  def add_check_item(c: CheckItem) = addItem(c)

  def add_radio_item(r: RadioItem) = {
    items.synchronized {
      items += (r.cmdId -> r)
    }
    Sender.sendCommand(CALL, ADD_ITEM, Some("menu"), Some(id),
      List(Arg_CommandId(r.cmdId), Label(r.label), Arg_GroupId(r.groupId)))
  }

  def add_separator = {
    Sender.sendCommand(CALL, ADD_SEPARATOR, None, Some(id), List())
  }

  def set_checked(c: CheckItem, value: Boolean) = {
    Sender.sendCommand(CALL, SET_CHECKED, None, Some(id), List(Arg_CommandId(c.cmdId), Value(value)))
  }

  def set_enabled(i: Item, value: Boolean) = {
    Sender.sendCommand(CALL, SET_ENABLED, None, Some(id), List(Arg_CommandId(i.cmdId), Value(value)))
  }

  def set_visible(i: Item, value: Boolean) = {
    Sender.sendCommand(CALL, SET_VISIBLE, None, Some(id), List(Arg_CommandId(i.cmdId), Value(value)))
  }

  def set_accelerator(i: Item, accl: String) = {
    Sender.sendCommand(CALL, SET_ACCELERATOR, None, Some(id), List(Arg_CommandId(i.cmdId), Accelerator(accl)))
  }

  def addSubmenu(m: Menu) = {
    subMenus.synchronized(subMenus :+ m)
    Sender.sendCommand(CALL, ADD_SUBMENU, None, Some(id), List(Arg_MenuId(m.id), Label(m.label), Arg_CommandId(m.cmdId)))
  }

  def clear = {
    items.synchronized(items.filter(_ => false))
    subMenus.synchronized(subMenus.filter(_ => false))
    Sender.sendCommand(CALL, CLEAR, None, Some(id), List())
  }

  def popup(w: Window) = {
    Sender.sendCommand(CALL, POPUP, None, Some(id), List(Arg_WindowId(w._id)))
  }

  def set_application_menu = {
    Sender.sendCommand(CALL, SET_APPLICATION_MENU, None, Some(id), List())
  }

  {
    Events.setCallback(id, EXECUTE, {
      ef => items.get(CommandId(ef.commandId.get)).foreach(_.onExecute(ef.eventFlags.get))
    })
  }
}

object Menu {
  var nextCmdId = new AtomicInteger(0)

  def getCommandId = nextCmdId.getAndIncrement

  def create(label: String) = {
    val cmdId = CommandId(getCommandId)
    val id = Sender.sendCommand(CREATE, EMPTY, Some("menu"), None, List(Arg_CommandId(cmdId), Label(label)))
    val p = Promise[Int]
    val f = p.future
    MessageBox.addPromise(id, p)
    f.map { menuId =>
      MessageBox.removePromise(id)
      Menu(MenuId(menuId), cmdId, label, TrieMap.empty[CommandId, Item], Seq())
    }
  }
}