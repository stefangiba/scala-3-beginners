package com.rockthejvm.part3fp

import scala.util.Random

object Options {
  // collections with at most one value
  val option: Option[Int]      = Option(42)
  val emptyOption: Option[Int] = Option.empty

  // alt version
  val presentValue: Option[Int]   = Some(4)
  val emptyOption_v2: Option[Int] = None

  // "standard" API
  val isEmpty       = option.isEmpty
  val innerValue    = option.getOrElse(90)
  val anotherOption = Option(46)
  val chainedOption = emptyOption.orElse(anotherOption)

  // map, flatMap, filter, for
  val anIncrementedOption = option.map(_ + 1)                      // Some(43)
  val filteredOption      = anIncrementedOption.filter(_ % 2 == 0) // None
  val flatMappedOption =
    option.flatMap(value => Option(value * 10)) // Some(420)

  // WHY Options: work with unsafe APIs
  def unsafeMethod(): String   = null
  def fallbackMethod(): String = "some valid result"

  // defensive style
  val stringLength = if (unsafeMethod() == null) -1 else unsafeMethod().length

  // option style
  val stringLengthOption = Option(unsafeMethod()).map(_.length)

  // use case for `orElse`
  val someResult = Option(unsafeMethod()).orElse(Option(fallbackMethod()))

  // DESIGN
  def betterUnsafeMethod(): Option[String]   = None
  def betterFallbackMethod(): Option[String] = Some("A valid result")
  val betterChain = betterUnsafeMethod().orElse(betterFallbackMethod())

  // example: Map.get()
  val phoneBook        = Map("Daniel" -> 1234)
  val marysPhoneNumber = phoneBook.get("Mary") // None

  /** Exercise: Get the host and port from the config map, try to open a
    * connection and print the connection status string
    */

  val config: Map[String, String] = Map(
    // comes from elsewhere
    "host" -> "176.45.32.1",
    "port" -> "8081"
  )

  class Connection {
    def connect(): String = "Connection successful!"
  }

  object Connection {
    val random = new Random()

    def apply(host: String, port: String): Option[Connection] =
      if (random.nextBoolean) Some(new Connection)
      else None
  }

  def main(args: Array[String]): Unit = {
    val connectionStatus = (for {
      host       <- config.get("host")
      port       <- config.get("port")
      connection <- Connection(host, port)
    } yield connection.connect()).getOrElse("Connection failed!")

    println(connectionStatus)
  }
}
