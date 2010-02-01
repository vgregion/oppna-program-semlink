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
	
		<p>
        <form:label path="rootUrl"><fmt:message key="form.rootUrl"/></form:label>
        <form:errors path="rootUrl" cssClass="errors"/>
        <form:input path="rootUrl" cssClass="txt" />
		</p>
		
		<p>
        <form:label path="source"><fmt:message key="form.source"/></form:label>
        <form:errors path="source" cssClass="errors"/>
        <form:input path="source" cssClass="txt"/>
		</p>
		
		<p>
        <form:label path="depth"><fmt:message key="form.depth"/></form:label> 
        <form:errors path="depth" cssClass="errors"/>
        <form:input path="depth" cssClass="txt" />
        </p>
        
        <p>
        <form:label path="whiteListPattern"><fmt:message key="form.whiteListPattern"/></form:label>  
        <form:errors path="whiteListPattern" cssClass="errors"/>
        <form:input path="whiteListPattern" cssClass="txt" />
        </p>
        
        <p>
        <form:label path="blackListPattern"><fmt:message key="form.blackListPattern"/></form:label> 
        <form:errors path="blackListPattern" cssClass="errors" />
        <form:input path="blackListPattern" cssClass="txt" />
        </p>
        
        <p>
        <form:label path="uniqueRepositoryName"><fmt:message key="form.uniqueRepositoryName"/></form:label> 
        <form:errors path="uniqueRepositoryName" cssClass="errors" />
        <form:input path="uniqueRepositoryName" cssClass="txt" />
        </p>
        
        <p>
        <form:label path="fullReCrawl"><fmt:message key="form.fullRecrawl"/></form:label>
        <form:errors path="fullReCrawl" cssClass="errors" />
        <form:select path="fullReCrawl">
        	<form:option label="Ja" value="true"/>
        	<form:option label="Nej" value="false"/>
        </form:select>
        </p>
        
        <p>
        <form:label path="sourceType"><fmt:message key="form.sourceType"/></form:label>
        <form:errors path="sourceType" cssClass="errors" />
        <form:select path="sourceType">
        	<form:option label="Webbdokument" value="WEB"/>
        </form:select>
        </p>
        
        <p>
		<c:choose>
          <c:when test="${new}">
            <input type="submit" value='<fmt:message key="button.add"/>'/>
          </c:when>
          <c:otherwise>
            <input type="submit" value='<fmt:message key="button.update"/>' />
          </c:otherwise>
        </c:choose>
        </p>
			
	</form:form>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>