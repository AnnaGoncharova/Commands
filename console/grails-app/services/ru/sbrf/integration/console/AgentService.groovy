package ru.sbrf.integration.console

import ru.sbrf.integration.discovery.AgentAddress

class AgentService {

    static transactional = true

    def List<AgentAddress> agentByMaster(Master master) {
       return [new AgentAddress('localhost', 1111)]
    }
}
