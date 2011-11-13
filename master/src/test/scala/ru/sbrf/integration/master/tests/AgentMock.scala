package ru.sbrf.integration.master.tests

import akka.actor.Actor
import ru.sbrf.integration.commands.{Alive, PingPong, Success}

class AgentMock extends Actor {
  def receive = {
    case command: Alive => self.reply(Success(true))
    case command: PingPong => self reply Success(command.message)
  }
}