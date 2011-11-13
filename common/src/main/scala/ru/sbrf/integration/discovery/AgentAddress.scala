package ru.sbrf.integration.discovery

import scala.reflect.BeanProperty

class AgentAddress(
  @BeanProperty
  val host: String,
  @BeanProperty
  val port: Int)
extends Serializable {

  override def equals(obj: Any) = {
    if (obj.isInstanceOf[AgentAddress]) {
      val o = obj.asInstanceOf[AgentAddress]
      o.host == host && o.port == port
    } else false
  }

  override def hashCode() = { ("%s:%d" format(host, port)).hashCode() }

  override def toString = "host: %s, port: %s" format (host, port)
}