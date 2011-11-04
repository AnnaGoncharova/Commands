package ru.sbrf.integration.agent

import akka.actor.{Scheduler, Actor}
import java.util.concurrent.TimeUnit._
import akka.actor.Actor._
import ru.sbrf.integration.discovery.AgentDiscoveryEvent

class MasterDiscoveryActor extends Actor {

  val master = remote.actorFor("discovery-service", "localhost", 2222).start()
  val event = new AgentDiscoveryEvent("localhost", 2222)
  val ping = "Ping"

  override def preStart { self ! ping}

  def receive = {
    case ping => {
      try {
        master ! new AgentDiscoveryEvent("localhost", 2222)
      } catch {
        case ex => println("Master not discovered")
      } finally {
        Scheduler.scheduleOnce(self, ping, 5, SECONDS)
      }
    }
  }
}