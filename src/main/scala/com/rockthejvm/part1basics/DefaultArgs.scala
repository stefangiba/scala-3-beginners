package com.rockthejvm.part1basics

import scala.annotation.tailrec

object DefaultArgs {

  @tailrec
  def sumUntilTailrec(x: Int, accumulator: Int = 0): Int =
    if (x <= 0) accumulator
    else
      sumUntilTailrec(x - 1, accumulator + x)

  val sumUntil100 = sumUntilTailrec(100) // additional arg passed automatically

  def savePicture(
      dirPath: String,
      name: String,
      format: String = "jpg",
      width: Int = 1920,
      height: Int = 1080
  ): Unit = {
    println("Saving picture in fromat " + format + " in path " + dirPath)
  }

  def main(args: Array[String]): Unit = {
    // default args are injected
    savePicture("/users/stefan/photos", "myphoto")
    // pass explicit different values for default args
    savePicture("/users/stefan/photos", "myphoto", "png")
    // pas values after the default argument
    savePicture("/users/stefan/photos", "myphoto", width = 800, height = 600)
    // naming arguments allow argument passing in a different order
    savePicture("/users/stefan/photos", "myphoto", height = 600, width = 800)
  }
}
