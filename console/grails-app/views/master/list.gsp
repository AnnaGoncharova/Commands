<%@ page import="ru.sbrf.integration.console.Master" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'master.label', default: 'Master')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </span>
    <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label"
                                                                               args="[entityName]"/></g:link></span>
</div>

<div class="body">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="list">
        <table>
            <thead>
            <tr>

                <g:sortableColumn property="id" title="${message(code: 'master.id.label', default: 'Id')}"/>

                <g:sortableColumn property="host" title="${message(code: 'master.host.label', default: 'Host')}"/>

                <g:sortableColumn property="port" title="${message(code: 'master.port.label', default: 'Port')}"/>

                <th><g:message code="agent.label"/></th>

            </tr>
            </thead>
            <tbody>
            <g:each in="${masterInstanceList}" status="i" var="masterInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                    <td><g:link action="show"
                                id="${masterInstance.id}">${fieldValue(bean: masterInstance, field: "id")}</g:link></td>

                    <td>
                      <g:link action="show" id="${masterInstance.id}">
                        ${fieldValue(bean: masterInstance, field: "host")}
                      </g:link>
                    </td>

                    <td>${fieldValue(bean: masterInstance, field: "port")}</td>

                     <td><g:link action="agents" id="${masterInstance.id}">show</g:link></td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>

    <div class="paginateButtons">
        <g:paginate total="${masterInstanceTotal}"/>
    </div>
</div>
</body>
</html>
