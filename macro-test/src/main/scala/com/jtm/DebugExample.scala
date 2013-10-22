package com.jtm

object DebugExample extends App {

  val a = 10
  case class Person(name: String)
  val ala = Person("Ala")

  import Debug._
  debug(10)
  debug(a)
  debug(ala)
  debug(ala.name)
}
