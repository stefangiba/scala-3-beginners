package com.rockthejvm.part2oop

val meaningOfLife         = 42
def computeMyLife: String = "Scala"

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
  // val aPredicate: Predicate[Int] = ???

  // import multiple symbols
  import PhysicsConstants.{SPEED_OF_LIGHT, EARTH_GRAVITY}
  val c = SPEED_OF_LIGHT

  // import everything but something
  object PlayingPhysics {
    import PhysicsConstants.{PLANCK as _, *}
    // this is not going to be available
    // val planck = PLANCK
    val c = SPEED_OF_LIGHT
  }

  object PhysicsConstants {
    // constants
    val SPEED_OF_LIGHT = 299792458
    val PLANCK         = 6.62e-34 // scientific
    val EARTH_GRAVITY  = 9.81
  }

  import com.rockthejvm.part2oop.*
  val mol = meaningOfLife

  // default imports
  // scala.*, scala.Predef.*, java.lang.*

  // exports
  class PhysicsCalculator {
    import PhysicsConstants.*
    def computePhotonEnergy(waveLength: Double): Double =
      PLANCK / waveLength
  }

  object ScienceApp {
    val physicsCalculator = new PhysicsCalculator

    export physicsCalculator.computePhotonEnergy

    def computeEquivalentMass(waveLength: Double): Double =
      computePhotonEnergy(waveLength) / (SPEED_OF_LIGHT * SPEED_OF_LIGHT)
  }

  def main(args: Array[String]): Unit = {
    ScienceApp.computePhotonEnergy(550)
    println("Hello, world!")
  }
}
