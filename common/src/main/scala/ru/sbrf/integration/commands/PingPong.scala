package ru.sbrf.integration.commands

import ru.sbrf.integration.Command


object PingPong {
  def apply(msg: String) = new PingPong(msg)
}
class PingPong(val message: String) extends Command[String]