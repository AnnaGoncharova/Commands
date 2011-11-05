package ru.sbrf.intergation.master.tests

import org.specs2.mutable.Specification
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import akka.actor.Actor._
import ru.sbrf.integration.master.{AgentDiscoveryActor, MasterActor}
import ru.sbrf.integration.discovery.{AgentDiscoveryEvent, AgentAddress}
import ru.sbrf.integration.commands.{Success, PingPong, Alive}

@RunWith(classOf[JUnitRunner])
class MasterActorSpec extends Specification {

  remote.start("localhost", 2222)
  remote.register("discovery-service", actorOf[AgentDiscoveryActor])
  remote.register("config-service", actorOf[AgentMock])

  val discovery = remote.actorFor("discovery-service", "localhost", 2222).start()
  discovery ? AgentDiscoveryEvent(new AgentAddress("localhost", 2222))

  val master = actorOf[MasterActor].start()

  "Master actor" should {

    "send alive command to remote agents and receive reply: true " in {
      (master ? Alive()).get match {
        case value:Set[Success[Boolean]] => {
          value must be not empty
          value.head.result must_== true
        }
        case wtf => failure("Master actor does not work properly " + wtf)
      }
    }

    "send ping pong command to remote agent and receive sended string back" in {
      (master ? PingPong("Ping")).get match {
        case value: Set[Success[String]] => {
          value must be not empty
          value.head.result must_==  "Ping"
        }
        case wtf => failure("Master actor does not work properly" + wtf)
      }
    }

    "If send not a command then return IllegalArgumentException" in {
      (master ? "Wrong").get match {
        case ex: IllegalArgumentException  =>  ex.getMessage mustEqual  "Agent receive not a Command[+T] - Wrong"
        case _ => failure("Wrong error handling")
      }
    }
  }
}