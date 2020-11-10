<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri = "/myTag" prefix = "my" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:out value="${title}" /></title>

<style>
<%@ include file="/css/style.css"%>
</style>

<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.run_course_page.run_course_page"
	var="loc" scope="session" />


<fmt:message bundle="${loc}" key="title" var="title" />
<fmt:message bundle="${loc}" key="course_name" var="course_name" />
<fmt:message bundle="${loc}" key="department_name" var="department_name" />
<fmt:message bundle="${loc}" key="start_date" var="start_date" />
<fmt:message bundle="${loc}" key="end_date" var="end_date" />
<fmt:message bundle="${loc}" key="description" var="description" />
<fmt:message bundle="${loc}" key="course_program" var="course_program" />
<fmt:message bundle="${loc}" key="requirements" var="requirements" />
<fmt:message bundle="${loc}" key="duration" var="duration" />
<fmt:message bundle="${loc}" key="shcedule" var="shcedule" />
<fmt:message bundle="${loc}" key="lecturer" var="lecturer" />
<fmt:message bundle="${loc}" key="classroom" var="classroom" />
<fmt:message bundle="${loc}" key="students_limit" var="students_limit" />
<fmt:message bundle="${loc}" key="available_places"
	var="available_places" />
<fmt:message bundle="${loc}" key="apply" var="apply" />
<fmt:message bundle="${loc}" key="remove_application" var="remove_application" />
<fmt:message bundle="${loc}" key="course_status" var="course_status" />
<fmt:message bundle="${loc}" key="successfully_enrolled" var="successfully_enrolled" />
<fmt:message bundle="${loc}" key="dropped" var="dropped" />
<fmt:message bundle="${loc}" key="no_mark" var="no_mark" />
<fmt:message bundle="${loc}" key="leave_feedback" var="leave_feedback" />
<fmt:message bundle="${loc}" key="my_grade" var="my_grade" />
<fmt:message bundle="${loc}" key="student_list" var="student_list" />
<fmt:message bundle="${loc}" key="student_name" var="student_name" />
<fmt:message bundle="${loc}" key="status_on_course" var="status_on_course" />
<fmt:message bundle="${loc}" key="mark" var="mark" />
<fmt:message bundle="${loc}" key="more" var="more" />
<fmt:message bundle="${loc}" key="approve" var="approve" />
<fmt:message bundle="${loc}" key="disapprove" var="disapprove" />
<fmt:message bundle="${loc}" key="drop_out" var="drop_out" />
<fmt:message bundle="${loc}" key="no_places" var="no_places" />
<fmt:message bundle="${loc}" key="course_ended" var="course_ended" />
<fmt:message bundle="${loc}" key="course_cancelled" var="course_cancelled" />
<fmt:message bundle="${loc}" key="course_recruting" var="course_recruting" />
<fmt:message bundle="${loc}" key="course_running" var="course_running" />
<fmt:message bundle="${loc}" key="no_students_on_course" var="no_students_on_course" />
<fmt:message bundle="${loc}" key="student_photo" var="student_photo" />
<fmt:message bundle="${loc}" key="view_student_page" var="view_student_page" />
<fmt:message bundle="${loc}" key="no_photo" var="no_photo" />
<fmt:message bundle="${loc}" key="give_the_grade" var="give_the_grade" />
<fmt:message bundle="${loc}" key="need_to_registrate" var="need_to_registrate" />
<fmt:message bundle="${loc}" key="send_feedback" var="send_feedback" />
<fmt:message bundle="${loc}" key="no_vacant_places"
	var="no_vacant_places" />

<c:set var="commandToLanguageChanger" scope="session"
	value="go_to_run_course_page&run_course_id=${requestScope.run_course.runCourseId}" />

</head>
<body>
	<jsp:include page="header.jsp" />
