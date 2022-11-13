package com.rockthejvm.part2oop

object PackagesImports {

  // packages = "folders"

  // fully qualified name
  val aList: com.rockthejvm.practice.LinkedList[Int] =
    ??? // throws a NotImplementedError

  // import
  import com.rockthejvm.practice.LinkedList
  val anotherList: LinkedList[Int] = ???

  import java.util.{List as JList}
  val aJavaList: JList[Int] = ???

  // import everything
  import com.rockthejvm.practice.*
  val aPredicate: Predicate[Int] = ???

  def main(args: Array[String]): Unit = {
    println("Hello, world!")
  }
}

