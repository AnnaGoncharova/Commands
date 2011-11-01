package tests

import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification
import ru.sbrf.integration.agent.handlers.PingPongHandler
import ru.sbrf.integration.commands.{Success, PingPong}

@RunWith(classOf[JUnitRunner])
class PingPongHandlerSpec extends Specification {
  "Ping pong command handler" should {

    "successifully return incoming message" in {
      val handler = new PingPongHandler(PingPong("ping"))
      val result = handler.run()

      result must haveClass[Success[String]]

      result match {
        case Success(message) => message must_== "ping"
        case _ => failure("Ping pong command handler does not return incoming message")
      }
    }
  }
}