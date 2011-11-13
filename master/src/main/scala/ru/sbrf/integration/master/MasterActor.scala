package ru.sbrf.integration.master

import akka.actor.Actor._
import ru.sbrf.integration.discovery.AgentAddress
import akka.actor.{ActorRef, Actor}
import ru.sbrf.integration.commands.{CommandResult, Failure, Command}
import scala.collection.JavaConverters._

class MasterActor extends Actor {

  val discovery = remote.actorFor("discovery-service", "localhost", 2222).start()

  def agentAddresses() = {
    (discovery ? AgentDiscoveryActor.list).get.asInstanceOf[java.util.Set[AgentAddress]].asScala.toSet
  }

  def sendCommand(command: Command[_]) = {
    val addresses = agentAddresses()
    addresses.map {address =>
      val agent = remote.actorFor("config-service", address.host, address.port).start()
      sendCommandTo(agent, command)
    }
  }

  def sendCommandTo(agent: ActorRef, command: Command[_]) = {
    try {
      (agent ? command).get.asInstanceOf[CommandResult[_]]
    } catch {
      case ex => Failure(ex)
    }
  }

  def receive = {
    case cmd: Command[_] => self reply sendCommand(cmd)
    case wtf => self reply new IllegalArgumentException("Agent receive not a Command[+T] - " + wtf)
  }
}