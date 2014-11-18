//root_url the url to load as top-level document for the window
//  size
//width the initial window width
//height the initial window height
//title the window title
//  icon_path absolute path to a PNG or JPG icon file for the window
//  has_frame creates a frameless window if true
//session_id the id of the session to use for this window

package com.eklavya.thrust

import scala.concurrent.Future

case class Size(width: Int, height: Int)

case class Window(id: Int,
                  root_url: String,
                  size: Size,
                  title: String,
                  icon_path: String,
                  session_id: Int) {


  def show: Unit = {

  }


  // focus whether to focus or blur the window
  // Focuses or blur the window depending on the value of focus
  def focus(blur: Boolean) : Unit = {

  }


  // Maximizes the window
  def maximize : Unit = {

  }


  // Minimizes the window
  def minimize : Unit = {

  }


  // Restores a minimized window
  def restore : Unit = {

  }


  // title the title to set
  // Sets the title of a window
  def setTitle(title: String) : Unit = {

  }


  // fullscreen whether to set the window fullscreen or not
  // Makes the window enter or leave fullscreen
  def setFullscreen : Unit = {

  }


  // kiosk whether to set the window in kiosk mode
  // Makes the window enter or leave kiosk mode
  def setKiosk : Unit = {

  }


  // Opens the DevTools for this window's main document
  def openDevtools : Unit = {

  }


  // Closes the DevTools for this window's main document
  def closeDevtools : Unit = {

  }


  // x the new x position
  // y the new y position
  // Moves the window to the specified position
  def move(pos: Position) : Unit = {

  }


  // width the new window width
  // height the new window height
  // Resizes the window to the specified size
  def resize(s: Size) : Unit = {

  }


  // Returns wether the window has been closed or not (can't be reopened)
  def isClosed : Future[Boolean] = {

  }


  // Returns the size of the window
  def size : Future[Size] = {

  }


  // Returns the position of the window
  def position : Future[Position] = {

  }


  // Returns whether the window is maximized or not
  def isMaximized : Future[Boolean] = {

  }


  // Returns whether the window is minimized or not
  def isMinimized : Future[Boolean] = {

  }


  // Returns whether the window is in fullscreen mode or not
  def isFullscreen : Future[Boolean] = {

  }


  // Returns whether the window is in kiosk mode or not
  def isKiosed : Future[Boolean] = {

  }


  // Returns whether the window's main document has its DevTools opened or not
  def isDevtoolsOpened : Future[Boolean] = {

  }

}

object Window {
  def create(root_url: String, title: String, icon_path: String, size: Size = Size(1280, 720)): Unit = {
    Sender.send(Command(Create, Empty, None,))
  }
}