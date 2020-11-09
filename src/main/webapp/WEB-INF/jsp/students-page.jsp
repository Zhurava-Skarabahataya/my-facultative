<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:out value="${students}" /></title>

<style>
<%@ include file="/css/style.css"%>
</style>

<c:set var="commandToLanguageChanger" scope="session"
	value="go_to_students_page" />

<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.students_page.students_page" var="loc"
	scope="session" />
<fmt:message bundle="${loc}" key="students" var="students" />
<fmt:message bundle="${loc}" key="my_page" var="my_page" />
<fmt:message bundle="${loc}" key="my_courses" var="my_courses" />
<fmt:message bundle="${loc}" key="create_course" var="create_course" />
<fmt:message bundle="${loc}" key="go_to_staff_page" var="go_to_staff_page" />
<fmt:message bundle="${loc}" key="go_to_students_page" var="go_to_students_page" />
<fmt:message bundle="${loc}" key="log_out" var="log_out" />
<fmt:message bundle="${loc}" key="faculty_students" var="faculty_students" />
<fmt:message bundle="${loc}" key="university_students" var="university_students" />
<fmt:message bundle="${loc}" key="name" var="name" />
<fmt:message bundle="${loc}" key="photo" var="photo" />
<fmt:message bundle="${loc}" key="more" var="more" />
<fmt:message bundle="${loc}" key="rating" var="rating" />
<fmt:message bundle="${loc}" key="no_photo" var="no_photo" />
<fmt:message bundle="${loc}" key="to_student_page" var="to_student_page" />
<fmt:message bundle="${loc}" key="more" var="more" />


</head>
<body>
	<jsp:include page="header.jsp" />
	
	<form style="display: inline" action="Controller" method="post">
		<input type="hidden" name="command" value="go_to_user_page" /> <input
			type="submit" value="${my_page}" /><br />
	</form>

	<form style = "display:inline" action="Controller" method="post">
				<input type="hidden" name="command"
					value="go_to_user_courses_page" /> <input type="submit"
					value="${my_courses}" /><br />
			</form>

	<c:if test="${sessionScope.bean.userRoleId == 2}">

		<form style="display: inline; float: center" action="Controller"
			method="post">
			<input type="hidden" name="command"
				value="go_to_create_run_course_page" /> <input type="submit"
				value="${create_course}" /><br />
		</form>

	</c:if>
	<c:if
		test="${sessionScope.bean.userRoleId >2 && sessionScope.bean.userStatusId == 2}">

		<form style="display: inline; float: center" action="Controller"
			method="post">
			<input type="hidden" name="command" value="go_to_staff_page" /> <input
				type="submit" value="${go_to_staff_page}" /><br />
		</form>
		<form style="display: inline; float: center" action="Controller"
			method="post">
			<input type="hidden" name="command" value="go_to_students_page" /> <input
				type="submit" value="${go_to_students_page}" /><br />
		</form>

	</c:if>


	<form style="display: inline" action="Controller" method="post">
		<input type="hidden" name="command" value="logout" /> <input
			type="submit" value="${log_out}" /><br />
	</form>
	
	
	<c:choose>
		<c:when test="${sessionScope.bean.userRoleId == 3}">
			<div class="inscription">
				<c:out value="${faculty_students}" />
			</div>
		</c:when>
		<c:when test="${sessionScope.bean.userRoleId == 4}">
			<div class="inscription">
				<c:out value="${university_students}" />
			</div>
		</c:when>
	</c:choose>
	
	<c:if test="${sessionScope.bean.userRoleId == 3}">
		<table>
		<tr>
			<th><c:out value="${name}"/></th>
			<th><c:out value="${photo}"/></th>
								<th><c:out value="${rating}" /></th>
			
			<th><c:out value="${more}"/></th>
		</tr>
		<c:forEach var="student" items="${requestScope.students}">
			<tr>
				<td><c:out value="${student.userFirstName}"/> 
				<c:if test="${student.userPatronymic != null}"><c:out value="${student.userPatronymic}"/> </c:if>
				<c:out value="${student.userSecondName}"/> </td>
				<td><img src="${student.userPhotoLink}" alt="${no_photo}"	height=200 width=200></td>
									<th><c:out value="${student.studentRating}" /></th>
				
				<td><form action="Controller" method="post">
				<input type="hidden" name="command"	value="go_to_another_user_page" /> 
				<input type="hidden" name="userId"	value="${student.userId}" /> 
				<input type="submit" value="${to_student_page}" />
			</form>
		</td>
			</tr>
		</c:forEach>
		</table>
		
		
	</c:if>
	<c:if test="${sessionScope.bean.userRoleId == 4}">
	
		<c:forEach var="department" items="${requestScope.allStudents}">
			<div class="inscription">	<c:out value="${department.departmentName}" />	</div>
			<table>
				<tr>
					<th><c:out value="${name}" /></th>
					<th><c:out value="${photo}" /></th>
					<th><c:out value="${rating}" /></th>
					<th><c:out value="${more}" /></th>
					
				</tr>
				<c:forEach var="student" items="${department.students}">
					<tr>
						<td><c:out value="${student.userFirstName}"/> 
				<c:if test="${student.userPatronymic != null}"><c:out value="${student.userPatronymic}"/> </c:if>
				<c:out value="${student.userSecondName}"/> </td>
				<td><img src="${student.userPhotoLink}" alt="${no_photo}"	height=200 width=200></td>
				
										<td><c:out value="${student.studentRating}" /></td>
				
				<td><form action="Controller" method="post">
				<input type="hidden" name="command"	value="go_to_another_user_page" /> 
				<input type="hidden" name="userId"	value="${student.userId}" /> 
				<input type="submit" value="${to_student_page}" />
			</form></td>
					</tr>
				</c:forEach>
			
			</table>
		
		</c:forEach>
	
		
	</c:if>
	
	
	
	<jsp:include page="footer.jsp" />


</body>
</html>