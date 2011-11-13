package ru.sbrf.integration.console

class MasterController {
    static scaffold = true

    def AgentService agentService

    def agents = {
        if (!params.id) {
            flash.message = "Master id is null"
            return
        }
        def master = Master.findById(params.id)
        if (!master) {
            flash.message = "Master with id ${params.id} not found"
        }
        [agetntInstanceList: agentService.agentByMaster(master)]
    }
}
