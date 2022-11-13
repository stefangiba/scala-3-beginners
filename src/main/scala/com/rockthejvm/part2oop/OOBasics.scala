package com.rockthejvm.part2oop

object OOBasics {

  class Person(val name: String, age: Int) { // constructor signature
    // fields
    val allCaps = name.toUpperCase

    // aux constructor
    def this(name: String) = this(name, 0)
    def this() = this("Jane Doe")

    // methods
    def greet(name: String): String =
      s"${this.name} says: Hi, $name!"

    // signature differs
    // OVERLOADING
    def greet(): String = s"Hi everyone, my name is $name"
  }

  val aPerson: Person = new Person("John", 26)

  class Writer(firstName: String, lastName: String, val yearOfBirth: Int) {
    def fullName: String = s"$firstName $lastName"
  }

  class Novel(name: String, yearOfRelease: Int, author: Writer) {
    def authorAge: Int                       = yearOfRelease - author.yearOfBirth
    def isWrittenBy(author: Writer): Boolean = author == this.author
    def copy(newYearOfRelease: Int): Novel   = new Novel(name, newYearOfRelease, author)
  }

  class Counter(count: Int = 0) {
    def inc() = new Counter(count + 1)
//    def inc(n: Int) = new Counter(count + n)
//    this is not tail recursive
    def inc(n: Int): Counter =
      if (n == 0) then this
      else inc().inc(n-1)
    def dec()       = if (count == 0) this else new Counter(count - 1)
    def dec(n: Int) = if (count - n < 0) new Counter(0) else new Counter(count - n)
    def print()     = println(s"Current count: $count")
  }

  def main(args: Array[String]): Unit = {
    println(aPerson)
    println(aPerson.name)
    println(aPerson.allCaps)
    println(aPerson.greet("Stefan"))
    println(aPerson.greet())

    val charlesDickens         = new Writer("Charles", "Dickens", 1812)
    val charlesDickensImpostor = new Writer("Charles", "Dickens", 2021)
    val novel                  = new Novel("Great Expectations", 1861, charlesDickens)

    println(charlesDickens.fullName)
    println(novel.authorAge)
    println(novel.isWrittenBy(charlesDickens))
    println(novel.isWrittenBy(charlesDickensImpostor))

    val counter = new Counter()
    counter.print()
    counter.dec().print()
    counter.inc().print()
    counter.inc(10).print()
    counter.inc(200000)
  }
}
