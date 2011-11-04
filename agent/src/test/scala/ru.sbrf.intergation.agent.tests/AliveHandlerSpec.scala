package ru.sbrf.intergation.agent.tests

import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification
import ru.sbrf.integration.agent.handlers.AliveHandler
import ru.sbrf.integration.commands.{Success, Alive}

@RunWith(classOf[JUnitRunner])
class AliveHandlerSpec extends Specification {
  "Alive handler" should {

    "always return success with true value" in {
      val handler = AliveHandler(Alive())
      val result = handler.run()

      result must haveClass[Success[Boolean]]

      result match {
        case Success(value) => value must_== (true)
        case _ => failure("unsuccessiful alive command handler")
      }
    }

  }
}