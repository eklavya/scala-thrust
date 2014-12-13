package com.github.eklavya.thrust

import scala.collection.concurrent.TrieMap
import scala.concurrent.Promise

private[thrust] object MessageBox {
  private val promiseMap = TrieMap.empty[MessageId, Promise[_]]

  def addPromise[T](id: MessageId, p: Promise[T]): Unit = promiseMap += (id -> p)

  def removePromise(id: MessageId): Unit = promiseMap.remove(id)

  def getPromise[T](id: MessageId) = promiseMap(id).asInstanceOf[Promise[T]]

}