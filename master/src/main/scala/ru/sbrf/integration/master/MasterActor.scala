package ru.sbrf.integration.master

import akka.actor.Actor._
import ru.sbrf.integration.discovery.{AgentDiscoveryEvent, AgentAddress}
import akka.actor.{ActorRef, Actor}
import ru.sbrf.integration.commands.{CommandResult, Failure, Success, Command}

class MasterActor extends Actor {

  val discovery = remote.actorFor("discovery-service", "localhost", 2222).start()
  println("Discovery initialized")

  def agentAddresses() = {
    (discovery ? AgentDiscoveryActor.list).get.asInstanceOf[collection.Set[AgentAddress]]
  }

  def sendCommand(command: Command[_]) = {
    println("Start to send " + command)
    val addresses = agentAddresses()
    println("Agent addresses are " + addresses)
    addresses.map {address =>
      val agent = remote.actorFor("config-service", address.host, address.port).start()
      sendCommandTo(agent, command)
    }
  }

  def sendCommandTo(agent: ActorRef, command: Command[_]) = {
    println("Try to send command to " + agent)

    try {
      (agent ? command).get.asInstanceOf[CommandResult[_]]
    } catch {
      case ex => {
        ex.printStackTrace()
        Failure(ex)
      }
    }
  }

  def receive = {
    case cmd: Command[_] => self reply sendCommand(cmd)
    case wtf => self reply new IllegalArgumentException("Agent receive not a Command[+T] - " + wtf)
  }
}