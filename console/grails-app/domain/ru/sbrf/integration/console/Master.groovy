package ru.sbrf.integration.console

class Master {

    String host
    int port

    static constraints = {
        host(blank: false, nullable: false)
        port(nullable: false, range: 1..65535)
    }
}
