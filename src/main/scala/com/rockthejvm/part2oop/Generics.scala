package com.rockthejvm.part2oop

object Generics {

  abstract class MyList[A] { // "generic" list - Java: abstract class MyList<A>
    def head: A
    def tail: MyList[A]
  }

  class Empty[A] extends MyList[A] {
    override def head: A         = throw new NoSuchElementException
    override def tail: MyList[A] = throw new NoSuchElementException
  }

  class NonEmpty[A](override val head: A, override val tail: MyList[A])
      extends MyList[A]

  val listOfIntegers: MyList[Int] =
    new NonEmpty[Int](1, new NonEmpty(2, new Empty[Int]))
  val listOfStrings =
    new NonEmpty[String]("Scala", new NonEmpty("Java", new Empty[String]))

  val firstNumber: Int = listOfIntegers.head
  val adding: Int      = firstNumber + 3

  // multiple generic types
  trait MyMap[K, V]

  object MyList {
    def from2Elements[A](firstElem: A, secondElem: A): MyList[A] =
      new NonEmpty[A](firstElem, new NonEmpty[A](secondElem, new Empty[A]))
  }

  // calling methods
  val first2Numbers    = MyList.from2Elements[Int](1, 2)
  val first2Numbers_v2 = MyList.from2Elements(1, 2)
  val first2Numbers_v3 = new NonEmpty(1, new NonEmpty(2, new Empty))

  /** genericize LinkedList
    */

  def main(args: Array[String]): Unit = {}
}
