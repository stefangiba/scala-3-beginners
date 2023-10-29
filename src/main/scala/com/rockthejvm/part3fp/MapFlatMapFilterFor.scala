package com.rockthejvm.part3fp

import scala.collection.View.Empty

object MapFlatMapFilterFor {

  // standard list
  val aList          = List(1, 2, 3) // [1] -> [2] -> [3] -> Nil // [1, 2, 3]
  val firstElement   = aList.head
  val restOfElements = aList.tail

  // map
  val anIncrementedList: Seq[Int] = aList.map(_ + 1)

  // filter
  val onlyOddNumbers = aList.filter(_ % 2 != 0)

  // flatMap
  val toPair          = (x: Int) => List(x, x + 1)
  val aFlatMappedList = aList.flatMap(toPair) // [1, 2, 2, 3, 3, 4]

  // all the possible combinations of all the elements of those lists,
  // int the format "1a - black"
  val numbers = List(1, 2, 3, 4)
  val chars   = List('a', 'b', 'c', 'd')
  val colors  = List("black", "white", "red")

  val combined = numbers
    .withFilter(_ % 2 == 0)
    .flatMap(number =>
      chars.flatMap(char => colors.map(color => s"$number$char - $color"))
    )

  // for-comprehension = IDENTICAL to flatMap + map chains
  val combined_v2 = for {
    number <- numbers if number % 2 == 0 // generator
    char   <- chars
    color  <- colors
  } yield s"$number$char - $color" // an EXPRESSION

  // for comprehensions with Unit
  // if foreach

  /* Exercises
   *   1. LinkedList supports for comprehensions?
   *   2. A small collection of at most 1 element - Maybe[A]
   *      - map
   *      - flatMap
   *      - filter
   */

  import com.rockthejvm.practice.{Cons, Empty, LinkedList}
  val lSimpleNumbers: LinkedList[Int] = Cons(1, Cons(2, Cons(3, Empty())))
  val incrementedNumbers              = lSimpleNumbers.map(_ + 1)
  val filteredNumbers                 = lSimpleNumbers.filter(_ % 2 == 0)
  val toPairList        = (x: Int) => Cons(x, Cons(x + 1, Empty()))
  val flatMappedNumbers = lSimpleNumbers.flatMap(toPairList)
  val combinations = for {
    num  <- lSimpleNumbers if num % 2 == 0
    char <- Cons('a', Cons('b', Cons('c', Empty())))
  } yield s"$char-$num"

  def main(args: Array[String]): Unit = {
    numbers.foreach(println)
    for {
      num <- numbers
    } println(num)

    println(combined)
    println(combined_v2)
    println(incrementedNumbers)
    println(filteredNumbers)
    println(flatMappedNumbers)
    println(combinations)
  }
}
