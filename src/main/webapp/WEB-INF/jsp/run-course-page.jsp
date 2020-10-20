<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<fmt:message bundle="${loc}" key="student_list" var="student_list" />
<fmt:message bundle="${loc}" key="student_name" var="student_name" />
<fmt:message bundle="${loc}" key="status_on_course" var="status_on_course" />
<fmt:message bundle="${loc}" key="mark" var="mark" />
<fmt:message bundle="${loc}" key="more" var="more" />
<fmt:message bundle="${loc}" key="approve" var="approve" />
<fmt:message bundle="${loc}" key="disapprove" var="disapprove" />
<fmt:message bundle="${loc}" key="drop_out" var="drop_out" />
<fmt:message bundle="${loc}" key="no_places" var="no_places" />
<fmt:message bundle="${loc}" key="no_vacant_places"
	var="no_vacant_places" />


<c:set var="commandToLanguageChanger" scope="session"
	value="go_to_run_course_page" />

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
			<td><c:out value="${run_course.dateOfStart}" /></td>
		</tr>
		<tr>
			<td><c:out value="${end_date}" /></td>
			<td><c:out value="${run_course.dateOfEnd}" /></td>
		</tr>
		<tr>
			<td><c:out value="${course_status}" /></td>
			<td><c:out value="${run_course.courseStatusName}" /></td>
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
			<td><c:out value="${run_course.lecturerName}" />ФОТО<img alt=""
				src=""></td>
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
		
		<c:when test="${sessionScope.bean.userRoleId == 1}">
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
					<c:out value="${successfully_enrolled}"/>
					ТУТ ФОРМА ОТЗЫВА, ЕСЛИ КУРС ОКОНЧЕН
				</c:when>
				<c:when test="${requestScope.user_approval_status_id == 3}">
					<div class="inscription"><c:out value="${dropped}"/></div>
				</c:when>

			</c:choose>

		</c:when>
		
		<c:when test="${sessionScope.bean.userRoleId > 1}">
			<div class="inscription"><c:out value="${student_list}"/></div>
			
			<table class="courses_table">
			<tr>
				<th><c:out value="${student_name}"/></th>
				<th>Фотка</th>
				<th><c:out value="${mark}"/></th>
				<th><c:out value="${status_on_course}"/></th>
				<th><c:out value="${more}"/></th>
			</tr>
			<c:forEach var="student" items="${requestScope.run_course.studentsOnCourse}">
				<tr>
					<td><c:out value="${student.userFirstName}"/> 
					<c:if test="${student.userPatronymic != null}">
					<c:out value="${student.userPatronymic}"/> 
					</c:if> 
					<c:out value="${student.userSecondName}"/></td>
					
					<td>ФОТКА</td>
					<td><c:choose>
							<c:when test="${student.result == 0}">
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
							<c:when test="${student.userApprovalStatusId == 2}">
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
		
		</c:when>
		
		<c:otherwise>
			<div class="inscription">Для записи на курсы необходимо зарегистрироваться</div>
		</c:otherwise>


	</c:choose>


	<jsp:include page="footer.jsp" />

</body>
</html>