<%@ include file="/WEB-INF/jsp/include.jsp" %>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
    
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="sv" lang="sv">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title><fmt:message key="title.manager"/></title>
	<link rel="stylesheet" type="text/css" href="../style/main.css" />
</head>
<body>
<div id="wrap">
	<div id="header">
		<img id="logo" height="45" width="180" alt="" src="../style/images/logo.png"/>
		<div id="logout">
			<p>
				<span class="icon_user"></span>
				<sec:authentication property="principal.username"/> | 
				<a href="../j_spring_security_logout"><fmt:message key="header.logout"/></a>
			</p>
		</div>
	</div>
	<div id="navigation">
		<ul id="nav">
			<li class="first"><a href="../" title="Start" ><fmt:message key="nav.home"/></a></li>   
			<li class="last"><a href="#"><fmt:message key="nav.source"/></a>
				<ul id="sec" >
					<li class="first"><a href="<c:url value="addSite.htm"/>"><fmt:message key="nav.addsource"/></a></li>
					<li class="last"><a href="<c:url value="harvestmanager.htm"/>"><fmt:message key="nav.sourcemanager"/></a></li>
				</ul>  
			</li>  
		</ul>
	</div>
	<div id="body" class="clear">
		<div id="breadcrumb">
			
		</div>
		
