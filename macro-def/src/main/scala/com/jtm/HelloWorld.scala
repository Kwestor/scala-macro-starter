package com.jtm

import scala.reflect.macros.Context
import scala.language.experimental.macros

object HelloWorld {

  def hello(): Unit = macro hello_impl

  def hello_impl(c: Context)(): c.Expr[Unit] = {
    import c.universe._
    reify { println("Hello World!") }
  }

  def hello(param: Any): Unit = macro helloParam_impl

  def helloParam_impl(c: Context)(param: c.Expr[Any]): c.Expr[Unit] = {
    import c.universe._
    c.warning(param.tree.pos, "Test warning")
    reify { println(s"Hello ${param.splice}!") }
  }

}
