package ru.sbrf.intergation.agent.tests.mock

import akka.actor.Actor
import ru.sbrf.integration.discovery.AgentDiscoveryEvent


class MasterDiscoveryMock extends Actor {

  var discovered = false

  def receive = {
    case AgentDiscoveryEvent(address) => { discovered = true }
    case _: String => self reply discovered
  }
}