package com.rockthejvm.part3fp

object TuplesMaps {

  // tuples = finite ordered "lists"
  val tuple: (Int, String) = (2, "Rock the JVM") // Tuple2[Int, String]
  val firstField           = tuple._1
  val copiedTuple          = tuple.copy(_1 = 54)

  // tuples of 2 elements
  val tuple_v2 = 2 -> "Rock the JVM" // IDENTICAL to (2, "Rock the JVM")

  // Maps: Keys -> Values
  val map = Map()
  val phoneBook: Map[String, Int] = Map(
    "Jim"    -> 555,
    "Daniel" -> 789,
    "Jane"   -> 123
  ).withDefaultValue(-1)

  // core APIs
  val phoneBookHasDaniel = phoneBook.contains("Daniel")
  val marysPhoneNumber = phoneBook(
    "Mary"
  ) // crashes with an Exception if no default value is set

  // add a pair
  val newPair      = "Mary" -> 678
  val newPhoneBook = phoneBook + newPair // returns a new Map

  // remove a key
  val phoneBookWithoutDaniel = phoneBook - "Daniel" // returns a new Map

  // list -> map
  val linearPhoneBook = List("Jim" -> 555, "Daniel" -> 789, "Jane" -> 123)
  val phoneBook_v2    = linearPhoneBook.toMap

  // map -> linear collection
  val linearPhonebook_v2 =
    phoneBook_v2.toList // toSeq, toVector, toArray, toSet

      // map, flatMap, filter
  val aProcessedPhonebook =
    phoneBook.map(pair => (pair._1.toUpperCase(), pair._2))
  val noJs = phoneBook.view.filterKeys(!_.startsWith("J")).toMap

  // mapping values
  val prefixNumbers = phoneBook.view.mapValues(number => s"0255-$number").toMap

  // other collections can create maps
  val names         = List("Bob", "James", "Angela", "Mary", "Daniel", "Jim")
  val nameGroupings = names.groupBy(name => name.charAt(0))

  def main(args: Array[String]): Unit = {
    println(phoneBook)
    println(phoneBookHasDaniel)
    println(marysPhoneNumber)
    println(nameGroupings)
  }
}
