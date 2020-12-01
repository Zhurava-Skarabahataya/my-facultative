b<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:out value="${editing_page_title}"/></title>

<style>
<%@include file ="/css/style.css"%>
</style>

<c:set var="commandToLanguageChanger" scope="session"
	value="go_to_edit_user_info_command" />

<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.user_page.user_page" var="loc"
	scope="session" />
<fmt:message bundle="${loc}" key="editing_page_title"
	var="editing_page_title" />
<fmt:message bundle="${loc}" key="upload_photo" var="upload_photo" />
<fmt:message bundle="${loc}" key="field.first_name"
	var="field.first_name" />
<fmt:message bundle="${loc}" key="field.second_name"
	var="field.second_name" />
<fmt:message bundle="${loc}" key="field.patronymic"
	var="field.patronymic" />
<fmt:message bundle="${loc}" key="field.role" var="field.role" />
<fmt:message bundle="${loc}" key="user_first_name" var="user_first_name" />
<fmt:message bundle="${loc}" key="user_second_name" var="user_second_name" />
<fmt:message bundle="${loc}" key="user_patronymic" var="user_patronymic" />
<fmt:message bundle="${loc}" key="user_role" var="user_role" />
<fmt:message bundle="${loc}" key="user_faculty" var="user_faculty" />
<fmt:message bundle="${loc}" key="user_adress" var="user_adress" />
<fmt:message bundle="${loc}" key="user_date_of_birth" var="user_date_of_birth" />
<fmt:message bundle="${loc}" key="user_tel_number" var="user_tel_number" />
<fmt:message bundle="${loc}" key="field.faculty" var="field.faculty" />
<fmt:message bundle="${loc}" key="field.adress" var="field.adress" />
<fmt:message bundle="${loc}" key="field.date_of_birth"
	var="field.date_of_birth" />
<fmt:message bundle="${loc}" key="field.tel_number"
	var="field.tel_number" />
<fmt:message bundle="${loc}" key="save_changes" var="save_changes" />

</head>

<body>
	<jsp:include page="header.jsp" />

	<br>
	<div class="user_photo"
		style="float: left; position: related; width: 30%; align: center">
		<img src="${sessionScope.bean.userPhotoLink}" alt="Upload user photo."
			height=200 width=200
			style="display: block; margin-left: auto; margin-right: auto">
		<br>
		<div align="center" style="text-align: center">
			<form action="Controller" method="post" enctype="multipart/form-data">
				<input type="hidden" name="command" value="upload_user_photo" /> <input
					type="file" id="image" name="file" accept="image/*" /><br> <input
					type="submit" value="${upload_photo}" name="${upload_photo}" />
			</form>
		</div>
	</div>

	<div class="main_user_info" style="float: rigth; position: related">
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="edit_user_info" />

			<table class="table_user_info">
				<tr>
					<td><c:out value="${user_first_name}" /></td>
					<td><input type="text" name="userFirstName" required
						value="${sessionScope.bean.userFirstName}" /></td>
				</tr>
				<tr>
					<td><c:out value="${user_second_name}" /></td>
					<td><input type="text" name="userSecondName" required
						value="${sessionScope.bean.userSecondName}" /></td>
				</tr>
				<tr>
					<td><c:out value="${user_patronymic}" /></td>
					<td><input type="text" name="userPatronymic"
						value="${sessionScope.bean.userPatronymic}" /></td>
				</tr>
				<tr>
					<td><c:out value="${user_role}" /></td>
					<td><c:out value="${sessionScope.bean.userRole}" /></td>
				</tr>
				<tr>
					<td><c:out value="${user_faculty}" /></td>
					<td><select name="faculty" size="1">
							<option selected="selected" disabled><c:out
									value="${sessionScope.bean.userFaculty}" /></option>

							<option value="1">Факультет Травоведения и Зельелогии</option>
							<option value="2">Факультет Прикладной Магии</option>
							<option value="3">Факультет Целительства и Знахарства</option>
							<option value="4">Факультет Ворожбы, Астрологии и
								Нумерологии</option>
							<option value="5">Факультет Нелюделогии и Нечистоведения</option>
							<option value="6">Факультет Шаманства и Метеорологии</option>
							<option value="7">Факультет Чернокнижества и Некромантии</option>
					</select></td>
				</tr>
				<tr>
					<td><c:out value="${user_adress}" /></td>
					<td><input type="text" name="userAdress"
						value="${sessionScope.bean.userAdress}" /></td>
				</tr>
				<tr>
					<td><c:out value="${user_date_of_birth}" /></td>
					<td><input type="date"  pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}" id="userDateOfBirth"
						name="userDateOfBirth"
						value="${sessionScope.bean.userDateOfBirth}" />
				</tr>
				<tr>
					<td><c:out value="${user_tel_number}" /></td>
					<td><input type="text" name="userPhone"
						value="${sessionScope.bean.userPhone}" /></td>
				</tr>
			</table>

			<br>
			<div align="center">
				<input type="submit" value="${save_changes}" /><br />
			</div>
		</form>


	</div>
	<jsp:include page="footer.jsp" />

</body>
</html>