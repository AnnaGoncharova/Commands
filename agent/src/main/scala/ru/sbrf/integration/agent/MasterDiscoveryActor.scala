package ru.sbrf.integration.agent

import akka.actor.{Scheduler, Actor}
import java.util.concurrent.TimeUnit._
import akka.actor.Actor._
import ru.sbrf.integration.discovery.{AgentAddress, AgentDiscoveryEvent}

class MasterDiscoveryActor extends Actor {

  val event = new AgentDiscoveryEvent(new AgentAddress("localhost", 1111))

  override def preStart() { Scheduler.schedule(self, "Discover", 0, 5, SECONDS) }

  def receive = {
    case wtf => {
      val master = remote.actorFor("discovery-service", "localhost", 2222).start()
      master ! event
    }
  }
}