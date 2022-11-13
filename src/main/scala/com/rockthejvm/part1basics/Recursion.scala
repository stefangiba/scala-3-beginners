package com.rockthejvm.part1basics

object Recursion {
  // "repetition" = RECURSION

  def sumUntil(n: Int): Int =
    if (n <= 0) 0
    else n + sumUntil(n - 1)

  def sumUntil_v2(n: Int): Int = {
    def sumUntilTailrec(x: Int, accumulator: Int): Int =
      if (x <= 0) accumulator
      else
        sumUntilTailrec(x - 1, accumulator + x) // TAIL recursion = recursive calloccurs LAST in its code path

    sumUntilTailrec(n, 0)
  }

  def sumBetween(a: Int, b: Int): Int = {
    def sumBetweenTailrec(x: Int, y: Int, accumulator: Int): Int =
      if (x == y) accumulator + x
      else sumBetweenTailrec(x + 1, y, accumulator + x)

    if (a < b) sumBetweenTailrec(a, b, 0)
    else sumBetweenTailrec(b, a, 0)
  }

  def repeat(str: String, times: Int): String = {
    def repeatTailrec(x: Int, acc: String): String =
      if (x == 0) acc
      else repeatTailrec(x - 1, acc + str)

    repeatTailrec(times, "")
  }

  def fibonacci(n: Int): Int = {
    def fibonacciTailrec(x: Int, y: Int, times: Int): Int =
      if (times == 0) then y
      else fibonacciTailrec(y, x + y, times - 1)

    if (n < 1) 0
    else if (n <= 2) 1
    else fibonacciTailrec(1, 1, n - 2)
  }
  def main(args: Array[String]): Unit = {
    println(sumUntil_v2(200000))
    println(sumBetween(4, 2))
    println(repeat("Scala", 5))
    println(fibonacci(7))
  }
}
