package ru.sbrf.integration.commands

object Alive {
  def apply() = new Alive()
}

class Alive extends Command[Boolean]