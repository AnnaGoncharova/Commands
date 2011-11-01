package ru.sbrf.integration.discovery

class AgentAddress(val host: String, val port: Int) extends Serializable {
  override def toString = "host: %s, port: %s" format (host, port)
}