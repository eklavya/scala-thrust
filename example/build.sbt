name := """scala-thrust-example"""

version := "1.0"

scalaVersion := "2.11.4"

crossScalaVersions := Seq("2.10.4", scalaVersion.value)

libraryDependencies += "io.argonaut" %% "argonaut" % "6.0.4"

libraryDependencies += "com.typesafe" % "config" % "1.2.1"
