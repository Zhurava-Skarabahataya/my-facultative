<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User page</title>


<jsp:include page="/css/style.css" />

<c:set var="commandToLanguageChanger" scope="session"
	value="go_to_user_page" />

</head>
<body>
	<jsp:include page="header.jsp" />

	<br>
	<div class="user_photo"
		style="float: left; position: related; width: 40%; align: center">
		<img src="${sessionScope.bean.userPhotoLink}" alt="Upload user photo."
			height=200 width=200
			style="display: block; margin-left: auto; margin-right: auto">
		<br>
		<div align="center" style="text-align: center">
			<form action="Controller" method="post" enctype="multipart/form-data">
				<input type="hidden" name="command" value="upload_user_photo" /> <input
					type="file" id="image" name="file" accept="image/*" /><br> <input
					type="submit" value="Загрузить фото" name="Загрузить фото" />
			</form>
		</div>
	</div>

	<div class="main_user_info" style="float: rigth; position: related">


		<table>
			<tr>
				<td>Имя:</td>
				<td><c:out value="${sessionScope.bean.userFirstName}" />
				</td>
			</tr>
			<tr>
				<td>Фамилия:</td>
				<td><c:out value="${sessionScope.bean.userSecondName}" /></td>
			</tr>
			<tr>
				<td>Отчество:</td>
				<td><c:out value="${sessionScope.bean.userPatronymic}" /></td>
			</tr>
			<tr>
				<td>Должность:</td>
				<td><c:out value="${sessionScope.bean.userRole}" /></td>
			</tr>
			<tr>
				<td>Факультет:</td>
				<td><c:out value="${sessionScope.bean.userFaculty}" /></td>
			</tr>
			<tr>
				<td>Адрес:</td>
				<td><c:out value="${sessionScope.bean.userAdress}" /></td>
			</tr>
			<tr>
				<td>Дата рождения:</td>
				<td><c:out value="${sessionScope.bean.userDateOfBirth}" /></td>
			</tr>
			<tr>
				<td>Номер телефона:</td>
				<td><c:out value="${sessionScope.bean.userPhone}" /></td>
			</tr>


		</table>

	
		<br>
		<div align="center">
			<form action="Controller" method="post">
				<input type="hidden" name="command" value="go_to_edit_user_info_command" /> <input
					type="submit" value="Редактировать данные" /><br />
			</form>
		</div>


	</div>

</body>
</html>