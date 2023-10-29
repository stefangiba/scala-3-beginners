package com.rockthejvm.part3fp

import scala.util.Random

object LinearCollections {

  // ** Seq ** -> well-defined ordering + indexing
  // it is a trait that describes a linear collection
  private def testSeq(): Unit = {
    val aSequence = Seq(1, 2, 3, 4)

    // main API - index an element
    val thirdElement = aSequence.apply(2)

    // map, flatMap, filter, for
    val anIncrementedSequence = aSequence.map(_ + 1)
    val aFlatMappedSequence   = aSequence.flatMap(x => Seq(x, x + 1))
    val aFilteredSequence     = aSequence.filter(_ % 2 == 0)

    println(anIncrementedSequence)
    println(aFlatMappedSequence)
    println(aFilteredSequence)

    // other methods
    val reversed             = aSequence.reverse
    val concatenation        = aSequence ++ Seq(5, 6, 7)
    val sortedSequence       = aSequence.sorted
    val sum                  = aSequence.foldLeft(0)(_ + _)
    val stringRepresentation = aSequence.mkString("[", ", ", "]")

    println(aSequence)
    println(reversed)
    println(concatenation)
    println(sortedSequence)
    println(sum)
    println(stringRepresentation)
  }

  // Lists
  private def testLists(): Unit = {
    val aList = List(1, 2, 3)
    // same API as Seq

    val firstElement = aList.head
    val rest         = aList.tail

    // appending and prepending
    val aBiggerList = 0 +: aList :+ 4
    val prepending  = 0 :: aList

    // utility methods
    val scalaX5 = List.fill(5)("Scala")
    println(scalaX5)
  }

  // Ranges
  private def testRanges(): Unit = {
    val aRange             = 1 to 10
    val aNonInclusiveRange = 1 until 10
    // same API as Seq

    (1 to 10).foreach(_ => println("Scala"))
  }

  // Arrays
  private def testArrays(): Unit = {
    // Arrays ARE NOT Seq
    val anArray = Array(1, 2, 3, 4, 5, 6) // int[] on the JVM
    // most Seq APIs
    val aSequence: Seq[Int] = anArray.toIndexedSeq

    // arrays are mutable
    anArray.update(2, 30) // no new array is allocated
  }

  // Vectors = fast Seqs for a large amount of data
  private def testVectors(): Unit = {
    val vector = Vector(1, 2, 3, 4, 5, 6)
    // the same Seq API
  }

  // Sets
  private def testSet(): Unit = {
    val set = Set(1, 2, 3, 4, 5) // no ordering guaranteed
    // equals + hashCode = hash set

    // main API: test if an element is in the set
    val containsThree = set.contains(3)
    println(containsThree)
    val contains3 = set(3)
    println(contains3)

    // adding / removing
    val aBiggerSet  = set + 4
    val aSmallerSet = set - 4

    // concatenation
    val anotherSet        = Set(4, 5, 6, 7, 8)
    val aMuchBiggerSet    = set.union(anotherSet)
    val aMuchBiggerSet_v2 = set ++ anotherSet
    val aMuchBiggerSet_v3 = set | anotherSet

    // difference
    val diffSet    = set.diff(anotherSet)
    val diffSet_v2 = set -- anotherSet

    // intersection
    val anIntersection    = set.intersect(anotherSet)
    val anIntersection_v2 = set & anotherSet
  }

  private def smallBenchmark(): Unit = {
    val maxRuns     = 1000
    val maxCapacity = 1_000_000

    def getWriteTime(collection: Seq[Int]): Double = {
      val random = new Random()
      val times = for {
        _ <- 1 to maxRuns
      } yield {
        val index   = random.nextInt(maxCapacity)
        val element = random.nextInt()

        val currentTime = System.nanoTime()
        val _           = collection.updated(index, element)
        System.nanoTime() - currentTime
      }

      // compute average
      times.sum * 1.0 / maxRuns
    }

    val numbersList   = (1 to maxCapacity).toList
    val numbersVector = (1 to maxCapacity).toVector

    println(getWriteTime(numbersList))
    println(getWriteTime(numbersVector))
  }

  def main(args: Array[String]): Unit = {
    smallBenchmark()
  }
}
