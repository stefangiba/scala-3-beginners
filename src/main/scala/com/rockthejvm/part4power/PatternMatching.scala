package com.rockthejvm.part4power

import scala.util.Random

object PatternMatching {

  // switch on steroids
  val random = new Random()
  val value  = random.nextInt(100)

  val description = value match {
    case 1 => "the first"
    case 2 => "the second"
    case 3 => "the third"
    case _ => s"something else $value"
  }

  case class Person(name: String, age: Int)
  val bob = Person("Bob", 20)

  val greeting = bob match {
    case Person(n, a) if a < 18 => s"Hi, my name is $n and I'm $a years old."
    case Person(n, a) =>
      s"Hello there, my name is $n and I'm not allowed to say my age."
    case _ => "I don't know who I am"
  }

  sealed class Animal
  case class Dog(breed: String)     extends Animal
  case class Cat(meowStyle: String) extends Animal

  val animal: Animal = Dog("Terra Nova")
  val animalPatternMatch = animal match {
    case Dog(breed) => "I've detected a dog."
    case Cat(meow)  => "I've detected a cat"
  }

  sealed trait Expr
  case class Number(n: Int)           extends Expr
  case class Sum(e1: Expr, e2: Expr)  extends Expr
  case class Prod(e1: Expr, e2: Expr) extends Expr

  def show(expr: Expr): String = expr match {
    case Number(n)        => s"$n"
    case Sum(left, right) => show(left) + " + " + show(right)
    case Prod(left, right) => {
      def maybeShowParantheses(exp: Expr) = exp match {
        case Prod(_, _) => show(exp)
        case Number(_)  => show(exp)
        case Sum(_, _)  => s"(${show(exp)})"
      }

      maybeShowParantheses(left) + " * " + maybeShowParantheses(right)
    }
  }

  def main(args: Array[String]): Unit = {
    println(description)
    println(greeting)

    println(show(Sum(Number(2), Number(3))))
    println(show(Sum(Sum(Number(2), Number(3)), Number(4))))
    println(show(Prod(Sum(Number(2), Number(3)), Number(4))))
    println(show(Sum(Prod(Number(2), Number(3)), Number(4))))
  }
}
