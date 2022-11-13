package com.rockthejvm.part2oop

object CaseClasses {

  // lightweight data structures
  case class Person(name: String, age: Int) {
    // do some other stuff
  }

  // 1 - case class args are now fields
  val stefan     = new Person("Stefan", 23)
  val stefansAge = stefan.age

  // 2 - toString, equals and hashCode
  val stefanToString = stefan.toString
  val stefanDuped    = new Person("Stefan", 23)
  val isSameStefan   = stefan == stefanDuped

  // 3 - utility methods
  val stefanYounger = stefan.copy(age = 18) // new Person("Stefan", 18)

  // 4 - CCs have companion objects
  val thePersonSingleton = Person
  val stefan_v2          = Person.apply("Stefan", 23)
  val stefan_v3          = Person("Stefan", 23)

  // 5 - CCs are serializable
  // Akka is a good use case

  // 6 - CCs have extractor patterns for PATTERN MATCHING

  // ILLEGAL
  // case class CCWithNoArgs {
  //   // some code
  // }
  // CCs need to have arguments

  case object UnitedKingdom {
    def name = "The UK of GB and NI"
  }

  case class CCWithArgListNoArgs[
      A
  ]() // legal, useful in the context of generics
  // the following are equal
  val ccna    = new CCWithArgListNoArgs
  val ccna_v2 = new CCWithArgListNoArgs

  def main(args: Array[String]): Unit = {
    println(stefan)
    println(isSameStefan)
  }
}
