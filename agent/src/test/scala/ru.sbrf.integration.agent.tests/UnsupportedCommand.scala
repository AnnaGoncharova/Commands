package ru.sbrf.integration.agent.tests

import ru.sbrf.integration.commands.Command

object UnsupportedCommand {
  def apply() = { new UnsupportedCommand }
}
class UnsupportedCommand extends Command[String] {}