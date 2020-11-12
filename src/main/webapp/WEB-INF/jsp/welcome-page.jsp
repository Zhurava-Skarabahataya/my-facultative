<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:out value="${page_title}" /></title>

<style>
<%@ include file="/css/style.css"%>
</style>

<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.welcome_page.welcome_page"
	var="loc" scope="session" />

<fmt:message bundle="${loc}" key="page_title" var="page_title" />
<fmt:message bundle="${loc}" key="welcomepage.message.welcome"
	var="message" />
<fmt:message bundle="${loc}" key="welcomepage.message.choose_form"
	var="enter" />
<fmt:message bundle="${loc}" key="welcomepage.button.name.registration"
	var="reg_button" />
<fmt:message bundle="${loc}" key="welcomepage.button.name.authorization"
	var="auth_button" />
<fmt:message bundle="${loc}"
	key="welcomepage.button.name.go_to_user_page" var="go_to_user_page" />

<c:set var="commandToLanguageChanger" scope="session"
	value="go_to_welcome_page" />
</head>
<body>
	<jsp:include page="header.jsp" />

	<div
		style="background-image: url(image/main-page-photo.jpg); background-size: contain; background-repeat: no-repeat; height: 600px;">

		<div style="margin: 25px; padding: 20px;">
			<h1 align="left" style="color: white; width: 400px; opacity: 0.9">
				<c:out value="${message}" />
			</h1>
		</div>

		<c:if test="${sessionScope.userLogin == null}">
			<div
				style="text-aligh: center; width: 300px; padding: 30px; margin: 30px;">
				<div style="text-align: center; opacity: 0.8">
					<form action="Controller" method="post">
						<input type="hidden" name="command"
							value="go_to_registration_page" /> <input type="submit"
							value="${reg_button}" /><br />
					</form>
					<br>
					<form action="Controller" method="post">
						<input type="hidden" name="command"
							value="go_to_authorization_page" /> <input type="submit"
							value="${auth_button}" /><br />
					</form>
				</div>
			</div>

		</c:if>
		<c:if test="${sessionScope.userLogin != null}">
			<div
				style="text-aligh: center; width: 300px; padding: 30px; margin: 30px;">
				<div style="text-align: center; opacity: 0.8">
					<div class="inscription">
						GREETINGS,
						<c:out value="${sessionScope.bean.userFirstName}" />
						<c:if test="${sessionScope.bean.userPatronymic!=null}">
							<c:out value="${sessionScope.bean.userPatronymic}" />
						</c:if>
					</div>
					<form action="Controller" method="post">
						<input type="hidden" name="command" value="go_to_user_page" /> <input
							type="submit" value="${go_to_user_page}" /><br />
					</form>

				</div>
			</div>
		</c:if>
	</div>


	<img src="image/welcome-cat.png" alt="Здесь должен был быть кот."
		height=200 style="opacity:0.9">

	<div style = "display:inline-block; padding:5px; ">
		<c:forEach var="news" items="${requestScope.info.news}">
		
		<div style="width:45%; float:left;background-color: #f1f1f1; 	opacity:0.9;
	border-radius: 10px;padding:5px; margin:5px;font-weight: bold;"><c:out value="${news.title}"/><hr>
		
			<div style = "display:inline-block;font-weight: normal;">
				<div style="width:40%; float:left;"><img width = 100% alt="" src="${news.imagePath}"></div>
				<div style="width:40%; float:left;"><c:out value="${news.text}"/></div>
			</div>
	
		</div>
		</c:forEach>
	
	</div>

	<c:out value="${popularCourses}" />
	<jsp:include page="footer.jsp" />

</body>
</html>