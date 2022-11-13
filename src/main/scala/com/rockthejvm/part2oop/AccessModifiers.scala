package com.rockthejvm.part2oop

object AccessModifiers {

  class Person(val name: String) {
    // PROTECTED == access within the class + children classes
    protected def sayHi(): String = s"Hi, my name is $name!"
    // PRIVATE == only within the class
    private def watchNetflix(): String =
      "I am binge watchig my favorite series..."
  }

  class Kid(override val name: String, age: Int) extends Person(name) {
    // no modifier == PUBLIC
    def greetPolitely(): String =
      sayHi() + " I love to play!"
  }

  val aPerson = new Person("Alice")
  val aKid    = new Kid("David", 5)

  // complication
  class KidWithParents(
      override val name: String,
      age: Int,
      momName: String,
      dadName: String
  ) extends Person(name) {
    val mom = new Person(momName)
    val dad = new Person(dadName)

    // NOT LEGAL
//    def everyoneSayHi(): String =
//      this.sayHi() + s"Hi, I'm $name, and here are my parents: " + mom
//        .sayHi() + dad.sayHi()
  }

  def main(args: Array[String]): Unit = {
    println(aKid.greetPolitely())
  }
}
