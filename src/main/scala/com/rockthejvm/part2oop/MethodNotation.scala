package com.rockthejvm.part2oop

import scala.language.postfixOps

object MethodNotation {

  class Person(val name: String, val age: Int, favoriteMovie: String) {
    // infix position
    infix def likes(movie: String): Boolean =
      movie == favoriteMovie

    infix def +(person: Person): String =
      s"${this.name} is hanging out with ${person.name}"

    infix def +(nickname: String): Person =
      new Person(s"$name ($nickname)", age, favoriteMovie)

    infix def !!(programmingLanguage: String): String =
      s"$name wonders how can $programmingLanguage be so cool!"

    // prefix position
    // unary ops: -, +, ~, !
    def unary_- : String = s"$name's alter ego"
    def unary_+ : Person = new Person(name, age + 1, favoriteMovie)

    // postfix notation
    def isAlive: Boolean = true

    def apply(): String =
      s"Hi, my name is $name and I really enjoy $favoriteMovie!"
    def apply(times: Int): String =
      s"$name watched $favoriteMovie 2 times!"
  }

  val mary = new Person("Mary", 34, "Inception")
  val john = new Person("John", 36, "Fight Club")

  val negativeOne = -1

  def main(args: Array[String]): Unit = {
    println(mary.likes("Fight Club"))
    // infix notation - for methods with one argument
    println(mary likes "Fight Club") // identical

    // "operator"
    println(mary + john)
    println(mary.+(john))
    println(2 + 3)
    println(2.+(3))
    println(mary !! "Scala")

    // prefix position
    println(-mary)
    println(mary.unary_-)

    // postfix position
    println(mary.isAlive)
    println(mary isAlive) // discouraged

    // apply is special
    println(mary.apply())
    println(mary()) // same

    // exercise tests
    val maryWithNickname = mary + "the rockstar"
    println(maryWithNickname.name)
    val maryOlder = +mary
    println(maryOlder.age)
    println(mary(2))
  }
}