<div class="inscription"><c:out value="${requestScope.run_course.courseName}" /></div>

	<table class="courses_table">
		
		<tr>
			<td><c:out value="${department_name}" /></td>
			<td><c:out value="${run_course.infoAboutCourse.departmentName}" /></td>
		</tr>
		<tr>
			<td><c:out value="${start_date}" /></td>
			<td><my:formDate date="${run_course.dateOfStart}" /></td>
		</tr>
		<tr>
			<td><c:out value="${end_date}" /></td>
			<td><my:formDate date="${run_course.dateOfEnd}" /></td>
		</tr>
		<tr>
			<td><c:out value="${course_status}" /></td>
			<td>
			<c:choose>
				<c:when test="${run_course.currentState == 1}"><c:out value="${course_canselled}"/> </c:when>
				<c:when test="${run_course.currentState== 2}"><c:out value="${course_ended}"/> </c:when>
				<c:when test="${run_course.currentState== 3}"><c:out value="${course_recruting}"/> </c:when>
				<c:when test="${run_course.currentState== 4}"><c:out value="${course_running}"/> </c:when>
				
			</c:choose>
		
		</tr>
		<tr>
			<td><c:out value="${description}" /></td>
			<td><c:out
					value="${run_course.infoAboutCourse.courseDescription}" /></td>
		</tr>
		<tr>
			<td><c:out value="${course_program}" /></td>
			<td><c:out value="${run_course.infoAboutCourse.courseProgram}" /></td>
		</tr>
		<tr>
			<td><c:out value="${requirements}" /></td>
			<td><c:out
					value="${run_course.infoAboutCourse.courseRequirement}" /></td>
		</tr>
		<tr>
			<td><c:out value="${lecturer}" /></td>
			<td><c:out value="${run_course.lecturerName}" /><br><img src = "${run_course.lecturerPhotoLink}" width=200px alt=""></td>
		</tr>
		<tr>
			<td><c:out value="${duration}" /></td>
			<td><c:out value="${run_course.infoAboutCourse.courseDuration}" /></td>
		</tr>
		<tr>
			<td><c:out value="${shcedule}" /></td>
			<td><c:out value="${run_course.shedule}" /></td>
		</tr>
		<tr>
			<td><c:out value="${classroom}" /></td>
			<td><c:out value="${run_course.classroomNumber}" /></td>
		</tr>
		<tr>
			<td><c:out value="${students_limit}" /></td>
			<td><c:out value="${run_course.studentLimit}" /></td>
		</tr>
		<tr>
			<td><c:out value="${available_places}" /></td>
			<td><c:out
					value="${run_course.studentLimit - run_course.studentAmount}" /></td>
		</tr>
	</table>

	<c:choose>
		
		<c:when test="${(sessionScope.bean.userRoleId == 1) && (run_course.currentState >2)}">
			<c:choose>
				<c:when test="${requestScope.user_approval_status_id == 0}">
					<c:choose>
						<c:when
							test="${(run_course.studentLimit - run_course.studentAmount) > 0}">
							<form  action="Controller" method="post">
								<input type="hidden" name="command" value="apply_for_course" />
								<input type="hidden" name="run_course_id"
									value="${run_course.runCourseId}" /> <input type="submit"
									value="${apply}" /><br />
							</form>
						</c:when>
						<c:otherwise>
							<c:out value="${no_vacant_places}" />
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:when test="${requestScope.user_approval_status_id == 1}">
					<form  action="Controller" method="post">
								<input type="hidden" name="command" value="remove_application_for_course" />
								<input type="hidden" name="run_course_id"
									value="${run_course.runCourseId}" /> <input type="submit"
									value="${remove_application}" /><br />
							</form>
				</c:when>
				<c:when test="${requestScope.user_approval_status_id == 2}">
					<div class="inscription"><c:out value="${successfully_enrolled}"/></div>
				</c:when>
				<c:when test="${requestScope.user_approval_status_id == 3}">
					<div class="inscription"><c:out value="${dropped}"/></div>
				</c:when>

			</c:choose>

		</c:when>
		
		<c:when test="${(sessionScope.bean.userRoleId == 1) && (run_course.currentState == 2)}">
			<c:choose>
				<c:when test="${requestScope.user_approval_status_id == 2}"> 
							<div class="inscription"><c:out value="${my_grade}"/>
							<div style="font-size:40px;"><c:out value="${requestScope.user_mark}"/></div></div>
				
					<div class="inscription"><c:out value="${leave_feedback}"/></div>
					<form action="Controller" method="post">
						<input type="hidden" name="command" value="leave_feedback"/>
						<input type="hidden" name="courseId" value="${run_course.courseId}"/>
						 <textarea name="comment" cols="40" rows="3"></textarea><br>
						 <input type="submit" value="${send_feedback}" />
						 
					</form>
				 </c:when>
				<c:otherwise><div class="inscription"><c:out value="${course_ended}"/></div></c:otherwise>				
			</c:choose>
		
		
		</c:when>
		<c:when test="${(sessionScope.bean.userRoleId == 1) && (run_course.currentState == 1)}">
			
				<div class="inscription"><c:out value="${course_cancelled}"/></div>
		
		</c:when>
		
		<c:when test="${(sessionScope.bean.userRoleId == 2 && 
		requestScope.run_course.lecturerId == sessionScope.bean.userId) ||
		 (sessionScope.bean.userRoleId == 3 
		 && sessionScope.bean.userFacultyId == run_course.infoAboutCourse.courseDepartment) ||
		 sessionScope.bean.userRoleId == 4}">
			<div class="inscription"><c:out value="${student_list}"/></div>
			<c:if test="${empty requestScope.run_course.studentsOnCourse }">
			<div class="inscription"><c:out value="${no_students_on_course}"/></div></c:if>
			<c:if test="${!empty requestScope.run_course.studentsOnCourse }">
			<table class="courses_table">
			<tr>
				<th><c:out value="${student_name}"/></th>
				<th><c:out value="${student_photo}"/></th>
				<th><c:out value="${mark}"/></th>
				<th><c:out value="${status_on_course}"/></th>
				<th><c:out value="${more}"/></th>
			</tr>
			<c:forEach var="student" items="${requestScope.run_course.studentsOnCourse}">
				<tr>
					<td><c:out value="${student.userFirstName}"/> <br>
					<c:if test="${student.userPatronymic != null}">
					<c:out value="${student.userPatronymic}"/> 
					<br>
					
					</c:if> 
					<c:out value="${student.userSecondName}"/><br>
					<form action="Controller" method = "post">
						<input type="hidden" name="command" value="go_to_another_user_page" />
						<input type="hidden" name="userId" value="${student.userId}" />
						<input type="submit" value="${view_student_page}" />
					</form></td>
					
					<td><img src="${student.userPhotoLink}" width = 200px alt = "${no_photo}"/></td>
					<td><c:choose>
							<c:when test="${student.result != 0}">
							<c:out value="${student.result}"/>
							</c:when>
							<c:otherwise><c:out value="${no_mark}"/></c:otherwise>
					
					</c:choose></td>
					<td><c:out value="${student.userApprovalStatusName}"/></td>
					<td>
						<c:choose>
							<c:when test="${student.userApprovalStatusId == 1}">
								<c:choose>
									<c:when test="${(run_course.studentLimit - run_course.studentAmount) > 0}">
										<form action="Controller" method="post">
									<input type="hidden" name="command" value="approve_student_on_course" />
									<input type="hidden" name="runCourseId" value="${requestScope.run_course.runCourseId}"/>
									<input type="hidden" name="studentId" value="${student.userId}" />
									<input type="submit" value="${approve}" />
								</form>
									</c:when>
									<c:otherwise><c:out value="${no_places}"/></c:otherwise>
								</c:choose>
								
								<form action="Controller" method="post">
									<input type="hidden" name="command" value="disapprove_student_on_course" />
									<input type="hidden" name="runCourseId" value="${requestScope.run_course.runCourseId}"/>
									<input type="hidden" name="studentId" value="${student.userId}" />
									<input type="submit" value="${disapprove}" />
								</form>
							</c:when>
							<c:when test="${student.userApprovalStatusId == 2 && run_course.currentState== 2
							 && student.result == 0}">
								<form action="Controller" method="post">
									<input type="hidden" name="command" value="give_the_grade" />
									<input type="range" name = "grade" min="1" max = "10"/>
									<input type="hidden" name="runCourseId" value="${requestScope.run_course.runCourseId}"/>
									<input type="hidden" name="studentId" value="${student.userId}" />
									<input type="submit" value="${give_the_grade}" />
								</form>
							</c:when>
							<c:when test="${student.userApprovalStatusId == 2 && run_course.currentState>2}">
								<form action="Controller" method="post">
									<input type="hidden" name="command" value="drop_out_student_from_course" />
									<input type="hidden" name="runCourseId" value="${requestScope.run_course.runCourseId}"/>
									<input type="hidden" name="studentId" value="${student.userId}" />
									<input type="submit" value="${drop_out}" />
								</form>
							</c:when>
						
						</c:choose>
					</td>
				</tr>
			</c:forEach>
			</table>
			</c:if>
		</c:when>
		
		<c:when test="${sessionScope.bean == null}">
			<div class="inscription"><c:out value="${need_to_registrate}"></c:out></div>
		</c:when>

	</c:choose>

	<jsp:include page="footer.jsp" />

</body>
</html>