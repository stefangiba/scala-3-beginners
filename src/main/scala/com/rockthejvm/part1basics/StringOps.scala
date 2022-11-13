package com.rockthejvm.part1basics

object StringOps {
  val aString: String = "Hello, I am learning Scala"

  // string functions
  val secondChar      = aString.charAt(1)
  val firstWord       = aString.substring(0, 5)
  val words           = aString.split(" ")
  val startsWithHello = aString.startsWith("Hello")
  val allDashes       = aString.replace(" ", "-")
  val allUpperCase    = aString.toUpperCase()
  val allLowerCase    = aString.toLowerCase()

  // other functions
  val reversed      = aString.reverse
  val aBunchOfChars = aString.take(10)

  // parse to numeric
  val numberAsString = "2"
  val number         = numberAsString.toInt

  // interpolation
  val name        = "Alice"
  val age         = 15
  val greeting    = s"Hello, I'm $name and I am $age years old!"
  val greeting_v2 = s"Hello, I'm $name and I will be turning ${age + 1} years old!"

  // f-interpolation
  val speed = 1.2f
  val myth  = f"$name can eat $speed%.5f burgers per minute."

  // raw-interpolation
  val escapes = raw"This is a \n newline."

  def main(args: Array[String]): Unit = {
    println(secondChar)
    println(firstWord)
    println(words)
    println(startsWithHello)
    println(allDashes)
    println(allUpperCase)
    println(allLowerCase)
    println(aString.length)
    println(reversed)
    println(aBunchOfChars)
    println(number)
    println(greeting)
    println(greeting_v2)
    println(myth)
    println(escapes)
  }
}
