<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:out value="${title}"/></title>
<style>
<%@ include file="/css/style.css"%>
</style>

<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.departments_page.departments_page" var="loc"
	scope="session" />

<c:set var="commandToLanguageChanger" scope="session"
	value="go_to_departments_page" />

<fmt:message bundle="${loc}" key="title" var="title" />
<fmt:message bundle="${loc}" key="our_departments" var="our_departments" />
<fmt:message bundle="${loc}" key="about_dpt" var="about_dpt" />
<fmt:message bundle="${loc}" key="title" var="title" />
<fmt:message bundle="${loc}" key="title" var="title" />
</head>
<body>
<jsp:include page="header.jsp" />
	<div class="inscription"><c:out value="${our_departments}"/></div>
	
	
	<table class="departmnent_table">
		<c:forEach var="department" items="${requestScope.departments}"> 
			<tr>
				<td><c:out value="${department.departmentName}"/></td>
				<td><img src="image/dep${department.departmentID}.jpg" width=150px></td>
				<td style="font-size:12px"><c:out value="${department.departmentDescription}"/></td>
				
				<td><form  action="Controller"	method="post">
					<input type="hidden" name="command" value="go_to_current_department_page_command" /> 
					<input	type="hidden" name="department_id" value="${department.departmentID}" /> 
					<input type="submit" value="${about_dpt}" />
		</form></td>
			</tr>
		
		</c:forEach>	
	
	</table>

<jsp:include page="footer.jsp" />
</body>
</html>