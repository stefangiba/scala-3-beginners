package com.rockthejvm.part4power

object PatternsEverywhere {

  // big idea #1: catches are actually MATCHES
  val potentialFailure =
    try {
      // code
    } catch {
      case e: RuntimeException       => "runtime exception"
      case npe: NullPointerException => "npe"
      case _                         => "some other exception"
    }

  // big idea #2: for comprehensions (generators) are based on PM
  val list = List(1, 2, 3, 4)
  val evenNumbers = for {
    n <- list if n % 2 == 0
  } yield 10 * n

  val tuples = List((1, 2), (3, 4))
  val filterTuples = for {
    (first, second) <- tuples if first < 3
  } yield (first, second)

  // big idea #3: new syntax
  val tuple     = (1, 2, 3)
  val (a, b, c) = tuple

  val head :: tail = tuples

  def main(args: Array[String]): Unit = {}
}
