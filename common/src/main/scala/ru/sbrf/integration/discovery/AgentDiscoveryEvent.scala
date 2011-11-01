package ru.sbrf.integration.discovery

object DiscoveryEventType extends Enumeration {
  type Type = Value
  val Add = Value("Add")
  val Remove = Value("Remove")
  val List = Value("List")
}

object AgentDiscoveryEvent {
  def apply(host: String, port: Int, event: DiscoveryEventType.Type) = {
    new AgentDiscoveryEvent(new AgentAddress(host, port), event)
  }
}
case class AgentDiscoveryEvent(val agentAddress: AgentAddress, val event: DiscoveryEventType.Type) {}