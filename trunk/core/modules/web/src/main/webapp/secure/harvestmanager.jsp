<%@ include file="/WEB-INF/jsp/header.jsp" %>

<div id="content">

	<h1 class="list"><fmt:message key="form.title.manager"/></h1>

	<table cellspacing=0 cellpadding=0>
		<thead>
		<tr>
			<th class="first"><fmt:message key="form.source"/></th>
			<th><fmt:message key="form.rootUrl"/></th>
			<th><fmt:message key="form.depth"/></th>
			<th><fmt:message key="form.whiteListPattern"/></th>
			<th><fmt:message key="form.blackListPattern"/></th>
			<th class="last">&nbsp;</th>
		</tr>
		</thead>
		<tbody>
			<c:forEach items="${model.harvesterManager}" var="site" varStatus="status">
				
				<c:choose>
   					<c:when test="${status.index % 2 == 0}">
   						<tr class="even_row">
   					</c:when>
   					<c:when test="${status.index % 2 != 0}">
   						<tr class="odd_row">
   					</c:when>
  				</c:choose>
				
					<td class="first"><c:out value="${site.source}"/></td>
					<td><c:out value="${site.rootUrl}"/></td>
					<td><c:out value="${site.depth}"/></td>
					<td>
					
						<c:out value="${site.whiteListPattern}"/>
						<c:if test="${empty site.whiteListPattern}">
							&nbsp;
						</c:if>
					</td>
					<td>
						<c:out value="${site.blackListPattern}"/>
						<c:if test="${empty site.blackListPattern}">
							&nbsp;
						</c:if>
					</td>
			
					<td class="last">
						<c:url var="editUrl" value="editSite.htm">
				            <c:param name="id" value="${site.id}" />
				        </c:url>
				        <a class="icon_edit" href='<c:out value="${editUrl}"/>' title='<fmt:message key="form.edit"/>'></a>

			        	<c:url var="deleteUrl" value="deleteSite.htm"/>
				        <form id="form${site.id}" action="${deleteUrl}" method="POST">
				            <input id="id" name="id" type="hidden" value="${site.id}"/>
				        </form>
				        <a class="icon_delete" href="javascript:document.forms['form${site.id}'].submit();" title='<fmt:message key="form.delete"/>'></a>
			        </td>			
				</tr>
			</c:forEach>
		</tbody>
	
	</table>
	
	<div class="btn_icon_add_site"> <!-- buttontype -->
            <a href="<c:url value="addSite.htm"/>" class="button"> <!-- button -->
                <span><span><span><fmt:message key="button.addsite"/></span></span></span>
            </a>
    </div>
	
	
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>