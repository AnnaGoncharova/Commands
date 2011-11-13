<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main"/>
        <g:set var="entityName" value="${message(code: 'agent.label', default: 'Agents')}"/>
        <title><g:message code="default.list.label" args="[entityName]"/></title>
    </head>

  <body>
  <div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/master')}"><g:message code="master.label" default="Master"/></a>
    </span>
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
              <g:sortableColumn property="host" title="${message(code: 'master.host.label', default: 'Host')}"/>
              <g:sortableColumn property="port" title="${message(code: 'master.port.label', default: 'Port')}"/>
          </tr>
          </thead>
          <tbody>
            <g:each in="${agentsInstanceList}" status="i" var="agentInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                    <td>${fieldValue(bean: agentInstance, field: "host")}</td>
                    <td>${fieldValue(bean: agentInstance, field: "port")}</td>
                </tr>
            </g:each>
          </tbody>
      </table>
    </div>

  </div>
  </body>
</html>