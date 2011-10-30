package ru.sbrf.intergation.master.tests

import org.specs2.mutable.Specification
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import akka.actor.Actor._
import ru.sbrf.integration.master.MasterActor
import ru.sbrf.integration.commands.{PingPong, Alive}
import ru.sbrf.integration.agent.Agent

@RunWith(classOf[JUnitRunner])
class MasterActorSpec extends Specification {

  Agent.run()

  val agent = actorOf[MasterActor].start()

  "Master actor" should {
    "send alive command to remote agents and receive reply: true " in {
      (agent ? Alive()).get match {
        case value: Boolean => value must_== true
        case wtf => failure("Master actor does not work properly")
      }
    }

    "send ping pong command to remote agent and receive sended string back" in {
      (agent ? PingPong("Ping")).get match {
        case value: String => value must_== "Ping"
        case wtf => failure("Master actor does not work properly")
      }
    }

    "If send not a command then return IllegalArgumentException" in {
      (agent ? "Wrong").get match {
        case ex: IllegalArgumentException  =>  ex.getMessage mustEqual  "Agent receive not a Command[+T] - Wrong"
        case _ => failure("Wrong error handling")
      }
    }
  }
}