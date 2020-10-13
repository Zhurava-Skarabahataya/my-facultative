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

</head>
<body>

	User PAGE

	<br>
	<div class="user_photo">
	<img src="${requestScope.bean.userPhotoLink}" alt="Upload user photo."
		height=200 width = 200 align="center">
	

	<form action="Controller" method="post" enctype="multipart/form-data">
		<input type="hidden" name="command" value="upload_user_photo" /> 
			<input type="file" id="image"		name="file" accept="image/*"/> 
			<input type="submit"	name="submit"/>
	</form>
</div>

<div class="main_user_info">

	

	<c:out value="${requestScope.bean.userFirstName}" />
	<br>
	<c:out value="${requestScope.bean.userSecondName}" />
	<br>
	<c:out value="${requestScope.bean.userPatronymic}" /><br>
	<c:out value="${requestScope.bean.userRole}" />
	<br>




</div>

</body>
</html>