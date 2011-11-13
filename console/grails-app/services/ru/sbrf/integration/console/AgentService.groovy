package ru.sbrf.integration.console

import ru.sbrf.integration.discovery.AgentAddress
import static akka.actor.Actors.remote

class AgentService {

    static transactional = true

    def List<AgentAddress> agentByMaster(Master master) {
        def masterActor = remote().actorFor("discovery-service", master.host, master.port).start()
        def addresses = masterActor.ask("List", 1000).get()
        println addresses
        addresses.collect { it }
    }
}
