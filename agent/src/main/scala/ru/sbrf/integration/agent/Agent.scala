package ru.sbrf.integration.agent

import ru.sbrf.integration.agent.handlers.{AliveHandler, PingPongHandler}
import ru.sbrf.integration.commands.{PingPong, Alive}
import akka.actor.Actor._
import ru.sbrf.integration.{Failure, Command}
import akka.actor.Actor

object Agent {

  def run() = {
    Actor.remote.start("localhost", 1111)
    Actor.remote.register("config-service", actorOf[AgentActor])
  }

  def main(args: Array[String]) = run
}

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