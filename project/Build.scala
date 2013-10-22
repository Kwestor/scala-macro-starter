import sbt._
import Keys._

object Version {
  val scala = "2.10.2"
  val scalaTest = "2.0.RC2"
}

object Library {
  val scalaTest = "org.scalatest" %% "scalatest" % Version.scalaTest
  val scalaReflect = "org.scala-lang" % "scala-reflect" % Version.scala
}

object Dependencies {

  import Library._

  val main = Seq(
    scalaTest % "test"
  )

  val macros = Seq(
    scalaReflect
  )
}

object BuildSettings {

  lazy val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := "com.jtm",
    name := "scala-macro-starter",
    version := "1.0.0",
    scalaVersion := Version.scala,
    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-feature"
    ),
    resolvers += Resolver.sonatypeRepo("snapshots"),
    initialCommands in console := "import com.jtm._",
    addCompilerPlugin("org.scala-lang.plugins" % "macro-paradise" % "2.0.0-SNAPSHOT" cross CrossVersion.full),
    libraryDependencies ++= Dependencies.main
  )
}

object MyBuild extends Build {

  import BuildSettings._

  lazy val macroDef = Project(id = "macro-def", base = file("macro-def"), settings = buildSettings).settings(
    name := "macro-def",
    libraryDependencies += "org.scala-lang" % "scala-reflect" % Version.scala
  )

  // macro usage/test project
  lazy val macroTest = Project(id = "macro-test", base = file("macro-test"), settings = buildSettings)
    .dependsOn(macroDef)
}