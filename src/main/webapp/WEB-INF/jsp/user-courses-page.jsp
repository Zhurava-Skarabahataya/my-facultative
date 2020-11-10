<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri = "/myTag" prefix = "my" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:out value="${my_courses}"/></title>


<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.user_courses_page.user_courses_page" var="loc"
	scope="session" />

<fmt:message bundle="${loc}" key="my_page" var="my_page" />
<fmt:message bundle="${loc}" key="my_courses" var="my_courses" />
<fmt:message bundle="${loc}" key="my_rating" var="my_rating" />
<fmt:message bundle="${loc}" key="create_course" var="create_course" />
<fmt:message bundle="${loc}" key="title" var="title" />
<fmt:message bundle="${loc}" key="date_of_start" var="date_of_start" />
<fmt:message bundle="${loc}" key="date_of_end" var="date_of_end" />
<fmt:message bundle="${loc}" key="course_status" var="course_status" />
<fmt:message bundle="${loc}" key="schedule" var="schedule" />
<fmt:message bundle="${loc}" key="classroom" var="classroom" />
<fmt:message bundle="${loc}" key="amount_of_students_on_course" var="amount_of_students_on_course" />
<fmt:message bundle="${loc}" key="students_limit_on_course" var="students_limit_on_course" />
<fmt:message bundle="${loc}" key="lecturer" var="lecturer" />
<fmt:message bundle="${loc}" key="available_places" var="available_places" />
<fmt:message bundle="${loc}" key="my_status" var="my_status" />
<fmt:message bundle="${loc}" key="more" var="more" />
<fmt:message bundle="${loc}" key="mark" var="mark" />
<fmt:message bundle="${loc}" key="log_out" var="log_out" />
<fmt:message bundle="${loc}" key="more_about_course" var="more_about_course" />
<fmt:message bundle="${loc}" key="no_courses_yet" var="no_courses_yet" />
<fmt:message bundle="${loc}" key="view_available_run_courses" var="view_available_run_courses" />
<fmt:message bundle="${loc}" key="course_ended" var="course_ended" />
<fmt:message bundle="${loc}" key="ended_courses" var="ended_courses" />
<fmt:message bundle="${loc}" key="course_cancelled" var="course_cancelled" />
<fmt:message bundle="${loc}" key="course_recruting" var="course_recruting" />
<fmt:message bundle="${loc}" key="course_running" var="course_running" />

<c:set var="commandToLanguageChanger" scope="session"
	value="go_to_user_courses_page" />


