<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

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
</head>
<body>
	<div
		style="overflow: hidden; background-color: #f1f1f1; 
		position: relative; padding: 15px; text-align: center; background: #1abc9c; color: white; font-size: 30px;">
		<div style="vertical-align: middle; display: inline;">
			<form style="display: inline;" action="Controller" method="post">
				<input type="hidden" name="command" value="go_to_welcome_page" /> <input
					type="image" src="image/logo.png" width="100" />
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
</body>
</html>