<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Курсы</title>


<c:set var="commandToLanguageChanger" scope="session"
	value="go_to_user_courses_page" />


</head>
<body>
	<jsp:include page="header.jsp" />

	<div>

		<form style="display: inline" action="Controller" method="post">
			<input type="hidden" name="command" value="go_to_user_page" /> <input
				type="submit" value="${my_page}" /><br />
		</form>

		<form style="display: inline" action="Controller" method="post">
			<input type="hidden" name="command" value="go_to_user_courses_page" />
			<input type="submit" value="${my_courses}" /><br />
		</form>

		<c:if test="${sessionScope.bean.userRoleId == 1}">

			<form style="display: inline; float: center" action="Controller"
				method="post">
				<input type="hidden" name="command" value="go_to_user_rating_page" />
				<input type="submit" value="${my_rating}" /><br />
			</form>


		</c:if>
		<c:if test="${sessionScope.bean.userRoleId == 2}">

			<form style="display: inline; float: center" action="Controller"
				method="post">
				<input type="hidden" name="command" value="go_to_create_course_page" />
				<input type="submit" value="${create_course}" /><br />
			</form>

		</c:if>
	</div>

	<div class="courses_info">
	
	
	
	
	</div>



</body>
</html>