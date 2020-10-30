<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User page</title>
<style>
<%@ include file="/css/style.css"%>
</style>


<c:set var="commandToLanguageChanger" scope="session"
	value="go_to_user_page" />

<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.user_page.user_page" var="loc"
	scope="session" />
<fmt:message bundle="${loc}" key="message.upload_user_photo"
	var="upload_photo" />
<fmt:message bundle="${loc}" key="button.upload_user_photo"
	var="key_upload_photo" />
<fmt:message bundle="${loc}" key="button.my_page"
	var="my_page" />
<fmt:message bundle="${loc}" key="button.my_courses"
	var="my_courses" />
<fmt:message bundle="${loc}" key="button.my_rating"
	var="my_rating" />
<fmt:message bundle="${loc}" key="button.create_course"
	var="create_course" />
<fmt:message bundle="${loc}" key="field.first_name"
	var="first_name" />
<fmt:message bundle="${loc}" key="field.second_name"
	var="second_name" />
<fmt:message bundle="${loc}" key="field.patronymic"
	var="patronymic" />
<fmt:message bundle="${loc}" key="field.role"
	var="role" />
<fmt:message bundle="${loc}" key="field.faculty"
	var="faculty" />
<fmt:message bundle="${loc}" key="field.adress"
	var="adress" />
<fmt:message bundle="${loc}" key="field.date_of_birth"
	var="date_of_birth" />
<fmt:message bundle="${loc}" key="field.tel_number"
	var="tel_number" />
<fmt:message bundle="${loc}" key="button.edit"	var="edit" />
<fmt:message bundle="${loc}" key="button.go_to_staff_page"	var="go_to_staff_page" />
<fmt:message bundle="${loc}" key="button.go_to_students_page"	var="go_to_students_page" />



</head>
<body>
	<jsp:include page="header.jsp" />
	
	<div style="display: inline;" >
	
			<form style = "display:inline" action="Controller" method="post">
				<input type="hidden" name="command"
					value="go_to_user_page" /> <input type="submit"
					value="${my_page}" /><br />
			</form>
		
			<form style = "display:inline" action="Controller" method="post">
				<input type="hidden" name="command"
					value="go_to_user_courses_page" /> <input type="submit"
					value="${my_courses}" /><br />
			</form>
		
		<c:if test="${sessionScope.bean.userRoleId == 1}">
		
		<form style = "display:inline;  float:center" action="Controller" method="post">
				<input type="hidden" name="command"
					value="go_to_user_rating_page" /> <input type="submit"
					value="${my_rating}" /><br />
			</form>
		
		
		</c:if>
		<c:if test="${sessionScope.bean.userRoleId == 2}">
		
		<form style = "display:inline; float:center" action="Controller" method="post">
				<input type="hidden" name="command"
					value="go_to_create_run_course_page" /> <input type="submit"
					value="${create_course}" /><br />
			</form>
		
		</c:if>
		<c:if test="${sessionScope.bean.userRoleId >2 }">
		
		<form style = "display:inline; float:center" action="Controller" method="post">
				<input type="hidden" name="command"
					value="go_to_staff_page" /> <input type="submit"
					value="${go_to_staff_page}" /><br />
			</form>
			<form style = "display:inline; float:center" action="Controller" method="post">
				<input type="hidden" name="command"
					value="go_to_students_page" /> <input type="submit"
					value="${go_to_students_page}" /><br />
			</form>
		
		</c:if>
		
	
		<form style = "display:inline" action="Controller" method="post">
				<input type="hidden" name="command"
					value="logout" /> <input type="submit"
					value="Выйти" /><br />
			</form>
		</div>
		
		
	<div class="user_photo" align="center">
	
		<img src="${sessionScope.bean.userPhotoLink}"
		onerror="this.src='D:/Java/facultative-project/user_photos/default.jpg'" alt="${upload_photo}"
			height=200 width=200>
		<br>
		
		<div align="center">
			<form action="Controller" method="post" enctype="multipart/form-data">
				<input type="hidden" name="command" value="upload_user_photo" /> <input
					type="file" id="image" name="file" accept="image/*" /><br> <input
					type="submit" value="${key_upload_photo}" name="${key_upload_photo}" />
			</form>
		</div>
	</div>

	<div class="main_user_info" >

		<table class="table_user_info">
			<tr>
				<td><c:out value="${first_name}"/></td>
				<td><c:out value="${sessionScope.bean.userFirstName}" /></td>
			</tr>
			<tr>
				<td><c:out value="${second_name}"/></td>
				<td><c:out value="${sessionScope.bean.userSecondName}" /></td>
			</tr>
			<tr>
				<td><c:out value="${patronymic}"/></td>
				<td><c:out value="${sessionScope.bean.userPatronymic}" /></td>
			</tr>
			<tr>
				<td><c:out value="${role}"/></td>
				<td><c:out value="${sessionScope.bean.userRole}" /></td>
			</tr>
			<tr>
				<td><c:out value="${faculty}"/></td>
				<td><c:out value="${sessionScope.bean.userFaculty}" /></td>
			</tr>
			<tr>
				<td><c:out value="${adress}"/></td>
				<td><c:out value="${sessionScope.bean.userAdress}" /></td>
			</tr>
			<tr>
				<td><c:out value="${date_of_birth}"/></td>
				<td><c:out value="${sessionScope.bean.userDateOfBirth}" /></td>
			</tr>
			<tr>
				<td><c:out value="${tel_number}"/></td>
				<td><c:out value="${sessionScope.bean.userPhone}" /></td>
			</tr>


		</table>


			<form action="Controller" method="post">
				<input type="hidden" name="command"
					value="go_to_edit_user_info_command" /> <input type="submit"
					value="${edit}" />
			</form>
		


	</div>
	

	<jsp:include page="footer.jsp" />

</body>
</html>