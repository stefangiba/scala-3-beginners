package com.rockthejvm.part2oop

object Exceptions {
  val aString: String = null
  // aString.length crashes with a NPE

  // 1 - throw Exceptions
  // val aWeirdValue: Int = throw new NullPointerException // returns Nothing

  // type Throwable
  // Error, eg. SOError (StackOverflow), OOMError (Out of memory)
  // Exception e.g. NPException, NSEException (NoSuchElement) ...

  def getInt(withExceptions: Boolean): Int =
    if (withExceptions) throw new RuntimeException("No int for you!")
    else 42

  val potentialFail =
    try {
      // code that might fail
      getInt(true)
    } catch {
      // place the most specific exceptions first
      case e: RuntimeException     => 54
      case e: NullPointerException => 35
    } finally {
      // executed no matter what
      // closing resources
      // Unit here
    }

  class MyException extends RuntimeException {
    // fields or methods
    override def getMessage(): String = "MY EXCEPTION"
  }

  val myException = new MyException

  /** Exercises:
    *
    *   1. Crash with SOError 2. Crash with OOMError 3. Find an element matching
    *      a predicate in LinkedList
    */

  def soCrash(): Unit = {
    def infinite(): Int = 1 + infinite()

    infinite()
  }

  def oomCrash(): Unit = {
    def bigString(n: Int, acc: String): String = 
      if (n == 0) acc
      else bigString(n - 1, acc + acc)

    bigString(56175363, "Scala")
  }

  def main(args: Array[String]): Unit = {
    println(potentialFail)
    // val throwingMyException = throw myException
    
    // soCrash() 
    oomCrash()
  }
}
