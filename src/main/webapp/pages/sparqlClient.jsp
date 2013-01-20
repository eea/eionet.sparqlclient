<%@page contentType="text/html;charset=UTF-8"%>

<%@ include file="/pages/common/taglibs.jsp"%>

<stripes:layout-render name="/pages/common/template.jsp" pageTitle="Simple SPARQL client">

  <stripes:layout-component name="contents">
    <h1>Simple SPARQL client</h1>
    <script type="text/javascript">
// <![CDATA[
function copyEndpoint() {
    sel = document.getElementById('endpointSelect');
    newvalue = sel.options[sel.selectedIndex].value;
    document.getElementById('endpointTxt').value= newvalue;
    sel.selectedIndex = 0;
    
    exp = document.getElementById('explore');
    if (exp && exp.disabled == false) {
        document.getElementById('queryText').disabled = true;
        document.getElementById('queryForm').submit();
    }
}

function removeExplore() {
    document.getElementById('explore').disabled = true;
    document.getElementById('tab').disabled = true;
}
// ]]>
    </script>
    <div style="margin-top:15px">
      <stripes:form action="/sparqlClient.action" method="get" id="queryForm">
        <div>
          <select id="endpointSelect" onchange="copyEndpoint()" title="SPARQL endpoint">
            <option value="">Enter or select endpoint</option>
            <c:forEach var="endpoint" items="${actionBean.endpoints}">
              <option value="${endpoint}">${endpoint}</option>
            </c:forEach>
          </select>
          <stripes:text name="endpoint" id="endpointTxt" size="70" value="${actionBean.endpoint}"/>
        </div>
        <div>
          <c:choose>
            <c:when test="${not empty actionBean.explore}">
              <label for="queryText" class="question">Explore:</label>
              <stripes:hidden name="explore" id="explore" value="${actionBean.explore}"/>
              <stripes:hidden name="tab" id="tab" value="${actionBean.tab}"/>
              <ul id="exploretabs"><c:forEach begin="0" end="${actionBean.numTabs - 1}" var="tab">
                <li class="exploretab"><stripes:link href="/sparqlClient.action"><c:out value="${fn:escapeXml(actionBean.tabLabels[tab])}"/>
                  <stripes:param name="tab" value="${tab}"/>
                  <stripes:param name="explore" value="${fn:escapeXml(actionBean.explore)}"/>
                  <stripes:param name="endpoint" value="${actionBean.endpoint}"/>
                </stripes:link></li>
              </c:forEach></ul>
            </c:when>
            <c:otherwise>
              <input type="hidden" name="explore" id="explore" value="" disabled="disabled" />
              <label for="queryText" class="question">Query:</label>
            </c:otherwise>
          </c:choose>
        </div>
        <div>
          <div class="expandingArea">
            <pre><span></span><br /></pre>
            <textarea name="query" id="queryText" rows="8" cols="80" style="clear:right; display:block; width:100%"
            onchange="removeExplore()"><c:if test="${empty actionBean.query}">PREFIX rdfs: &lt;http://www.w3.org/2000/01/rdf-schema#&gt;

SELECT DISTINCT ?subject ?label
WHERE {
  ?subject a ?class
  OPTIONAL { ?subject rdfs:label ?label }
} LIMIT 100</c:if>${actionBean.query}</textarea>
          </div>
            <stripes:submit name="execute" value="Execute" id="executeButton"/>
        </div>
        <script type="text/javascript">
// <![CDATA[
function makeExpandingArea(container) {
 var area = container.querySelector('textarea');
 var span = container.querySelector('span');
 if (area.addEventListener) {
   area.addEventListener('input', function() {
     span.textContent = area.value;
   }, false);
   span.textContent = area.value;
 } else if (area.attachEvent) {
   // IE8 compatibility
   area.attachEvent('onpropertychange', function() {
     span.innerText = area.value;
   });
   span.innerText = area.value;
 }
 // Enable extra CSS
 container.className += ' active';
}

var areas = document.querySelectorAll('.expandingArea');
var l = areas.length;

while (l--) {
 makeExpandingArea(areas[l]);
}
// ]]>
        </script>

        <c:choose>
          <c:when test="${not empty actionBean.query || not empty actionBean.explore}">
            <c:choose>
              <c:when test="${not empty actionBean.result && not empty actionBean.result.rows}">

                <table class="datatable">
                  <thead>
                    <c:forEach items="${actionBean.result.variables}" var="variable">
                      <th><c:out value="${variable}"/></th>
                    </c:forEach>
                  </thead>
                  <tbody>
                    <c:forEach items="${actionBean.result.rows}" var="resultRow">
                      <tr>
                        <c:forEach items="${actionBean.result.variables}" var="variable">
                          <td>
                            <c:choose>
                              <c:when test="${not empty resultRow[variable]}">
                                <c:choose>
                                  <c:when test="${resultRow[variable].literal}">
                                    <span class="literal"><c:out value="${resultRow[variable].value}"/></span>
                                  </c:when>
                                  <c:otherwise>
                                    <stripes:link href="/sparqlClient.action"><c:out value="${resultRow[variable].value}"/>
                                      <stripes:param name="explore" value="${fn:escapeXml(resultRow[variable].value)}"/>
                                      <stripes:param name="endpoint" value="${actionBean.endpoint}"/>
                                    </stripes:link>
                                  </c:otherwise>
                                </c:choose>
                              </c:when>
                              <c:otherwise>
                                &nbsp;
                              </c:otherwise>
                            </c:choose>
                          </td>
                        </c:forEach>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </c:when>
              <c:otherwise>
                <div class="system-msg">The query gave no results!</div>
              </c:otherwise>
            </c:choose>
          </c:when>
          <c:otherwise>
            <div id="intro">
              <h2>Introduction</h2>
              <p>This application has two modes. The starting mode is the <em>query</em> mode where you can execute a SPARQL script
                on a remote endpoint. When you get some results you can click on a resource and this takes you to <em>explore</em> mode.
                The application will then provide you with some scripts to investigate the resource in various ways.
                If you change the query, then you go back to <em>query</em> mode.
              </p>
              <p>
              Note: Some endpoints don't have the required functionality for all queries, and therefore can react in unexpected ways.
              </p>
          </div>
          </c:otherwise>
        </c:choose>
      </stripes:form>
    </div>
  </stripes:layout-component>
</stripes:layout-render>
