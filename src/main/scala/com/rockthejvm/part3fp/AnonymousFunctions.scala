package com.rockthejvm.part3fp

object AnonymousFunctions {
  // instances of FunctionN
  val doubler: Int => Int = new Function1[Int, Int] {
    override def apply(x: Int): Int = x * 2
  }

  // lambdas = anonymous function instances
  val doubler_v2: Int => Int   = (x: Int) => x * 2 // identical
  val adder: (Int, Int) => Int = (x: Int, y: Int) => x + y

  // zero arg functions
  val justDoSomething: () => Int = () => 45
  val anInvocation               = justDoSomething()

  // alternative syntax with curly braces
  val stringToInt = { (str: String) =>
    // implementation: code block
    str.toInt
  }

  // type inference
  val doubler_v3: Int => Int      = x => x * 2 // type inferred by the compiler
  val adder_v2: (Int, Int) => Int = (x, y) => x + y

  // shortest lambdas
  val doubler_v4: Int => Int      = _ * 2
  val adder_v3: (Int, Int) => Int = _ + _
  // each underscore is a different argument, you can't reuse them

  /**
    * Exercises
    * 1. Replace all FunctionN instantiations with Lambdas in LinkedList.
    * 2. Rewrite the `superAdder` from WhatsAFunction using Lambdas.
    */

  def main(args: Array[String]): Unit = {
    println(justDoSomething)
    println(justDoSomething())
  }
}
