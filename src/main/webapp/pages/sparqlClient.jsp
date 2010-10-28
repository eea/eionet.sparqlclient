<%@page contentType="text/html;charset=UTF-8"%>

<%@ include file="/pages/common/taglibs.jsp"%>	

<stripes:layout-render name="/pages/common/template.jsp" pageTitle="Simple SPARQL client">

	<stripes:layout-component name="contents">
	
        <h1>Simple SPARQL client</h1>
        
        <p>
        	Type a SPARQL SELECT query and select an endpoint on which you want to execute it.
        	Then press Execute.
		</p>

		<div style="margin-top:15px">
			<stripes:form action="/sparqlClient.action" method="get">
			
							<label for="endpointSelect" class="question">SPARQL endpoint:</label>
							<stripes:select name="endpoint" id="endpointSelect" style="display:block">
						   		<c:forEach var="endpoint" items="${actionBean.endpoints}">
									<stripes:option value="${endpoint}" label="${endpoint}"/>
						   		</c:forEach>
						   	</stripes:select>
							<label for="queryText" class="question">Query:</label>
							<stripes:textarea name="query" id="queryText" rows="4" cols="80" style="display:block"/>
							<stripes:submit name="execute" value="Execute" id="executeButton"/>

				<c:if test="${not empty actionBean.query}">
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
														<c:when test="${resultRow[variable].literal}">
															<c:out value="${resultRow[variable].value}"/>
														</c:when>
														<c:otherwise>
															<a class="link-external" href="${fn:escapeXml(resultRow[variable].value)}">
																<c:out value="${resultRow[variable].value}"/>
															</a>
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
				</c:if>
	    	</stripes:form>
    	</div>
	</stripes:layout-component>
</stripes:layout-render>
