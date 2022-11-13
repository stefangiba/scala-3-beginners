package com.rockthejvm.part1basics

import scala.annotation.tailrec

object Functions {

  def aFunction(a: String, b: Int): String = a + " " + b.toString

  val aFunctionInvocation = aFunction("Scala", 9999999)

  def aNoArgFunction(): Int = 45
  val aParameterlessFunction: Int = 45

  // functions can be recursive
  // when you need loops, use RECURSION
  def stringConcatenation(str: String, n: Int): String =
    if (n == 0) ""
    else if (n == 1) str
    else str + stringConcatenation(str, n - 1)

  val scalax3 = stringConcatenation("Scala", 3)

  // "void" functions
  def aVoidFUnction(aString: String): Unit = println(aString)

  def computeDoubleStringWithSideEffect(aString: String): String = {
    aVoidFUnction(aString) // Unit
    aString + aString // meaningful value
  } // discouraging side effects

  def aBigFunction(n: Int): Int = {
    def aSmallerFunction(a: Int, b: Int): Int = a + b

    aSmallerFunction(n, n + 1)
  }

  // Exercises
  // 1. A greeting function (name, age) => "Hi my name is $name and I am $age years old"
  // 2. Factorial function
  // 3. Fibonacci function (returns Nth Fibonacci number)
  // 4. Test if a number is prime

  def greeting(name: String, age: Int): String =
    s"Hi, my name is $name and I am $age years old."

  def factorial(n: Int): Int =
    if (n < 1) 0
    else if (n == 1) 1
    else n * factorial(n - 1)

  def fibonacci(n: Int): Int =
    if (n < 1) 0
    else if (n <= 2) 1
    else fibonacci(n - 1) + fibonacci(n - 2)

  def isPrime(n: Int): Boolean =
    @tailrec
    def isPrimeUtil(x: Int): Boolean =
      if (x > n / 2) true
      else if (n % x == 0) false
      else isPrimeUtil(x + 1)

    isPrimeUtil(2)

  def main(args: Array[String]): Unit = {
    println(aFunctionInvocation)
    println(aNoArgFunction())
    println(scalax3)
    println(fibonacci(7))
    println(factorial(5))
    println(greeting("Stefan", 23))
    println(isPrime(9))
  }
}
