package ru.sbrf.integration.agent

import akka.actor.Actor
import handlers.{PingPongHandler, AliveHandler}
import ru.sbrf.integration.commands.{Failure, PingPong, Alive, Command}

class AgentActor extends Actor {

  def resolveExecutor(command: Command[_]) = command match {
    case cmd: Alive => AliveHandler(cmd)
    case cmd: PingPong => PingPongHandler(cmd)
    case cmd => UnsupportedCommandHandler(cmd)
  }

  def executeCommand(command: Command[_]) = resolveExecutor(command).run()

  def receive = {
    case command: Command[_] => self.reply(executeCommand(command))
    case wtf => {
      self.reply( new Failure(new UnknownInputException("Input %s not recognized as command" format wtf )))
    }
  }
}