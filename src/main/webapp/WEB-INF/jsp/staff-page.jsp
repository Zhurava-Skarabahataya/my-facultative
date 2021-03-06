<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:out value="${staff}" /></title>

<style>
<%@ include file="/css/style.css"%>
</style>

<c:set var="commandToLanguageChanger" scope="session"
	value="go_to_staff_page" />

<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.staff_page.staff_page" var="loc"
	scope="session" />
<fmt:message bundle="${loc}" key="staff" var="staff" />
<fmt:message bundle="${loc}" key="faculty_staff" var="faculty_staff" />
<fmt:message bundle="${loc}" key="university_staff"
	var="university_staff" />
<fmt:message bundle="${loc}" key="name" var="name" />
<fmt:message bundle="${loc}" key="photo" var="photo" />
<fmt:message bundle="${loc}" key="position" var="position" />
<fmt:message bundle="${loc}" key="phone" var="phone" />
<fmt:message bundle="${loc}" key="adress" var="adress" />
<fmt:message bundle="${loc}" key="date_of_birth" var="date_of_birth" />
<fmt:message bundle="${loc}" key="status" var="status" />
<fmt:message bundle="${loc}" key="no_photo" var="no_photo" />
<fmt:message bundle="${loc}" key="my_page" var="my_page" />
<fmt:message bundle="${loc}" key="my_courses" var="my_courses" />
<fmt:message bundle="${loc}" key="create_course" var="create_course" />
<fmt:message bundle="${loc}" key="approve" var="approve" />
<fmt:message bundle="${loc}" key="disapprove" var="disapprove" />
<fmt:message bundle="${loc}" key="watch_page" var="watch_page" />
<fmt:message bundle="${loc}" key="go_to_staff_page"
	var="go_to_staff_page" />
<fmt:message bundle="${loc}" key="go_to_students_page"
	var="go_to_students_page" />
<fmt:message bundle="${loc}" key="log_out" var="log_out" />
<fmt:message bundle="${loc}" key="working" var="working" />
<fmt:message bundle="${loc}" key="fired" var="fired" />


