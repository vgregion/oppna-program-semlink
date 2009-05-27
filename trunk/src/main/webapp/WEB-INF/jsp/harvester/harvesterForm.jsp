<%@ include file="/WEB-INF/jsp/header.jsp" %>

<div id="content">
	
	<c:choose>
         <c:when test="${new}">
           <h1 class="add"><fmt:message key="form.title.add"/></h1>
         </c:when>
         <c:otherwise>
           <h1 class="edit"><fmt:message key="form.title.edit"/></h1>
         </c:otherwise>
   </c:choose>
	
	<c:if test="${not empty success}">
    	<div id="success"><fmt:message key="${success}"/></div>
	</c:if>
	
	<form:form modelAttribute="harvester">
	
	        <form:label path="startUrl"><fmt:message key="form.startUrl"/></form:label>
	        <form:errors path="startUrl" cssClass="errors"/>
	        <form:input path="startUrl" cssClass="txt" />
	
	        <form:label path="siteName"><fmt:message key="form.siteName"/></form:label>
	        <form:errors path="siteName" cssClass="errors"/>
	        <form:input path="siteName" cssClass="txt"/>
	
	        <form:label path="depth"><fmt:message key="form.depth"/></form:label> 
	        <form:errors path="depth" cssClass="errors"/>
	        <form:input path="depth" cssClass="txt" />
	        
	        <form:label path="includePattern"><fmt:message key="form.includePattern"/></form:label>  
	        <form:errors path="includePattern" cssClass="errors"/>
	        <form:input path="includePattern" cssClass="txt" />
	        
	        <form:label path="excludePattern"><fmt:message key="form.excludePattern"/></form:label> 
	        <form:errors path="excludePattern" cssClass="errors" />
	        <form:input path="excludePattern" cssClass="txt" />
	        
			<c:choose>
	          <c:when test="${new}">
	            <input type="submit" value='<fmt:message key="button.add"/>'/>
	          </c:when>
	          <c:otherwise>
	            <input type="submit" value='<fmt:message key="button.update"/>' />
	          </c:otherwise>
	        </c:choose>
			
	</form:form>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>