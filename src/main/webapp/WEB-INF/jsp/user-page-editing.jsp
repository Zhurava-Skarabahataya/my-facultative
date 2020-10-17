b<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
<%@include file ="/css/style.css"%>
</style>

<c:set var="commandToLanguageChanger" scope="session"
	value="go_to_edit_user_info_command" />

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
					type="submit" value="Загрузить фото" name="Загрузить фото" />
			</form>
		</div>
	</div>

	<div class="main_user_info" style="float: rigth; position: related">
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="edit_user_info" />

			<table  class="table_user_info">
				<tr>
					<td>Имя:</td>
					<td><input type="text" name="userFirstName" required
						value="${sessionScope.bean.userFirstName}" /></td>
				</tr>
				<tr>
					<td>Фамилия:</td>
					<td><input type="text" name="userSecondName" required
						value="${sessionScope.bean.userSecondName}" /></td>
				</tr>
				<tr>
					<td>Отчество:</td>
					<td><input type="text" name="userPatronymic" required
						value="${sessionScope.bean.userPatronymic}" /></td>
				</tr>
				<tr>
					<td>Должность:</td>
					<td><c:out
									value="${sessionScope.bean.userRole}" />
							</td>
				</tr>
				<tr>
					<td>Факультет:</td>
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
					<td>Адрес:</td>
					<td><input type="text" name="userAdress"
						value="${sessionScope.bean.userAdress}" /></td>
				</tr>
				<tr>
					<td>Дата рождения:</td>
					<td><input type="date" id="userDateOfBirth"
						name="userDateOfBirth"
						value="${sessionScope.bean.userDateOfBirth}" />
				</tr>
				<tr>
					<td>Номер телефона:</td>
					<td><input type="text" name="userPhone"
						value="${sessionScope.bean.userPhone}" /></td>
				</tr>


			</table>

			<br>
			<div align="center">
				<input type="submit" value="Сохранить данные" /><br />
		</form>
	</div>


	</div>

</body>
</html>