package ru.sbrf.integration

sealed abstract class CommandResult[+T]

case class Success[T](result:T) extends CommandResult[T]
case class Failure(cause: Any) extends CommandResult[Nothing]