package ru.sbrf.integration.commands

import ru.sbrf.integration.Command

object Alive {
  def apply() = new Alive()
}

class Alive extends Command[Boolean]