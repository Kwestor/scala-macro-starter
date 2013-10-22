package com.jtm

import scala.reflect.macros.Context
import scala.language.experimental.macros

object Tuples {

  def fields[Tup <: Product](tuple: Tup): Seq[String] = macro fields_impl[Tup]

  def fields_impl[Tup <: Product : c.WeakTypeTag](c: Context)(tuple: c.Expr[Tup]): c.Expr[Seq[String]] = {
    import c.universe._
    val fields: List[Tree] = weakTypeOf[Tup].members
      .filter(t => t.isTerm && t.asTerm.isCaseAccessor && t.asTerm.isGetter)
      .toList.sortBy(_.fullName)
      .map(f => q"$tuple.${f.name.toTermName}.toString")

    val tree = c.typeCheck(q"List(..$fields)")
    c.Expr(tree)(c.WeakTypeTag(tree.tpe))
  }

  def tail[Tup <: Product](tuple: Tup): Product = macro tail_impl[Tup]

  def tail_impl[Tup <: Product : c.WeakTypeTag](c: Context)(tuple: c.Expr[Tup]) = {
    import c.universe._
    val fields = weakTypeOf[Tup].members
      .filter(t => t.isTerm && t.asTerm.isCaseAccessor && t.asTerm.isGetter)
      .toList.sortBy(_.fullName)
      .map(f => q"$tuple.${f.name.toTermName}")
      .tail
    val length = weakTypeOf[Tup].typeSymbol.asClass.typeParams.length
    def tupleName(l: Int): TermName = definitions.TupleClass(l).name.toTermName
    val tree = c.typeCheck(q"${tupleName(length - 1)}(..$fields)")
    c.Expr(tree)(c.WeakTypeTag(tree.tpe))
  }

  def head[Tup <: Product](tuple: Tup): Any = macro head_impl[Tup]

  def head_impl[Tup <: Product : c.WeakTypeTag](c: Context)(tuple: c.Expr[Tup]) = {
    import c.universe._
    val tree = c.typeCheck(q"$tuple._1")
    c.Expr(tree)(c.WeakTypeTag(tree.tpe))
  }

}