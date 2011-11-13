package ru.sbrf.integration.agent.tests

import mock.MasterDiscoveryMock
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification
import akka.actor.Actor._
import ru.sbrf.integration.agent.MasterDiscoveryActor

@RunWith(classOf[JUnitRunner])
class MasterDiscoveryActorSpec extends Specification {

  remote.start("localhost", 2222)
  val masterMock = actorOf[MasterDiscoveryMock].start()
  remote.register("discovery-service", masterMock)
  val discovery = actorOf[MasterDiscoveryActor].start()

  "Master discover actor " should {
    "periodically send discovery events to master" in {
      val discovered = (masterMock ? "Discovered?").get.asInstanceOf[Boolean]
      discovered must_== true
    }
  }
}