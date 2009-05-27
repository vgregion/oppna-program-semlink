<%@ include file="/WEB-INF/jsp/header.jsp" %>

<div id="content">
	<sec:authorize ifAllGranted="ROLE_SUPERVISOR">
		<p><a href="../">Home</a></p>
		<p><a href="<c:url value="addSite.htm"/>">Add Site</a></p>
		<p><a href="harvestmanager.htm">Harvester Manager</a></p>
		<p><a href="../j_spring_security_logout">Logout</a></p>
		
	</sec:authorize>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>