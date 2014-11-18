package com.eklavya.thrust

abstract class Method

case object Empty extends Method

// Makes the window visible
case object Show extends Method


// focus whether to focus or blur the window
// Focuses or blur the window depending on the value of focus
case object Focus extends Method


// Maximizes the window
case object Maximize extends Method


// Minimizes the window
case object Minimize extends Method


// Restores a minimized window
case object Restore extends Method


// title the title to set
// Sets the title of a window
case object SetTitle extends Method


// fullscreen whether to set the window fullscreen or not
// Makes the window enter or leave fullscreen
case object SetFullscreen extends Method


// kiosk whether to set the window in kiosk mode
// Makes the window enter or leave kiosk mode
case object SetKiosk extends Method


// Opens the DevTools for this window's main document
case object OpenDevtools extends Method


// Closes the DevTools for this window's main document
case object CloseDevtools extends Method


// x the new x position
// y the new y position
// Moves the window to the specified position
case object Move extends Method


// width the new window width
// height the new window height
// Resizes the window to the specified size
case object Resize extends Method


// Returns wether the window has been closed or not (can't be reopened)
case object IsClosed extends Method


// Returns the size of the window
case object Size extends Method


// Returns the position of the window
case object Position extends Method


// Returns whether the window is maximized or not
case object IsMaximized extends Method


// Returns whether the window is minimized or not
case object IsMinimized extends Method


// Returns whether the window is in fullscreen mode or not
case object IsFullscreen extends Method


// Returns whether the window is in kiosk mode or not
case object IsKiosed extends Method


// Returns whether the window's main document has its DevTools opened or not
case object IsDevtoolsOpened extends Method
