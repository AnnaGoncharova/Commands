package ru.sbrf.integration.agent

import akka.actor.Actor._
import akka.actor.Actor
object Agent extends App {

  def registerServices() {
    Actor.remote.start("localhost", 1111)
    Actor.remote.register("config-service", actorOf[AgentActor])
  }

  def startMasterDiscovery() {
    actorOf[MasterDiscoveryActor].start()
  }

  registerServices()
  startMasterDiscovery()

  readLine()
}
