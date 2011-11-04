package ru.sbrf.integration.master

import akka.actor.Actor._
import akka.actor.Actor
import ru.sbrf.integration.commands.{Failure, Success, Command}

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