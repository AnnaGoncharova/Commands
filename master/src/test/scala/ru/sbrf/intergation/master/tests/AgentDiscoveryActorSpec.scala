package ru.sbrf.intergation.master.tests

import org.specs2.mutable.Specification
import akka.actor.Actor._
import ru.sbrf.integration.master.AgentDiscoveryActor
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import ru.sbrf.integration.discovery.{AgentDiscoveryEvent, AgentAddress}

@RunWith(classOf[JUnitRunner])
class AgentDiscoveryActorSpec extends Specification {

  val discovery = actorOf[AgentDiscoveryActor].start()
  val address: AgentAddress = new AgentAddress("myhost", 2222)
  val event = new AgentDiscoveryEvent(address)

  def listAgents() = {
    (discovery ? AgentDiscoveryActor.list).get.asInstanceOf[collection.Set[AgentAddress]]
  }

  "Agent discovery actor" should {

    "discover active actors and send active actors list on list message" in {
      discovery ? event
      val discoveredAgents = listAgents()
      discoveredAgents.head must_== address
    }

    "undiscover actors if they dont send discovery events" in {
      discovery ? event
      val discoveredAgents = listAgents()
      Thread.sleep(20000)
      discoveredAgents must be empty
    }
  }

}