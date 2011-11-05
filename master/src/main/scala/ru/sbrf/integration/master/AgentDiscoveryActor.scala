package ru.sbrf.integration.master

import System._
import java.util.concurrent.TimeUnit._
import collection.mutable.Map
import ru.sbrf.integration.discovery.{AgentDiscoveryEvent, AgentAddress}
import akka.actor.{Scheduler, Actor}

object AgentDiscoveryActor {
  val clean = "Clean"
  val list = "List"
}

class AgentDiscoveryActor extends Actor {

  val agents = Map[AgentAddress, Long]()

  override def preStart() { Scheduler.schedule( self, AgentDiscoveryActor.clean, 10, 10, SECONDS)}

  def agentDiscovered(address: AgentAddress) {
    agents += (address -> currentTimeMillis())
  }

  def cleanInactiveAgent() {
    val current = currentTimeMillis()
    val inactiveAgents = agents filter (current - _._2 > 10000)
    agents --= inactiveAgents.keys
  }

  def receive = {
    case AgentDiscoveryEvent(address) => agentDiscovered(address)
    case AgentDiscoveryActor.clean => cleanInactiveAgent()
    case AgentDiscoveryActor.list => { self reply agents.keySet }
  }
}