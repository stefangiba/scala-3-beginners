package com.rockthejvm.part3fp

object WhatsAFunction {

  // FP: functions are "first-class" citizens

  trait MyFuntion[A, B] {
    def apply(arg: A): B
  }

  val doubler = new MyFuntion[Int, Int] {
    override def apply(arg: Int): Int = arg * 2
  }

  val meaningOfLife  = 42
  val meaningDoubled = doubler(meaningOfLife)

  val doublerStandard = new Function1[Int, Int] {
    override def apply(v1: Int): Int = v1 * 2
  }

  val meaningDoubled_v2 = doublerStandard(meaningOfLife)

  val adder = new Function2[Int, Int, Int] {
    override def apply(v1: Int, v2: Int): Int = v1 + v2
  }
  val anAddition = adder(2, 57)

  // (Int, String, Double, Boolean) => Int === Funtction4[Int, String, Double, Boolean, Int]
  val aThreeArgFunction = new Function4[Int, String, Double, Boolean, Int] {
    override def apply(v1: Int, v2: String, v3: Double, v4: Boolean): Int = ???
  }

  /** Exercises:
    *   1. A function which takes 2 strings and concatenates them.
    *
    * 2. Modify the Linked List implementation to make Predicate and Transformer
    * into functions instead of traits.
    *
    * 3. Define a function which takes an Int as an arg and returns another
    * function as a result.
    */

  val concat = (firstString: String, secondString: String) =>
    firstString + secondString

  val superAdder = (x: Int) => (y: Int) => x + y

  // function values != methods

  def main(args: Array[String]): Unit = {
    println(concat("I love ", "Scala!"))
    println(superAdder(1)(2))
  }
}
