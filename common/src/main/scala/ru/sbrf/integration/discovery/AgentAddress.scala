package ru.sbrf.integration.discovery

class AgentAddress(val host: String, val port: Int) extends Serializable {

  override def equals(obj: Any) = {
    if (obj.isInstanceOf[AgentAddress]) {
      val o = obj.asInstanceOf[AgentAddress]
      o.host == host && o.port == port
    } else false
  }

  override def hashCode() = { ("%s:%d" format(host, port)).hashCode() }

  override def toString = "host: %s, port: %s" format (host, port)
}