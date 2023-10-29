package com.rockthejvm.practice

import scala.annotation.tailrec
import java.util

// singly linked list
// [1, 2, 3] = [1] -> [2] -> [3] -> |

abstract class LinkedList[A] {
  def head: A
  def tail: LinkedList[A]
  def isEmpty: Boolean

  infix def ++(anotherList: LinkedList[A]): LinkedList[A]
  def reverse: LinkedList[A]
  def filter(predicate: A => Boolean): LinkedList[A]
  def withFilter(predicate: A => Boolean): LinkedList[A] = filter(predicate)
  def map[B](transformer: A => B): LinkedList[B]
  def flatMap[B](transformer: A => LinkedList[B]): LinkedList[B]
  def add(element: A): LinkedList[A] = Cons(element, this)
  def foreach(f: A => Unit): Unit
  def zipWith[B](list: LinkedList[A], f: (A, A) => B): LinkedList[B]
  def foldLeft[B](zero: B)(f: (B, A) => B): B
  def sort(comparator: (A, A) => Int): LinkedList[A]
}

case class Empty[A]() extends LinkedList[A] {
  override def head: A             = throw new NoSuchElementException
  override def tail: LinkedList[A] = throw new NoSuchElementException
  override def isEmpty: Boolean    = true
  override def toString: String    = "[]"
  override def ++(anotherList: LinkedList[A]): LinkedList[A]  = anotherList
  override def reverse: LinkedList[A]                         = Empty()
  override def filter(predicate: A => Boolean): LinkedList[A] = Empty()
  override def map[B](transformer: A => B): LinkedList[B] =
    Empty()
  override def flatMap[B](
      transformer: A => LinkedList[B]
  ): LinkedList[B] = Empty()
  override def foreach(f: A => Unit): Unit = ()
  override def zipWith[B](list: LinkedList[A], f: (A, A) => B): LinkedList[B] =
    if (!list.isEmpty)
      throw new IllegalArgumentException(
        "Can't zip empty list with non-empty list"
      )
    else Empty()
  override def foldLeft[B](zero: B)(f: (B, A) => B): B        = zero
  override def sort(comparator: (A, A) => Int): LinkedList[A] = Empty()
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

  override def filter(predicate: A => Boolean): LinkedList[A] = {
    @tailrec def go(
        remainder: LinkedList[A],
        acc: LinkedList[A]
    ): LinkedList[A] =
      if (remainder.isEmpty) acc
      else if (predicate(remainder.head))
        go(remainder.tail, Cons(remainder.head, acc))
      else go(remainder.tail, acc)

    go(this, Empty()).reverse
  }

  override def map[B](transformer: A => B): LinkedList[B] = {
    @tailrec def go(
        remainder: LinkedList[A],
        acc: LinkedList[B]
    ): LinkedList[B] =
      if (remainder.isEmpty) acc
      else
        go(remainder.tail, Cons(transformer(remainder.head), acc))

    go(
      this.tail,
      Cons(transformer(this.head), Empty())
    ).reverse
  }

  override def flatMap[B](
      transformer: A => LinkedList[B]
  ): LinkedList[B] = {
    @tailrec def go(
        remainder: LinkedList[A],
        acc: LinkedList[B]
    ): LinkedList[B] =
      if (remainder.isEmpty) acc
      else go(remainder.tail, transformer(remainder.head) ++ acc)

    go(this.reverse, Empty())
  }

  override def foreach(f: A => Unit): Unit = {
    @tailrec def go(remainder: LinkedList[A]): Unit =
      if (remainder.isEmpty) ()
      else {
        f(remainder.head)
        go(remainder.tail)
      }

    go(this)
  }

  override def zipWith[B](
      list: LinkedList[A],
      zip: (A, A) => B
  ): LinkedList[B] = {
    @tailrec def go(
        firstRemainder: LinkedList[A],
        secondRemainder: LinkedList[A],
        acc: LinkedList[B]
    ): LinkedList[B] = {
      if (
        (firstRemainder.isEmpty && !secondRemainder.isEmpty) || (!firstRemainder.isEmpty && secondRemainder.isEmpty)
      )
        throw new IllegalArgumentException(
          "The provided list need to be of the same length as this list order for them to be zipped."
        )
      else if (firstRemainder.isEmpty && secondRemainder.isEmpty) acc
      else
        go(
          firstRemainder.tail,
          secondRemainder.tail,
          Cons(zip(firstRemainder.head, secondRemainder.head), acc)
        )
    }

    go(this, list, Empty()).reverse
  }

  override def foldLeft[B](zero: B)(f: (B, A) => B): B = {
    @tailrec def go(remainder: LinkedList[A], acc: B): B =
      if (remainder.isEmpty) acc
      else go(remainder.tail, f(acc, remainder.head))

    go(this, zero)
  }

  override def sort(compare: (A, A) => Int): LinkedList[A] = {
    // insertion sort, O(n^2), stack recursive
    def insert(elem: A, sortedList: LinkedList[A]): LinkedList[A] =
      if (sortedList.isEmpty) Cons(elem, Empty())
      else if (compare(elem, sortedList.head) <= 0) Cons(elem, sortedList)
      else Cons(sortedList.head, insert(elem, sortedList.tail))

    val sortedTail = tail.sort(compare)
    insert(head, sortedTail)
  }
}

object LinkedList {
  def find[A](list: LinkedList[A], predicate: A => Boolean): A =
    if (list.isEmpty) throw new NoSuchElementException
    else if (predicate(list.head)) list.head
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

    println(otherNumbers.map(_ * 2))
    println(otherNumbers.filter(_ % 2 == 0))
    println(otherNumbers ++ first3Numbers)
    println(otherNumbers.flatMap(x => Cons(x, Cons(x + 1, Empty()))))

    // find test
    println(LinkedList.find(first3Numbers, _ % 2 == 0))
    // println(LinkedList.find(empty, evenPredicate)) NoSuchElementException

    first3Numbers.foreach(println)
    println(first3Numbers.zipWith(first3Numbers, _ * _))
    println(first3Numbers.foldLeft(0)(_ - _))
  }
}
