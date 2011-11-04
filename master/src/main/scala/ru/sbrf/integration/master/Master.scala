package ru.sbrf.integration.master

import akka.actor.Actor
import akka.actor.Actor._

object Master extends App {

  def registerServices = {
    remote.start("localhost", 2222)
    val discovery = actorOf[AgentDiscoveryActor].start()
    remote.register("discovery-service", discovery)
    discovery
  }

  registerServices
  val master = actorOf[MasterActor].start()

  readLine()
}