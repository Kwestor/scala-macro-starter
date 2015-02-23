import sbt._
import Keys._

val baseSettings = Seq(
  scalaVersion := "2.11.5",
  organization := "com.jtm",
  name := "scala-macro-starter",
  version := "1.0.0",
  resolvers += Resolver.sonatypeRepo("releases"),
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0-M5" cross CrossVersion.full),
  initialCommands in console := "import com.jtm._",
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "2.2.1" % "test"
  ),
  scalacOptions ++= Seq(
    "-nowarn"
  )
)

lazy val root = (project in file("."))
  .settings(baseSettings: _*)
  .aggregate(macroDef, macroTest)

lazy val macroDef = (project in file("macro-def"))
  .settings(baseSettings: _*)
  .settings(
    name := "macro-def",
    libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
  )

lazy val macroTest = (project in file("macro-test"))
  .settings(baseSettings: _*)
  .settings(
    name := "macro-test"
  ).dependsOn(macroDef)
