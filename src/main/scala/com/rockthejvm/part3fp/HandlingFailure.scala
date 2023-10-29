package com.rockthejvm.part3fp

import scala.util.{Try, Success, Failure}
import scala.util.Random

object HandlingFailure {

  // Try = a potentially failed computation
  val aTry: Try[Int]       = Try(42)
  val aFailedTry: Try[Int] = Try(throw new RuntimeException)

  // alt construction
  val aTry_v2: Try[Int]       = Success(42)
  val aFailedTry_v2: Try[Int] = Failure(new RuntimeException)

  // main API
  val checkSuccess = aTry.isSuccess
  val checkFailure = aTry.isFailure
  val chain        = aFailedTry.orElse(aTry)

  // map, flatMap, filter, for comprehensions
  val anIncrementedTry = aTry.map(_ + 1)
  val aFlatMappedTry   = aTry.flatMap(mol => Try(s"My meaning of life is $mol"))
  val aFilteredTry     = aTry.filter(_ % 2 == 0)

  // WHY: avoid unsafe APIs which can THROW exceptions
  def unsafeMethod(): String = throw new RuntimeException(
    "No string for you, buster!"
  )

  // defensive: try-catch-finally
  val stringLengthDefensive =
    try {
      unsafeMethod().length()
    } catch {
      case ex: RuntimeException => -1
    }

  // purely functional
  val stringLengthPure = Try(unsafeMethod()).map(_.length()).getOrElse(-1)

  // DESIGN
  def betterUnsafeMethod(): Try[String] =
    Try(throw new RuntimeException("No string for you, buster!"))
  def betterFallbackMethod(): Try[String] =
    Try("Scala")

  val stringLengthPure_v2 = betterUnsafeMethod().map(_.length())
  val safeChain           = betterUnsafeMethod().orElse(betterFallbackMethod())

  /** Exercise: \- obtain a connection \- fetch URL using that connection \-
    * print the resulting HTML
    */
  val host = "locahost"
  val port = "8081"
  val url  = "rockthejvm.com/home"

  class Connection {
    val random = new Random()

    def get(url: String): String =
      if (random.nextBoolean()) "<html>Success</html>"
      else throw new RuntimeException("Cannot fetch page right now.")

    def getSafe(url: String): Try[String] =
      Try(get(url))
  }

  object HttpService {
    val random = new Random
    def getConnection(host: String, port: String): Connection =
      if (random.nextBoolean()) new Connection
      else throw new RuntimeException("cannot access host/port combination")

    def getConnectionSafe(host: String, port: String): Try[Connection] =
      Try(getConnection(host, port))
  }

  def main(args: Array[String]): Unit = {
    val getResult = for {
      connection <- Try(HttpService.getConnection(host, port))
      getResult  <- Try(connection.get(url))
    } yield getResult

    println(getResult.fold(e => s"<html>${e.getMessage()}</html>", r => r))
  }
}
