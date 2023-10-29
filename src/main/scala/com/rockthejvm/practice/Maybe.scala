package com.rockthejvm.practice

abstract class Maybe[A] {
  def map[B](f: A => B): Maybe[B]
  def filter(predicate: A => Boolean): Maybe[A]
  def flatMap[B](f: A => Maybe[B]): Maybe[B]
}

case class Nothing[A]() extends Maybe[A] {
  override def map[B](f: A => B): Maybe[B]               = Nothing[B]()
  override def filter(predicate: A => Boolean): Maybe[A] = this
  override def flatMap[B](f: A => Maybe[B]): Maybe[B]    = Nothing[B]()
}

case class Just[A](value: A) extends Maybe[A] {
  override def map[B](f: A => B): Maybe[B] = Just(f(value))
  override def filter(predicate: A => Boolean): Maybe[A] =
    if (predicate(value)) this else Nothing[A]()
  override def flatMap[B](f: A => Maybe[B]): Maybe[B] = f(value)
}

object MaybeTest {
  def main(args: Array[String]): Unit = {
    val maybeInt: Maybe[Int]  = Just(3)
    val maybeInt2: Maybe[Int] = Nothing()
    val maybeIncrementedInt   = maybeInt.map(_ + 1)
    val maybeIncrementedInt2  = maybeInt2.map(_ + 1)
    println(maybeIncrementedInt)
    println(maybeIncrementedInt2)

    val maybeFiltered = maybeInt.filter(_ % 2 == 0)
    println(maybeFiltered)

    val maybeFlatMapped = maybeInt.flatMap(number => Just(number * 3))
    println(maybeFlatMapped)
  }
}
