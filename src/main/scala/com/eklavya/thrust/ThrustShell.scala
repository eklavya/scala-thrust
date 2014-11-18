package com.eklavya.thrust

case class Result(_target: Option[Int])

case class Reply(_action: String, _error: Option[String], _id: Int, _result: Result)

object ThrustShell {
  val process = {
    val pb = new ProcessBuilder("/Users/eklavya/Documents/thrust/out/Debug/ThrustShell.app/Contents/MacOS/ThrustShell")
    pb.redirectErrorStream(true)
    pb.start
  }

  val in = io.Source.fromInputStream(process.getInputStream).bufferedReader

  val out = process.getOutputStream

  {
    new Thread(new Runnable {
      def run: Unit = {
        var continue = true
        while (continue) {
          Option(in.readLine()).map { reply =>
            ()
          }.getOrElse {
            continue = false
          }
        }
      }
    }).start()
  }
}