package ru.sbrf.integration.master.tests

import org.specs2.mutable.Specification
import akka.actor.Actor._
import ru.sbrf.integration.master.AgentDiscoveryActor
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import ru.sbrf.integration.discovery.{AgentDiscoveryEvent, AgentAddress}
import scala.collection.JavaConverters._

@RunWith(classOf[JUnitRunner])
class AgentDiscoveryActorSpec extends Specification {

  val discovery = actorOf[AgentDiscoveryActor].start()
  val address: AgentAddress = new AgentAddress("myhost", 1111)
  val event = new AgentDiscoveryEvent(address)

  def listAgents() = {
    val agentsSet = (discovery ? AgentDiscoveryActor.list).get.asInstanceOf[java.util.Set[AgentAddress]]
    agentsSet.asScala.toSet
  }

  "Agent discovery actor" should {

    "discover active actors and send active actors list on list message" in {
      discovery ? event
      val discoveredAgents = listAgents()
      discoveredAgents.head must_== address
    }

    "undiscover actors if they dont send discovery events" in {
      discovery ? event
      Thread.sleep(12000)
      discovery ? AgentDiscoveryActor.clean
      val discoveredAgents = listAgents()
      discoveredAgents must be empty
    }
  }

}