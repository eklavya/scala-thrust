package com.eklavya.thrust

import scala.collection.concurrent.TrieMap
import scala.concurrent.Promise

private[thrust] object MessageBox {
  private val boolMap = TrieMap.empty[Int, Promise[Boolean]]

  private val sizeMap = TrieMap.empty[Int, Promise[Size]]

  private val posMap = TrieMap.empty[Int, Promise[Position]]

  def addBooleanPromise(id: Int, p: Promise[Boolean]): Unit = boolMap += (id -> p)

  def addSizePromise(id: Int, p: Promise[Size]): Unit = sizeMap += (id -> p)

  def addPositionPromise(id: Int, p: Promise[Position]): Unit = posMap += (id -> p)

  def removeBooleanPromise(id: Int): Unit = boolMap.remove(id)

  def removeSizePromise(id: Int): Unit = sizeMap.remove(id)

  def removePositionPromise(id: Int): Unit = posMap.remove(id)

  def getBooleanPromise(id: Int) = boolMap(id)

  def getSizePromise(id: Int) = sizeMap(id)

  def getPositionPromise(id: Int) = posMap(id)
}