package com.rockthejvm.part2oop

object PreventingInheritance {

  class Person(name: String) {
    final def enjoyLife(): Int = 42
  }

  class Adult(name: String) extends Person(name) {
//    override def enjoyLife(): Int = 999 CANNOT OVERRIDE FINAL METHOD
  }

  final class Animal // cannot be inherited
//  class Cat extends Animal

  // sealing a type hierarchy
  // inheritance only permitted within the current file
  sealed class Guitar(nStrings: Int)
  class ElectricGuitar(nStrings: Int) extends Guitar(nStrings)
  class AcousticGuitar                extends Guitar(6)

  // no modifier = "not encouraging" inheritance
  open class ExtensibleGuitar(nStrings: Int)

  def main(args: Array[String]): Unit = {}
}
