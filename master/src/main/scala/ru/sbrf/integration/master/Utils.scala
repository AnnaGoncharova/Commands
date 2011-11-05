package ru.sbrf.integration.master

import akka.actor.Actor._

object Utils {
  def discoveryActor() = {
    val discoverers = registry.actorsFor(AgentDiscoveryActor)
    if (discoverers.isEmpty) {
      println("Create new discovery")
      actorOf[AgentDiscoveryActor].start()
    } else {
      println("Reuse discovery")
      discoverers.head
    }
  }

}