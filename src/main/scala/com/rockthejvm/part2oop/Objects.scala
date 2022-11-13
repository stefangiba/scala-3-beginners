package com.rockthejvm.part2oop

object Objects {

  object MySingleton { // type + the only instance of this type
    val aField          = 42
    def aMethod(x: Int) = x + 1
  }

  val theSingleton     = MySingleton
  val anotherSingleton = MySingleton
  val isSameSingleton  = theSingleton == anotherSingleton

  // objects an have fields and methods
  val theSingletonField      = MySingleton.aField
  val theSingletonMethodCall = MySingleton.aMethod(99)

  class Person(name: String) {
    def sayHi(): String = s"Hi, my name is $name!"
  }

  // companions
  object Person { // companion object
    // can access each other's private fields and methods
    val N_EYES = 2

    def canFly(): Boolean = false
  }

  val mary          = new Person("Mary")
  val mary_v2       = new Person("Mary")
  val marysGreeting = mary.sayHi()

  // methods and fields in objects are used for instance-independent functionality - "static"
  val humansCanFly = Person.canFly()
  val nEyesHuman   = Person.N_EYES

  // equality
  // 1 - equality of reference (mary == mary) (mary != mary_v2)
  // usually defined as ==
  val sameMary      = mary eq mary_v2
  val sameSingleton = MySingleton eq MySingleton
  // 2 - equality of "sameness" - in Java defined as .equals
  val sameMary_v2      = mary equals mary_v2        // false
  val sameMary_v3      = mary == mary_v2            // same as equals
  val sameSingleton_v2 = MySingleton == MySingleton // true

  // objects can extend classes
  object BigFoot extends Person("BigFoot")

  // Scala application = Object + def main(args: Array[String]): Unit
  def main(args: Array[String]): Unit = {
    println(sameMary_v3)
    println(BigFoot.sayHi())
  }
}
