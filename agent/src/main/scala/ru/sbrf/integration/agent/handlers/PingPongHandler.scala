package ru.sbrf.integration.agent.handlers

import ru.sbrf.integration.commands.PingPong
import ru.sbrf.integration.agent.CommandHandler

object PingPongHandler {
  def apply(command:PingPong) = new PingPongHandler(command)
}

class PingPongHandler(val command: PingPong) extends CommandHandler[PingPong, String] {
  def execute() = command.message
}