package com.eklavya.thrust

import scala.collection.concurrent.TrieMap
import scala.concurrent.Promise

private[thrust] object MessageBox {
  private val promiseMap = TrieMap.empty[Int, Promise[_]]

  def addBooleanPromise(id: Int, p: Promise[Boolean]): Unit = promiseMap += (id -> p)

  def addSizePromise(id: Int, p: Promise[Size]): Unit = promiseMap += (id -> p)

  def addPositionPromise(id: Int, p: Promise[Position]): Unit = promiseMap += (id -> p)

  def removePromise(id: Int): Unit = promiseMap.remove(id)

  def getBooleanPromise(id: Int) = promiseMap(id).asInstanceOf[Promise[Boolean]]

  def getSizePromise(id: Int) = promiseMap(id).asInstanceOf[Promise[Size]]

  def getPositionPromise(id: Int) = promiseMap(id).asInstanceOf[Promise[Position]]
}