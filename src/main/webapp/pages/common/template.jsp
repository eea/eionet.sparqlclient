<%@ include file="/pages/common/taglibs.jsp"%>	

<%@page import="eionet.sparqlClient.web.util.BaseUrl"%>

<stripes:layout-definition>
	<%@ page contentType="text/html;charset=UTF-8" language="java"%>
	
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
			<meta name="Publisher" content="EEA, The European Environment Agency" />
			<meta name="Rights" content="Copyright EEA Copenhagen 2003-2008" />
			<base href="<%= BaseUrl.getBaseUrl(request) %>"/>
			
			<title>SPARQL client - ${pageTitle}</title>
			
			<link rel="stylesheet" type="text/css" href="http://www.eionet.europa.eu/styles/eionet2007/print.css" media="print" />
			<link rel="stylesheet" type="text/css" href="http://www.eionet.europa.eu/styles/eionet2007/handheld.css" media="handheld" />		
			<link rel="stylesheet" type="text/css" href="http://www.eionet.europa.eu/styles/eionet2007/screen.css" media="screen" title="Eionet 2007 style" />
			<link rel="stylesheet" type="text/css" href="<c:url value="/css/eionet2007.css"/>" media="screen" title="Eionet 2007 style"/>
			<link rel="stylesheet" type="text/css" href="<c:url value="/css/application.css"/>" media="screen" title="application style sheet"/>
			<link rel="shortcut icon" href="<c:url value="/favicon.ico"/>" type="image/x-icon" />
			
			<script type="text/javascript" src="<c:url value="/scripts/jquery-1.3.2.min.js"/>"></script>
			<script type="text/javascript" src="<c:url value="/scripts/jquery-timers.js"/>"></script>
			<script type="text/javascript" src="<c:url value="/scripts/jquery.autocomplete.js"/>"></script>
			<script type="text/javascript" src="<c:url value="/scripts/util.js"/>"></script>
			<script type="text/javascript" src="<c:url value="/scripts/pageops.js"/>"></script>
		</head>
		<body>
			<div id="container">
				<div id="toolribbon">
					<div id="lefttools">
						<a id="eealink" href="http://www.eea.europa.eu/">EEA</a>
						<a id="ewlink" href="http://www.ewindows.eu.org/">EnviroWindows</a>
				    </div>
				    <div id="righttools">
				    	<c:choose>
                        	<c:when test="${empty appUser}">    
                        		<stripes:link id="loginlink" title="Login" href="#" event="login">Login</stripes:link>
					    	</c:when>
	                        <c:otherwise>
								<stripes:link id="logoutlink" title="Logout" href="#" event="logout">Logout ${appUser.userName}</stripes:link>
	                        </c:otherwise>
	                    </c:choose>
						<a id="printlink" title="Print this page" href="javascript:this.print();"><span>Print</span></a>
				        <a id="fullscreenlink" href="javascript:toggleFullScreenMode()" title="Switch to/from full screen mode"><span>Switch to/from full screen mode</span></a>
				        <a id="acronymlink" href="http://www.eionet.europa.eu/acronyms" title="Look up acronyms"><span>Acronyms</span></a>
				        <form action="http://search.eionet.europa.eu/search.jsp" method="get">
							<div id="freesrchform"><label for="freesrchfld">Search</label>
								<input type="text" id="freesrchfld" name="query"/>
				
								<input id="freesrchbtn" type="image" src="<c:url value="images/button_go.gif"/>" alt="Go"/>
							</div>
						</form>
				    </div>
				</div> <!-- toolribbon -->
				
				<div id="pagehead">
				    <a href="/"><img src="images/eea-print-logo.gif" alt="Logo" id="logo" /></a>
				    <div id="networktitle">Eionet</div>
				    <div id="sitetitle">SPARQL client</div>
				    <div id="sitetagline">This service is part of Reportnet</div>
				</div> <!-- pagehead -->
				
				<div id="menuribbon">
					<%@ include file="/pages/common/dropdownmenus.txt" %>
				</div>
				
				<div class="breadcrumbtrail">
					<div class="breadcrumbhead">You are here:</div>
					<div class="breadcrumbitem eionetaccronym">
						<a href="http://www.eionet.europa.eu">Eionet</a>
					</div>
					<c:choose>
                    	<c:when test="${empty pageTitle}">
                    		<div class="breadcrumbitemlast">SPARQL client</div>
						</c:when>
						<c:otherwise>
							<div class="breadcrumbitem"><a href="${pageContext.request.contextPath}/">SPARQL client</a></div>
 							<div class="breadcrumbitemlast"><c:out value="${pageTitle}"/></div>
						</c:otherwise>
					</c:choose>
					<div class="breadcrumbtail"></div>
				</div>
				
				<stripes:layout-component name="navigation">
	                <jsp:include page="/pages/common/navigation.jsp"/>
            	</stripes:layout-component>

				<div id="workarea">
				
					<!--  validation errors -->
					<stripes:errors/>
					
					<!--  messages -->
					<stripes:layout-component name="messages">
						<c:if test="${not empty systemMessages}">
							<div class="system-msg">
								<stripes:messages key="systemMessages"/>
							</div>
						</c:if>
						<c:if test="${not empty cautionMessages}">
							<div class="caution-msg">
								<strong>Caution ...</strong>
								<stripes:messages key="cautionMessages"/>
							</div>
						</c:if>
						<c:if test="${not empty warningMessages}">
							<div class="warning-msg">
								<strong>Warning ...</strong>
								<stripes:messages key="warningMessages"/>
							</div>
						</c:if>
					</stripes:layout-component>

					<stripes:layout-component name="contents"/>
					
				</div>
				<div id="pagefoot" style="max-width: none;">
					<p><a href="mailto:cr@eionet.europa.eu">E-mail</a> | Last harvest: 2008-01-29 | <a href="mailto:helpdesk@eionet.europa.eu?subject=Feedback from the SPARQL client website">Feedback</a></p>
					<p><a href="http://www.eea.europa.eu/"><b>European Environment Agency</b></a>
					<br/>Kgs. Nytorv 6, DK-1050 Copenhagen K, Denmark - Phone: +45 3336 7100</p>
				</div>
			</div>
		</body>
	</html>
</stripes:layout-definition>
