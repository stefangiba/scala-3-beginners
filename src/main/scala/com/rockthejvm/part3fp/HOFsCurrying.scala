package com.rockthejvm.part3fp

import scala.annotation.tailrec

object HOFsCurrying {

  // higher order functions (HOFs)
  val aHof: (Int, (Int => Int)) => Int = (x, func) => func(x)
  val anotherHof: Int => (Int => Int)  = x => (y => y + 2 * x)

  // quick exercise
  val superFunction: (Int, (String, (Int => Boolean)) => Int) => (Int => Int) =
    (x, func) => (y => x + y)

  // examples: map, flatMap, filter

  // more examples
  @tailrec
  def nTimes(f: Int => Int, n: Int, x: Int): Int =
    if (n <= 0) x
    else nTimes(f, n - 1, f(x))

  val plusOne     = (x: Int) => x + 1
  val tenThousand = nTimes(plusOne, 10000, 0)

  // def nTimes_v2(f: Int => Int, n: Int): Int => Int =
  //   if (n <= 0) x => x
  //   else (x: Int) => nTimes_v2(f, n - 1)(f(x))
  def nTimes_v2(f: Int => Int, n: Int): Int => Int =
    @tailrec
    def go(times: Int, acc: Int): Int =
      if (times <= 0) acc
      else go(times - 1, f(acc))

    x => go(n, x)

  val plusOneThousand = nTimes_v2(plusOne, 1000000)

  // currying
  val superAdder: Int => Int => Int = x => y => x + y
  val add3                          = superAdder(3)

  // curried methods = methods with multiple argument lists
  def curriedFormatter(fmt: String)(x: Double) =
    fmt.format(x)

  val standardFormat = curriedFormatter("%4.3f")
  val preciseFormat  = curriedFormatter("%10.8f")

  /**   1. LinkedList exercises: \- foreach(A => Unit) [1, 2, 3].foreach(x =>
    *      println(x))
    *
    * \- sort((A, A) => Int): LinkedList[A] [3, 2, 4, 1].sort((x, y) => x - y)
    * (hint: use insertion sort)
    *
    * \- zipWith[B](LinkedList[A], (A, A) => B): LinkedList[B] [1, 2,
    * 3].zipWith([4, 5, 6], x * y) => [1 * 4, 2 * 5, 3 * 6]
    *
    *   - foldLeft[B](start: B)((A, B) => B): B
    *
    * 2. curry(f: (Int, Int) => Int): Int => Int => Int 3
    *   - uncurry (f: Int => Int => Int): (Int, Int) => Int
    */

  def curry[A, B, C](f: (A, B) => C): A => B => C =
    x => y => f(x, y)

  def uncurry[A, B, C](f: A => B => C): (A, B) => C =
    (a: A, b: B) => f(a)(b)

  def compose[A, B, C](f: B => C, g: A => B): A => C =
    x => f(g(x))

  def andThen[A, B, C](f: A => B, g: B => C): A => C =
    x => g(f(x))

  val simpleAdder          = curry[Int, Int, Int](_ + _)
  val simpleAdder_v2       = uncurry(simpleAdder)
  val incrementer          = (x: Int) => x + 1
  val doubler              = (x: Int) => x * 2
  val composedApplication  = compose(incrementer, doubler)
  val sequencedApplication = andThen(incrementer, doubler)

  def main(args: Array[String]): Unit = {
    println(tenThousand)
    println(plusOneThousand(1))
    println(standardFormat(Math.PI))
    println(preciseFormat(Math.PI))
    println(simpleAdder(1)(2))
    println(simpleAdder_v2(1, 2))
    println(composedApplication(42))
    println(sequencedApplication(42))
  }
}
