package ru.sbrf.integration.agent

import ru.sbrf.integration.commands.{Failure, Success, Command}

abstract class CommandHandler[C <: Command[T], T] {

  val command: C

  def execute(): T

  def run() = try { Success(execute()) } catch { case e => Failure(e) }
}

class UnsupportedCommandException(msg:String) extends Exception(msg)
class UnknownInputException(msg:String) extends Exception(msg)

object UnsupportedCommandHandler {
  def apply(command: Command[_]) = new UnsupportedCommandHandler(command)
}
class UnsupportedCommandHandler(val command: Command[_]) extends CommandHandler[Command[Any], Any]{
  def execute() = throw new UnsupportedCommandException("Command handler does not resolved for command " + command)
}