package ru.sbrf.integration.agent.handlers

import ru.sbrf.integration.agent.CommandHandler
import ru.sbrf.integration.commands.Alive

object AliveHandler {
  def apply(command: Alive) = new AliveHandler(command)
}
class AliveHandler(val command: Alive) extends CommandHandler[Alive, Boolean] {
  def execute() = true
}