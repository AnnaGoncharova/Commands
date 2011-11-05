package ru.sbrf.integration.agent

import akka.actor.{Scheduler, Actor}
import java.util.concurrent.TimeUnit._
import akka.actor.Actor._
import ru.sbrf.integration.discovery.{AgentAddress, AgentDiscoveryEvent}

class MasterDiscoveryActor extends Actor {

  val master = remote.actorFor("discovery-service", "localhost", 2222).start()
  val event = new AgentDiscoveryEvent(new AgentAddress("localhost", 2222))

  override def preStart() { Scheduler.schedule(master, event, 5, 5, SECONDS) }

  def receive = {
    case wtf => {}
  }
}