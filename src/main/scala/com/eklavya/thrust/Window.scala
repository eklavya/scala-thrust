//root_url the url to load as top-level document for the window
//  size
//width the initial window width
//height the initial window height
//title the window title
//  icon_path absolute path to a PNG or JPG icon file for the window
//  has_frame creates a frameless window if true
//session_id the id of the session to use for this window

package com.eklavya.thrust

import com.eklavya.thrust.Actions.{CALL, CREATE}
import com.eklavya.thrust.Arguments._
import com.eklavya.thrust.Events._
import com.eklavya.thrust.Methods._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}
import scala.reflect.ClassTag

case class WinId(id: Int) extends AnyVal

case class Window(_id: WinId,
                  root_url: String,
                  size: Size,
                  title: String,
                  icon_path: String,
                  session_id: Option[Int]) {

  def show: Unit = {
    Sender.sendCommand(CALL, SHOW, None, Some(_id), List())
  }

  // focus whether to focus or blur the window
  // Focuses or blur the window depending on the value of focus
  def focus(blur: Boolean): Unit = {
    Sender.sendCommand(CALL, FOCUS, None, Some(_id), List(Focus(blur)))
  }

  // Maximizes the window
  def maximize: Unit = {
    Sender.sendCommand(CALL, MAXIMIZE, None, Some(_id), List())
  }

  // Minimizes the window
  def minimize: Unit = {
    Sender.sendCommand(CALL, MINIMIZE, None, Some(_id), List())
  }

  // Restores a minimized window
  def restore: Unit = {
    Sender.sendCommand(CALL, RESTORE, None, Some(_id), List())
  }

  // title the title to set
  // Sets the title of a window
  def setTitle(title: String): Unit = {
    Sender.sendCommand(CALL, SET_TITLE, None, Some(_id), List(Title(title)))
  }

  // fullscreen whether to set the window fullscreen or not
  // Makes the window enter or leave fullscreen
  def setFullscreen: Unit = {
    Sender.sendCommand(CALL, SET_FULLSCREEN, None, Some(_id), List())
  }

  // kiosk whether to set the window in kiosk mode
  // Makes the window enter or leave kiosk mode
  def setKiosk: Unit = {
    Sender.sendCommand(CALL, SET_KIOSK, None, Some(_id), List())
  }

  // Opens the DevTools for this window's main document
  def openDevtools: Unit = {
    Sender.sendCommand(CALL, OPEN_DEV_TOOLS, None, Some(_id), List())
  }

  // Closes the DevTools for this window's main document
  def closeDevtools: Unit = {
    Sender.sendCommand(CALL, CLOSE_DEV_TOOLS, None, Some(_id), List())
  }

  // x the new x position
  // y the new y position
  // Moves the window to the specified position
  def move(pos: Position): Unit = {
    Sender.sendCommand(CALL, MOVE, None, Some(_id), List(pos))
  }

  // width the new window width
  // height the new window height
  // Resizes the window to the specified size
  def resize(s: Size): Unit = {
    Sender.sendCommand(CALL, RESIZE, None, Some(_id), List(s))
  }

  private def callAndGet[T: ClassTag](m: Method): Future[T] = {
    val id = Sender.sendCommand(action = CALL,
      method = m,
      _type = None,
      target = Some(_id),
      args = List())
    val p = Promise[T]
    val f = p.future
    MessageBox.addPromise(id, p)
    f.mapTo[T]
  }

  // Returns wether the window has been closed or not (can't be reopened)
  def isClosed: Future[Boolean] = callAndGet(IS_CLOSED)

  // Returns the size of the window
  def getSize: Future[Size] = callAndGet(SIZE)

  // Returns the position of the window
  def position: Future[Position] = callAndGet(POSITION)

  // Returns whether the window is maximized or not
  def isMaximized: Future[Boolean] = callAndGet(IS_MAXIMIZED)

  // Returns whether the window is minimized or not
  def isMinimized: Future[Boolean] = callAndGet(IS_MINIMIZED)

  // Returns whether the window is in fullscreen mode or not
  def isFullscreen: Future[Boolean] = callAndGet(IS_FULLSCREEN)

  // Returns whether the window is in kiosk mode or not
  def isKiosed: Future[Boolean] = callAndGet(IS_KIOSED)

  // Returns whether the window's main document has its DevTools opened or not
  def isDevtoolsOpened: Future[Boolean] = callAndGet(IS_DEV_TOOLS_OPENED)

  def onBlur(f: Function0[Unit]) = Events.setCallback(_id, BLURED, f)

  def onFocus(f: Function0[Unit]) = Events.setCallback(_id, FOCUSED, f)

  //called on closed
  Events.setCallback(_id, CLOSED, () => {
    Events.removeForWindow(_id)
  })

  def onResponsive(f: Function0[Unit]) = Events.setCallback(_id, RESPONSIVE, f)

  def onUnResponsive(f: Function0[Unit]) = Events.setCallback(_id, UNRESPONSIVE, f)

  def onRendererCrashed(f: Function0[Unit]) = Events.setCallback(_id, WORKER_CRASHED, f)
}

object Window {
  def create(root_url: String, title: String = "", icon_path: String = "", size: Size = Size(1280, 720)): Future[Window] = {
    val args = List[Argument](RootUrl(root_url), Title(title), IconPath(icon_path), size)
    val id = Sender.sendCommand(CREATE, EMPTY, Some("window"), None, args)
    val p = Promise[Int]
    val f = p.future
    MessageBox.addPromise(id, p)
    f.map { winId =>
      MessageBox.removePromise(id)
      Window(WinId(winId), root_url, size, title, icon_path, None)
    }
  }
}