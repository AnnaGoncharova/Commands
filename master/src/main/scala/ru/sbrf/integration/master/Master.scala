package ru.sbrf.integration.master

import akka.actor.Actor._

object Master extends App {

  def startEngine() {
    remote.start("localhost", 2222)
    remote.register("discovery-service", actorOf[AgentDiscoveryActor])
  }
  startEngine()

  val master = actorOf[MasterActor].start()

  readLine()
}