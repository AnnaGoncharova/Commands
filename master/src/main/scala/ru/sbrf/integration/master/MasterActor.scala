package ru.sbrf.integration.master

import ru.sbrf.integration.{Failure, Success, Command}
import ru.sbrf.integration.commands.{PingPong, Alive}
import akka.actor.Actor._
import akka.actor.Actor

object Master extends App {
  val master = actorOf(new MasterActor)
  master.start()
  master ? Alive()
  master ? PingPong("Hey")
  master.stop()
  remote.shutdown()
}

class MasterActor extends Actor {

  val agent = remote actorFor("config-service", "localhost", 1111)

  def sendCommand(command: Command[_]) = {
    (agent ? command).get match {
      case s: Success[_] => s.result
      case f: Failure => f.cause
      case wtf => Failure("Unknown reply" + wtf)
    }
  }

  def receive = {
    case cmd: Command[_] => self reply sendCommand(cmd)
    case wtf => self reply new IllegalArgumentException("Agent receive not a Command[+T] - " + wtf)
  }
}