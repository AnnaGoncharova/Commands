package ru.sbrf.integration.master

import akka.actor.{ActorRef, Actor}
import ru.sbrf.integration.discovery.{DiscoveryEventType, AgentDiscoveryEvent, AgentAddress}

class AgentDiscoveryActor extends Actor {

  val agents = List[ActorRef]()

  def newAgentDiscovered(address: AgentAddress) = {
    println ("Discovered agent %s" format address)
    address
  }
  def removeDiscoveredAgent(address: AgentAddress) = {
    println ("Agent %s shutdowned" format address)
    address
  }
  def listDiscoveredAgent(address: AgentAddress) = {
    println ("List of agents")
    agents
  }

  def receive = {
    case AgentDiscoveryEvent(address, DiscoveryEventType.Add) => self reply newAgentDiscovered(address)
    case AgentDiscoveryEvent(address, DiscoveryEventType.Remove) => self reply removeDiscoveredAgent(address)
    case AgentDiscoveryEvent(address, DiscoveryEventType.List) => self reply listDiscoveredAgent(address)
  }
}