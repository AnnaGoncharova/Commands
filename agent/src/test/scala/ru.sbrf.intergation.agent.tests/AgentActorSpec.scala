package ru.sbrf.intergation.agent.tests

import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification
import akka.actor.Actor
import ru.sbrf.integration.agent.{AgentActor, UnsupportedCommandException, Agent}
import ru.sbrf.integration.commands.{Failure, Success, PingPong, Alive}

@RunWith(classOf[JUnitRunner])
class AgentActorSpec extends Specification {

  val actor = Actor.actorOf[AgentActor].start()

  "Agent " should {

    "successifully return true on alive command" in {
      (actor ? Alive()).get match {
        case Success(value: Boolean) => value must_== true
        case _ => failure("Agent alive command is wrong handled")
      }
    }

    "successifully return incoming message on ping pong command" in {
      (actor ? PingPong("hello")).get match {
        case Success(value: String) => value must_== "hello"
        case _ => failure("Agent alive command is wrong handled")
      }

    }

    "If command does not have handler then return failure UnsupportedCommandException" in {
      (actor ? UnsupportedCommand()).get match {
        case Failure(cause) => cause must  haveClass[UnsupportedCommandException]
        case _ => failure("Unsupported command not properly handled by ru.sbrf.integration.master")
      }
    }
  }

}