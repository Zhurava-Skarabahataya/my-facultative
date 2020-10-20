<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
<%@ include file="/css/style.css"%>
</style>
<fmt:setLocale value="${sessionScope.local}" />


<fmt:setBundle basename="localization.header.header" var="loc_header"
	scope="session" />

<fmt:message bundle="${loc_header}" key="header.button.name.ru"
	var="ru_button" />
<fmt:message bundle="${loc_header}" key="header.button.name.en"
	var="en_button" />
<fmt:message bundle="${loc_header}" key="header.button.name.by"
	var="by_button" />
<fmt:message bundle="${loc_header}" key="header.university_name"
	var="university_name" />
<fmt:message bundle="${loc_header}" key="header.menu.about_university"
	var="about" />
<fmt:message bundle="${loc_header}" key="header.menu.departments"
	var="departments" />
<fmt:message bundle="${loc_header}" key="header.menu.courses"
	var="courses" />
<fmt:message bundle="${loc_header}" key="header.menu.contacts"
	var="contacts" />
<fmt:message bundle="${loc_header}" key="header.menu.news"
	var="news" />
</head>
<body>
	<div class="header">
		<div style="vertical-align: middle; display: inline;">
			<form style="display: inline;" action="Controller" method="post">
				<input type="hidden" name="command" value="go_to_welcome_page" /> <input
					type="image" src="image/logo.png" width="100px" height = "300px"/>
			</form>


			<c:out value="${university_name}" />

		</div>

		<form style="display: inline; float: right;" action="Controller"
			method="post">
			<input type="hidden" name="command" value="change_language" /> <input
				type="hidden" name="local" value="ru" /> <input type="hidden"
				name="uri" value="${adress}" /> <input type="submit"
				value="${ru_button}" />
		</form>
		<form style="display: inline; float: right;" action="Controller"
			method="post">
			<input type="hidden" name="command" value="change_language" /> <input
				type="hidden" name="local" value="en" /> <input type="hidden"
				name="uri" value="${adress}" /><input type="submit"
				value="${en_button}" />
		</form>
		<form style="display: inline; float: right;" action="Controller"
			method="post">
			<input type="hidden" name="command" value="change_language" /> <input
				type="hidden" name="local" value="be" /> <input type="hidden"
				name="uri" value="${adress}" /><input type="submit"
				value="${by_button}" />
		</form>



	</div>
	
	<ul class="menu-main">
  		<li><a href="Controller?command=go_to_about_university_page"><c:out value="${about}"/></a></li>
  		<li><a href="Controller?command=go_to_departments_page"><c:out value="${departments}"/></a></li>
  		<li><a href="Controller?command=go_to_available_run_courses_page"><c:out value="${courses}"/></a></li>
  		<li><a href="Controller?command=go_to_contacts_page"><c:out value="${contacts}"/></a></li>
  		<li><a href="Controller?command=go_to_news_page"><c:out value="${news}"/></a></li>
	</ul>
</body>
</html>