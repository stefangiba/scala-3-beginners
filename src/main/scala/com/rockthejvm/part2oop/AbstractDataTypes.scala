package com.rockthejvm.part2oop

object AbstractDataTypes {

  abstract class Animal {
    val creatureType: String // abstract
    // non-abstract fields/methods allowed
    def preferredMeal: String = "anything"
    def eat(): Unit
  }

//  abstract classes can't be instantiated
//  val anAnimal: Animal = new Animal

  // non-abstract classes must implement the abstract fields/methods
  class Dog extends Animal {
    override val creatureType: String = "domestic"
    override def eat(): Unit          = println("crunching this bone")
    // overriding is legal for everything
    // accessor type methods (without `()`) can be overridden by values/fields
    override val preferredMeal: String = "bones"
  }

  val aDog: Animal = new Dog

  // traits
  trait Carnivore { // Scala 3 - traits can have constructor arguments
    def eat(animal: Animal): Unit
  }

  class TRex extends Carnivore {
    override def eat(animal: Animal): Unit = println(
      s"I'm a T-Rex, I am eating a ${animal.creatureType}"
    )
  }

  // practical difference abstract classes vs traits
  // one class inheritance
  // multiple traits inheritance

  trait ColdBlooded
  class Crocodile extends Animal with Carnivore with ColdBlooded {
    override val creatureType: String = "croc"
    override def eat(animal: Animal): Unit = println(
      "I'm a croc, I just crunch stuff"
    )
    override def eat(): Unit = println("croc eating animal")
  }

  // philosophical difference abstract classes vs traits
  // abstract classes are THINGS
  // traits are BEHAVIOURS

  def main(args: Array[String]): Unit = {}
}
