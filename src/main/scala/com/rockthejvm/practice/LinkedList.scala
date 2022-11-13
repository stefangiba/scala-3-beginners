package com.rockthejvm.practice

import scala.annotation.tailrec
import java.{util => ju}

// singly linked list
// [1, 2, 3] = [1] -> [2] -> [3] -> |

trait Predicate[T] {
  def test(elem: T): Boolean
}

trait Transformer[A, B] {
  def transform(elem: A): B
}

abstract class LinkedList[A] {
  def head: A
  def tail: LinkedList[A]
  def isEmpty: Boolean

  infix def ++(anotherList: LinkedList[A]): LinkedList[A]
  def reverse: LinkedList[A]
  def filter(predicate: Predicate[A]): LinkedList[A]
  def map[B](transformer: Transformer[A, B]): LinkedList[B]
  def flatMap[B](transformer: Transformer[A, LinkedList[B]]): LinkedList[B]
  def add(element: A): LinkedList[A] = Cons(element, this)
}

case class Empty[A]() extends LinkedList[A] {
  override def head: A             = throw new NoSuchElementException
  override def tail: LinkedList[A] = throw new NoSuchElementException
  override def isEmpty: Boolean    = true
  override def toString: String    = "[]"
  override def ++(anotherList: LinkedList[A]): LinkedList[A]  = anotherList
  override def reverse: LinkedList[A]                         = Empty()
  override def filter(predicate: Predicate[A]): LinkedList[A] = Empty()
  override def map[B](transformer: Transformer[A, B]): LinkedList[B] =
    Empty()
  override def flatMap[B](
      transformer: Transformer[A, LinkedList[B]]
  ): LinkedList[B] = Empty()
}

case class Cons[A](override val head: A, override val tail: LinkedList[A])
    extends LinkedList[A] {
  override def isEmpty: Boolean = false

  override def toString(): String = {
    @tailrec def concatenateElements(
        remainder: LinkedList[A],
        acc: String
    ): String =
      if (remainder.isEmpty) acc
      else concatenateElements(remainder.tail, s"$acc, ${remainder.head}")

    s"[${concatenateElements(this.tail, s"$head")}]"
  }

  override infix def ++(anotherList: LinkedList[A]): LinkedList[A] = {
    @tailrec def go(
        remainder: LinkedList[A],
        acc: LinkedList[A]
    ): LinkedList[A] =
      if (remainder.isEmpty) acc
      else go(remainder.tail, Cons(remainder.head, acc))

    go(this.reverse, anotherList)
  }

  override def reverse: LinkedList[A] = {
    @tailrec def go(
        remainder: LinkedList[A],
        acc: LinkedList[A]
    ): LinkedList[A] =
      if (remainder.isEmpty) acc
      else go(remainder.tail, Cons(remainder.head, acc))

    go(this.tail, Cons(this.head, Empty()))
  }

  override def filter(predicate: Predicate[A]): LinkedList[A] = {
    @tailrec def go(
        remainder: LinkedList[A],
        acc: LinkedList[A]
    ): LinkedList[A] =
      if (remainder.isEmpty) acc
      else if (predicate.test(remainder.head))
        go(remainder.tail, Cons(remainder.head, acc))
      else go(remainder.tail, acc)

    go(this, Empty()).reverse
  }

  override def map[B](transformer: Transformer[A, B]): LinkedList[B] = {
    @tailrec def go(
        remainder: LinkedList[A],
        acc: LinkedList[B]
    ): LinkedList[B] =
      if (remainder.isEmpty) acc
      else
        go(remainder.tail, Cons(transformer.transform(remainder.head), acc))

    go(
      this.tail,
      Cons(transformer.transform(this.head), Empty())
    ).reverse
  }

  override def flatMap[B](
      transformer: Transformer[A, LinkedList[B]]
  ): LinkedList[B] = {
    @tailrec def go(
        remainder: LinkedList[A],
        acc: LinkedList[B]
    ): LinkedList[B] =
      if (remainder.isEmpty) acc
      else go(remainder.tail, transformer.transform(remainder.head) ++ acc)

    go(this.reverse, Empty())
  }
}

object LinkedList {
  def find[A](list: LinkedList[A], predicate: Predicate[A]): A =
    if (list.isEmpty) throw new NoSuchElementException
    else if (predicate.test(list.head)) list.head
    else find(list.tail, predicate)
}

object LinkedListTest {
  def main(args: Array[String]): Unit = {
    val empty = Empty[Int]()
    println(empty.isEmpty)
    println(empty)

    val first3Numbers   = Cons(1, Cons(2, Cons(3, empty)))
    val first3NumbersV2 = empty.add(1).add(2).add(3)
    println(first3Numbers)
    println(first3Numbers.isEmpty)
    println(first3NumbersV2)
    println(first3NumbersV2.isEmpty)

    val otherNumbers = Cons(1, Cons(2, Cons(3, Cons(4, empty))))
    println(otherNumbers)
    println(otherNumbers.isEmpty)

    val someStrings = Cons("dog", Cons("cat", Empty()))
    println(someStrings)
    println(someStrings.reverse)
    println(otherNumbers.reverse)

    val evenPredicate = new Predicate[Int] {
      override def test(elem: Int): Boolean = elem % 2 == 0
    }

    val doublerTransformer = new Transformer[Int, Int] {
      override def transform(elem: Int): Int = elem * 2
    }

    val replicateAndIncrementTransformer =
      new Transformer[Int, LinkedList[Int]] {
        override def transform(elem: Int): LinkedList[Int] =
          Cons(elem, Cons(elem + 1, Empty()))
      }

    println(otherNumbers.map(doublerTransformer))
    println(otherNumbers.filter(evenPredicate))
    println(otherNumbers ++ first3Numbers)
    println(otherNumbers.flatMap(replicateAndIncrementTransformer))

    // find test
    println(LinkedList.find(first3Numbers, evenPredicate))
    println(LinkedList.find(empty, evenPredicate))
  }
}