</head>
<body>
	<jsp:include page="header.jsp" />

	<form style="display: inline" action="Controller" method="post">
		<input type="hidden" name="command" value="go_to_user_page" /> <input
			type="submit" value="${my_page}" /><br />
	</form>

	<form style="display: inline" action="Controller" method="post">
		<input type="hidden" name="command" value="go_to_user_courses_page" />
		<input type="submit" value="${my_courses}" /><br />
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
				<c:out value="${faculty_staff}" />
			</div>
		</c:when>
		<c:when test="${sessionScope.bean.userRoleId == 4}">
			<div class="inscription">
				<c:out value="${university_staff}" />
			</div>
		</c:when>
	</c:choose>

	<c:if test="${sessionScope.bean.userRoleId == 3 }">
		<table>
			<tr>
				<th><c:out value="${name}" /></th>
				<th><c:out value="${photo}" /></th>
				<th><c:out value="${position}" /></th>
				<th><c:out value="${phone}" /></th>
				<th><c:out value="${adress}" /></th>
				<th><c:out value="${date_of_birth}" /></th>
				<th><c:out value="${status}" /></th>
			</tr>
			<c:forEach var="employee"
				items="${requestScope.staff.notApprovedStaff}">
				<tr>
					<td><c:out value="${employee.userFirstName}" /> <c:if
							test="${employee.userPatronymic != null}">
							<c:out value="${employee.userPatronymic}" />
						</c:if> <c:out value="${employee.userSecondName}" /><br>
						<form action="Controller" method="post">
							<input type="hidden" name="command"
								value="go_to_another_user_page" /> <input type="hidden"
								name="userId" value="${employee.userId}" /> <input
								type="submit" value="${watch_page}" />
						</form></td>
						
					<td><img src="${employee.userPhotoLink}" width=100px
						alt="${no_photo}" /></td>
						
					<td><c:out value="${employee.userRole}" /></td>
					<td><c:out value="${employee.userPhone}" /></td>
					<td><c:out value="${employee.userAdress}" /></td>
					<td><c:out value="${employee.userDateOfBirth}" /></td>
					<td><c:choose>
							<c:when test="${employee.userStatusId == 1}">
								<form action="Controller" method="post">
									<input type="hidden" name="command" value="approve_employee" />
									<input type="hidden" name="employeeId"
										value="${employee.userId}" /> <input type="submit"
										value="${approve}" />
								</form>
								<form action="Controller" method="post">
									<input type="hidden" name="command" value="disapprove_employee" />
									<input type="hidden" name="employeeId"
										value="${employee.userId}" /> <input type="submit"
										value="${disapprove}" />
								</form>

							</c:when>
							<c:when test="${employee.userStatusId == 2}">
								<c:out value="${working}" />
							</c:when>
							<c:when test="${employee.userStatusId == 4}">
								<c:out value="${fired}" />
							</c:when>
						</c:choose></td>
				</tr>

			</c:forEach>
			<c:forEach var="employee" items="${requestScope.staff.workingStaff}">
				<tr>
					<td><c:out value="${employee.userFirstName}" /> <c:if
							test="${employee.userPatronymic != null}">
							<c:out value="${employee.userPatronymic}" />
						</c:if> <c:out value="${employee.userSecondName}" />
						<form action="Controller" method="post">
							<input type="hidden" name="command"
								value="go_to_another_user_page" /> <input type="hidden"
								name="userId" value="${employee.userId}" /> <input
								type="submit" value="${watch_page}" />
						</form></td>
					<td><img src="${employee.userPhotoLink}" width=100px
						alt="${no_photo}" /></td>
					<td><c:out value="${employee.userRole}" /></td>
					<td><c:out value="${employee.userPhone}" /></td>
					<td><c:out value="${employee.userAdress}" /></td>
					<td><c:out value="${employee.userDateOfBirth}" /></td>
					<td><c:out value="${working}" /></td>
				</tr>
			</c:forEach>

		</table>
	</c:if>

	<c:if test="${sessionScope.bean.userRoleId == 4 }">
		<c:forEach var="departmentStaff" items="${requestScope.allStaff}">
			<div class="inscription">
				<c:out value="${departmentStaff.departmentName}" />
			</div>
			<table>
				<tr>
					<th><c:out value="${name}" /></th>
					<th><c:out value="${photo}" /></th>
					<th><c:out value="${position}" /></th>
					<th><c:out value="${phone}" /></th>
					<th><c:out value="${adress}" /></th>
					<th><c:out value="${date_of_birth}" /></th>
					<th><c:out value="${status}" /></th>
				</tr>

				<c:forEach var="employee"
					items="${departmentStaff.notApprovedStaff}">
					<tr>
						<td><c:out value="${employee.userFirstName}" /> <c:if
								test="${employee.userPatronymic != null}">
								<c:out value="${employee.userPatronymic}" />
							</c:if> <c:out value="${employee.userSecondName}" /> <br>
							<form action="Controller" method="post">
								<input type="hidden" name="command"
									value="go_to_another_user_page" /> <input type="hidden"
									name="userId" value="${employee.userId}" /> <input
									type="submit" value="${watch_page}" />
							</form></td>
						<td><img src="${employee.userPhotoLink}" width=100px
							alt="${no_photo}" /></td>
						<td><c:out value="${employee.userRole}" /></td>
						<td><c:out value="${employee.userPhone}" /></td>
						<td><c:out value="${employee.userAdress}" /></td>
						<td><c:out value="${employee.userDateOfBirth}" /></td>
						<td>
							<form action="Controller" method="post">
								<input type="hidden" name="command" value="approve_employee" />
								<input type="hidden" name="employeeId"
									value="${employee.userId}" /> <input type="submit"
									value="${approve}" />
							</form>
							<form action="Controller" method="post">
								<input type="hidden" name="command" value="disapprove_employee" />
								<input type="hidden" name="employeeId"
									value="${employee.userId}" /> <input type="submit"
									value="${disapprove}" />
							</form>

						</td>
					</tr>

				</c:forEach>

				<c:forEach var="employee" items="${departmentStaff.workingStaff}">
					<tr>
						<td><c:out value="${employee.userFirstName}" /> <c:if
								test="${employee.userPatronymic != null}">
								<c:out value="${employee.userPatronymic}" />
							</c:if> <c:out value="${employee.userSecondName}" />
							<form action="Controller" method="post">
								<input type="hidden" name="command"
									value="go_to_another_user_page" /> <input type="hidden"
									name="userId" value="${employee.userId}" /> <input
									type="submit" value="${watch_page}" />
							</form></td>
						<td><img src="${employee.userPhotoLink}" width=100px
							alt="${no_photo}" /></td>
						<td><c:out value="${employee.userRole}" /></td>
						<td><c:out value="${employee.userPhone}" /></td>
						<td><c:out value="${employee.userAdress}" /></td>
						<td><c:out value="${employee.userDateOfBirth}" /></td>
						<td>><c:out value="${working}" /></td>
					</tr>
				</c:forEach>

			</table>
		</c:forEach>
	</c:if>

	<jsp:include page="footer.jsp" />

</body>
</html>