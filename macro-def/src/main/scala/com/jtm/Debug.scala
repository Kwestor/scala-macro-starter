package com.jtm

import scala.reflect.macros.Context
import scala.language.experimental.macros

object Debug {

  def debug(param: Any): Unit = macro debug_impl

  def debug_impl(c: Context)(param: c.Expr[Any]): c.Expr[Unit] = {
    import c.universe._
    val paramRep = show(param.tree)
    val paramRepTree = Literal(Constant(paramRep))
    val paramRepExpr = c.Expr[String](paramRepTree)
    reify { println(s"${paramRepExpr.splice}: ${param.splice}") }
  }
}