</head>
<body>
	<jsp:include page="header.jsp" />

	<div class="navigate_buttons">

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
				<input type="hidden" name="command" value="view_student_rating_page" />
								<input type="hidden" name="userLogin"	value="${bean.userLogin}" /> 
				
				<input type="submit" value="${my_rating}" /><br />
			</form>


		</c:if>
		<c:if test="${sessionScope.bean.userRoleId == 2}">

			<form style="display: inline; float: center" action="Controller"
				method="post">
				<input type="hidden" name="command" value="go_to_create_run_course_page" />
				<input type="submit" value="${create_course}" /><br />
			</form>

		</c:if>
		<form style = "display:inline" action="Controller" method="post">
				<input type="hidden" name="command"
					value="logout" /> <input type="submit"
					value="${log_out}" /><br />
			</form>
	</div>

	<div>
		<c:if test="${!empty sessionScope.bean.currentCourses}"> 
	<div class = "inscription"><c:out value="${my_courses}"/></div>
	
		<table>
				<tr>
					<th><c:out value="${title}"/></th>
					<th><c:out value="${date_of_start}"/></th>

					<th><c:out value="${date_of_end}"/></th>
					<th><c:out value="${course_status}"/></th>
					<th><c:out value="${schedule}"/></th>
					
					<th><c:out value="${classroom}"/></th>
					<c:if test="${sessionScope.bean.userRoleId > 1}">
					<th><c:out value="${amount_of_students_on_course}"/></th>
					<th><c:out value="${students_limit_on_course}"/></th>
					</c:if>

					<c:if test="${sessionScope.bean.userRoleId == 1}">
						<th><c:out value="${available_places}"/></th>
					</c:if>
					<c:if test="${sessionScope.bean.userRoleId == 1}">
						<th><c:out value="${my_status}"/></th>
					</c:if>

					<th><c:out value="${more}"/></th>
				</tr>
				<c:forEach var="course" items="${sessionScope.bean.currentCourses}">
					<tr>
						<td>
							<c:out value="${course.courseName}" />
						
						</td>
						<td><my:formDate date="${course.dateOfStart}" /></td>
						<td><my:formDate date="${course.dateOfEnd}" /></td>
						<td><c:choose>
				<c:when test="${course.currentState == 1}"><c:out value="${course_canselled}"/> </c:when>
				<c:when test="${course.currentState== 2}"><c:out value="${course_ended}"/> </c:when>
				<c:when test="${course.currentState== 3}"><c:out value="${course_recruting}"/> </c:when>
				<c:when test="${course.currentState== 4}"><c:out value="${course_running}"/> </c:when>
				
			</c:choose></td>

						<td><c:out value="${course.shedule}" /></td>
						
						<td><c:out value="${course.classroomNumber}" /></td>
						<c:if test="${sessionScope.bean.userRoleId > 1}">
							<td><c:out value="${course.studentAmount}" /></td>
							<td><c:out value="${course.studentLimit}" /></td>
						</c:if>

						<c:if test="${sessionScope.bean.userRoleId == 1}">
							<td><c:out
									value="${course.studentLimit - course.studentAmount}" /></td>
						</c:if>
						<c:if test="${sessionScope.bean.userRoleId == 1}">
							<td><c:out value="${course.studentStatusName}" /></td>
						</c:if>
						<td>

							<form action="Controller" method="post">
								<input type="hidden" name="command"
									value="go_to_run_course_page" /> 
									<input type="hidden" name="run_course_id"
									value="${course.runCourseId}" /><input type="submit"
									value="${more_about_course}" />
							</form>

						</td>

					</tr>

				</c:forEach>

			</table>
		</c:if>
	</div>
	<br>
	
		<div style="padding: 15px">
		<c:if test="${empty sessionScope.bean.currentCourses}"> 
		<div class="inscription"><c:out value="${no_courses_yet}"/></div>
		<br>
			<br>

		</c:if>

		<c:if test="${sessionScope.bean.userRoleId == 1}">

			<form action="Controller" method="post">
				<input type="hidden" name="command"
					value="go_to_available_run_courses_page" /> <input type="submit"
					value="${view_available_run_courses}" />
			</form>

		</c:if>
		
		
	</div>

	<c:if test="${!empty sessionScope.bean.endedCourses}"> 
		<div class="inscription"><c:out value="${ended_courses}"/></div>
		<br>
			<table>
				<tr>
					<th><c:out value="${title}"/></th>
					<th><c:out value="${date_of_start}"/></th>

					<th><c:out value="${date_of_end}"/></th>
					<th><c:out value="${course_status}"/></th>
					<th><c:out value="${schedule}"/></th>
					
					<th><c:out value="${classroom}"/></th>
					<c:if test="${sessionScope.bean.userRoleId > 1}">
					<th><c:out value="${amount_of_students_on_course}"/></th>
					</c:if>

					<c:if test="${sessionScope.bean.userRoleId == 1}">
						<th><c:out value="${mark}"/></th>
					</c:if>
					<c:if test="${sessionScope.bean.userRoleId == 1}">
						<th><c:out value="${my_status}"/></th>
					</c:if>

					<th><c:out value="${more}"/></th>
				</tr>
				<c:forEach var="course" items="${sessionScope.bean.endedCourses}">
					<tr>
						<td>
							<c:out value="${course.courseName}" />
						
						</td>
						<td><c:out value="${course.dateOfStart}" /></td>
						<td><c:out value="${course.dateOfEnd}" /></td>
						<td><c:choose>
				<c:when test="${course.currentState == 1}"><c:out value="${course_canselled}"/> </c:when>
				<c:when test="${course.currentState== 2}"><c:out value="${course_ended}"/> </c:when>
				<c:when test="${course.currentState== 3}"><c:out value="${course_recruting}"/> </c:when>
				<c:when test="${course.currentState== 4}"><c:out value="${course_running}"/> </c:when>
				
			</c:choose></td>

						<td><c:out value="${course.shedule}" /></td>
						
						<td><c:out value="${course.classroomNumber}" /></td>
						<c:if test="${sessionScope.bean.userRoleId > 1}">
							<td><c:out value="${course.studentAmount}" /></td>
						</c:if>

						<c:if test="${sessionScope.bean.userRoleId == 1}">
							<td><c:out
									value="${course.studentResult}" /></td>
						</c:if>
						<c:if test="${sessionScope.bean.userRoleId == 1}">
							<td><c:out value="${course.studentStatusName}" /></td>
						</c:if>
						<td>

							<form action="Controller" method="post">
								<input type="hidden" name="command"
									value="go_to_run_course_page" /> 
									<input type="hidden" name="run_course_id"
									value="${course.runCourseId}" /><input type="submit"
									value="${more_about_course}" />
							</form>

						</td>

					</tr>

				</c:forEach>

			</table>

		</c:if>



	<jsp:include page="footer.jsp" />

</body>
</html>