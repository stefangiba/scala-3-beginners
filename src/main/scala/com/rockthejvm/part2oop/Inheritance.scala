package com.rockthejvm.part2oop

object Inheritance {

  class Animal {
    val creatureType = "wild"
    def eat(): Unit  = println("omnomnom")
  }

  class Cat extends Animal {
    def crunch() = {
      eat()
      println("crunch, crunch")
    }
  }

  class Dog extends Animal {
    override val creatureType: String = "domestic"

    override def eat(): Unit      = println("mmm, I like this bone")
    override def toString: String = "a dog"
  }

  val cat = new Cat

  class Person(val name: String, age: Int) {
    def this(name: String) = this(name, 0)
  }

  class Adult(name: String, age: Int, idCard: String) extends Person(name, age)

  // subtype polymorphism
  val dog = new Dog

  // overloading vs overriding
  class Crocodile extends Animal {
    override val creatureType: String = "very wild"

    override def eat(): Unit      = println("I can eat anything, I am a croc")
    def eat(animal: Animal): Unit = println("I'm eating this poor fella")
    def eat(dog: Dog): Unit       = println("eating a dog")
    def eat(person: Person): Unit = println(
      s"I'm eating a human with the name ${person.name}"
    )
    def eat(person: Person, dog: Dog): Unit = println(
      "I'm eating a human and the dog"
    )
    def eat(dog: Dog, person: Person): Unit = println(
      "I'm eating a human and the dog"
    )
//    def eat(): Int = 45 => NOT VALID
  }

  def main(args: Array[String]): Unit = {
    cat.eat()
    dog.eat()
    println(dog) // dog.toString

    val croc   = new Crocodile
    val stefan = new Person("Stefan", 23)
    croc.eat(dog, stefan)
  }
}
