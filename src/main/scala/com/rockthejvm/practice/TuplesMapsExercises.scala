package com.rockthejvm.practice

import scala.annotation.tailrec
import scala.collection.mutable

object TuplesMapsExercises {
  /*
    Social network = Map[String, Set[String]]
    Friend relationships are mutual

    - add a person to the network
    - remove a person from the network
    - add friend relationship (network, a, b)
    - unfriend

    - number of friends of a person
    - which person has the most friends
    - how many people have no friends
    - if there is a social connection between 2 people
   */

  case class Person(firstName: String, lastName: String)
  type SocialNetwork = Map[Person, Set[Person]]

  def addPerson(
      network: SocialNetwork,
      newPerson: Person
  ): SocialNetwork =
    if (network.contains(newPerson)) network else network + (newPerson -> Set())

  @tailrec
  def addFriend(
      network: SocialNetwork,
      firstPerson: Person,
      secondPerson: Person
  ): SocialNetwork = (for {
    firstFriends  <- network.get(firstPerson)
    secondFriends <- network.get(secondPerson)
  } yield (network +
    (firstPerson  -> (firstFriends + secondPerson)) +
    (secondPerson -> (secondFriends + firstPerson)))) match
    case Some(modifiedNetwork) => modifiedNetwork
    case None =>
      addFriend(
        addPerson(addPerson(network, firstPerson), secondPerson),
        firstPerson,
        secondPerson
      )

  def unfriend(
      network: SocialNetwork,
      firstPerson: Person,
      secondPerson: Person
  ): SocialNetwork = (for {
    firstFriends  <- network.get(firstPerson)
    secondFriends <- network.get(secondPerson)
  } yield (network +
    (firstPerson  -> (firstFriends - secondPerson)) +
    (secondPerson -> (secondFriends - firstPerson)))) match
    case Some(modifiedNetwork) => modifiedNetwork
    case None                  => network

  def remove(network: SocialNetwork, person: Person): SocialNetwork =
    network.get(person) match
      case Some(friends) =>
        friends.foldLeft(network)((network, friend) =>
          unfriend(network, person, friend)
        ) - person
      case None => network

  def getNumberOfFriendsOf(
      network: SocialNetwork,
      person: Person
  ): Option[Int] =
    network.get(person) match
      case Some(friends) => Some(friends.size)
      case None          => None

  def getPersonWithMostFriends(network: SocialNetwork): Option[Person] =
    if (network.isEmpty) None
    else
      network.maxBy { case (_, friends) => friends.size } match
        case (person, _) => Some(person)

  def getNumberOfPeopleWithNoFriends(network: SocialNetwork): Int =
    network.view.values.count(_.isEmpty)

  def areConnected(
      network: SocialNetwork,
      firstPerson: Person,
      secondPerson: Person
  ): Boolean = {
    @tailrec
    def breadthFirstSearch(
        visited: Set[Person],
        queue: Set[Person]
    ): Boolean =
      if (queue.isEmpty) false
      else
        val currentPerson = queue.head
        val friends = network.get(currentPerson) match
          case Some(friends) => friends
          case None          => Set()

        if (friends.contains(secondPerson)) true
        else
          breadthFirstSearch(
            visited + currentPerson,
            queue - currentPerson ++ friends -- visited
          )

    (for {
      firstFriends <- network.get(firstPerson)
      _            <- network.get(secondPerson)
    } yield firstFriends) match
      case Some(firstFriends) =>
        breadthFirstSearch(
          Set(firstPerson),
          firstFriends
        )
      case None => false
  }

  def main(args: Array[String]): Unit = {
    val stefan               = Person("Stefan", "Giba")
    val ruxi                 = Person("Ruxandra", "Istrate")
    val cazan                = Person("Alexandru-Petre", "Cazan")
    val cosmin               = Person("Cosmin", "Popa")
    val empty: SocialNetwork = Map()

    val people = addPerson(
      addPerson(addPerson(addPerson(empty, stefan), ruxi), cazan),
      cosmin
    )
    val socialNetwork =
      addFriend(
        addFriend(addFriend(people, stefan, ruxi), ruxi, cazan),
        stefan,
        cosmin
      )

    println(socialNetwork)
    println(unfriend(socialNetwork, stefan, ruxi))
    println(remove(socialNetwork, cazan))
    println(getNumberOfFriendsOf(socialNetwork, stefan))
    println(getNumberOfFriendsOf(socialNetwork, ruxi))
    println(getNumberOfFriendsOf(socialNetwork, cosmin))
    println(getNumberOfFriendsOf(socialNetwork, cazan))
    println(getPersonWithMostFriends(socialNetwork))
    println(getNumberOfPeopleWithNoFriends(socialNetwork))
    println(unfriend(socialNetwork, ruxi, cazan))
    println(areConnected(socialNetwork, cosmin, cazan))
    println(areConnected(unfriend(socialNetwork, ruxi, cazan), cosmin, cazan))
    println(getPersonWithMostFriends(empty))
  }
}
