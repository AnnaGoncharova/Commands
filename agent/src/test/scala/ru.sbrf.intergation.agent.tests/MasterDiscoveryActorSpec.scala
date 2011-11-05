package ru.sbrf.intergation.agent.tests

import mock.MasterDiscoveryMock
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification
import akka.actor.Actor._
import ru.sbrf.integration.agent.MasterDiscoveryActor

@RunWith(classOf[JUnitRunner])
class MasterDiscoveryActorSpec extends Specification {

  val discovery =  actorOf[MasterDiscoveryActor].start()

  "Master discover actor " should {
    "periodically send discovery events to master" in {

      remote.start("localhost", 2222)
      val masterMock = actorOf[MasterDiscoveryMock].start()
      remote.register("discovery-service", masterMock)

      def discoverStatus(retry: Int): Boolean = {
        Thread.sleep(3000)
        (masterMock ? "Discovered?").get.asInstanceOf[Boolean]
      }
      val statuses = (1 to 5).map { discoverStatus }
      statuses.contains(true) must_== true
    }
  }
}