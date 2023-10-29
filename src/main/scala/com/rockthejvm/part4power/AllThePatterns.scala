package com.rockthejvm.part4power

import com.rockthejvm.practice.{LinkedList, Cons, Empty}

object AllThePatterns {
  object MySingleton

  // 1 - constants
  val someValue: Any = "Scala"
  val constants = someValue match {
    case 42          => "a number"
    case "Scala"     => "THE Scala"
    case true        => "the truth"
    case MySingleton => "a singleton objects"
  }

  // 2 - match anything
  val matchAnythingVar = someValue match {
    case something => s"I've matched anything, it's $something"
  }

  val matchAnything = someValue match {
    case _ => "I can match anything at all"
  }

  // 3 - match tuples
  val tuple = (1, 4)
  val matchTuple = tuple match {
    case (1, somethingElse) => s"A tuple with 1 and $somethingElse"
    case (something, 2)     => s"A tuple with 2 as its second fields"
  }

  // Pattern Match structures can be NESTED
  val nestedTuple = (1, (2, 3))
  val matchNestedTuple = nestedTuple match {
    case (_, (2, v)) => "A nested tuple ..."
  }

  // 4 - case classes
  val list: LinkedList[Int] = Cons(1, Cons(2, Empty()))
  val matchList = list match {
    case Empty()                   => "An empty list"
    case Cons(head, Cons(_, tail)) => s"a non-empty list starting with $head"
  }

  val option: Option[Int] = Option(2)
  val matchOption = option match {
    case None        => "an empty option"
    case Some(value) => s"non-empty, got $value"
  }

  // 5 - list patterns
  val standardList = List(1, 2, 3, 42)
  val matchStandardList = standardList match {
    case List(1, _, _, _)    => "list with 4 elements, first is 1"
    case List(1, _*)         => "list starting with 1, arbitrarily large"
    case List(1, 2, _) :+ 42 => "list ending in 42"
    case head :: tail        => "deconstructed list"
  }

  // 6 - type specifiers
  val unknown: Any = 2
  val matchTyped = unknown match {
    case anInt: Int      => s"I matched an Int, I can add 2 to it: ${anInt + 2}"
    case aString: String => "I matched a String"
    case _: Double       => "I matched a double I don't care about"
  }

  // 7 - name binding
  val bindingNames = list match {
    case Cons(head, rest @ Cons(_, tail)) =>
      s"Can use $rest"
  }

  // 8 - chained patterns
  val multiMatch = list match {
    case Empty() | Cons(0, _) => "an empty list to me"
    case _                    => "anything else"
  }

  // 9 - if guards
  val secondElementSpecial = list match {
    case Cons(_, Cons(specialElement, _)) if specialElement > 5 =>
      "second element is big enough"
    case _ => "anything else"
  }

  /** Example
    */
  val simpleInt = 45
  val isEven_bad = simpleInt match {
    case n if n % 2 == 0 => true
    case _ => false
  }
  val isEven_bad_v2 = if (simpleInt % 2 == 0) true else false
  val isEven        = simpleInt % 2 == 0

  /** Exercise (trick)
    */
  val numbers = List(1, 2, 3, 4)
  val numbersMatch = numbers match {
    case listOfStrings: List[String] => "a list of strings"
    case listOfInts: List[Int]       => "a list of numbers"
  }

  /* PM runs at runtime
   * - reflection
   * - generic types are erased at runtime
   * List[String] => List
   * List[Int] => List
   * Function1[Int, String] => Function1
   *
   */

  def main(args: Array[String]): Unit = {
    println(numbersMatch) // "a list of strings"
  }
}
