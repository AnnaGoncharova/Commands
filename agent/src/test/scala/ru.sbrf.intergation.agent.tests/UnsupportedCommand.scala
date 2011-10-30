package tests

import ru.sbrf.integration.Command

object UnsupportedCommand {
  def apply() = { new UnsupportedCommand }
}
class UnsupportedCommand extends Command[String] {}