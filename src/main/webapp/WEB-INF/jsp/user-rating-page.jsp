<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>


<style>
<%@ include file="/css/style.css"%>
</style>


<c:set var="commandToLanguageChanger" scope="session"
	value="go_to_user_rating_page" />

<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.user_page.user_page" var="loc"
	scope="session" />
<fmt:message bundle="${loc}" key="log_out"	var="log_out" />
<fmt:message bundle="${loc}" key="rating"	var="rating" />
<fmt:message bundle="${loc}" key="no_rating_yet"	var="no_rating_yet" />
<fmt:message bundle="${loc}" key="completed_courses"	var="completed_courses" />
<fmt:message bundle="${loc}" key="no_courses_completed"	var="no_courses_completed" />
<fmt:message bundle="${loc}" key="my_page"	var="my_page" />
<fmt:message bundle="${loc}" key="my_courses"	var="my_courses" />
<fmt:message bundle="${loc}" key="my_rating"	var="my_rating" />
<fmt:message bundle="${loc}" key="create_course"	var="create_course" />
<fmt:message bundle="${loc}" key="go_to_staff_page"	var="go_to_staff_page" />
<fmt:message bundle="${loc}" key="go_to_students_page"	var="go_to_students_page" />
	
</head>
<body>
<jsp:include page="header.jsp" />

<div style="display: inline;" >
	
			<form style = "display:inline" action="Controller" method="post">
				<input type="hidden" name="command"
					value="go_to_user_page" /> <input type="submit"
					value="${my_page}" /><br />
			</form>
		
			<form style = "display:inline" action="Controller" method="post">
				<input type="hidden" name="command"
					value="go_to_user_courses_page" /> <input type="submit"
					value="${my_courses}" /><br />
			</form>
		
		<c:if test="${sessionScope.bean.userRoleId == 1}">
		
		<form style = "display:inline;  float:center" action="Controller" method="post">
				<input type="hidden" name="command"
					value="view_student_rating_page" /> 
						<input type="hidden" name="userLogin"	value="${bean.userLogin}" /> 
						<input type="submit"
					value="${my_rating}" /><br />
			</form>
		
		
		</c:if>
		<c:if test="${sessionScope.bean.userRoleId == 2}">
		
		<form style = "display:inline; float:center" action="Controller" method="post">
				<input type="hidden" name="command"
					value="go_to_create_run_course_page" /> <input type="submit"
					value="${create_course}" /><br />
			</form>
		
		</c:if>
		<c:if test="${sessionScope.bean.userRoleId >2 }">
		
		<form style = "display:inline; float:center" action="Controller" method="post">
				<input type="hidden" name="command"
					value="go_to_staff_page" /> <input type="submit"
					value="${go_to_staff_page}" /><br />
			</form>
			<form style = "display:inline; float:center" action="Controller" method="post">
				<input type="hidden" name="command"
					value="go_to_students_page" /> <input type="submit"
					value="${go_to_students_page}" /><br />
			</form>
		
		</c:if>
		
	
		<form style = "display:inline" action="Controller" method="post">
				<input type="hidden" name="command"
					value="logout" /> <input type="submit"
					value="${log_out}" /><br />
			</form>
		</div>
		
		<div class="inscription"><c:out value="${rating}"/></div>
		
			<c:choose>
				<c:when test="${requestScope.student.studentRating == null}">
				<div class="inscription"><c:out value="${no_rating_yet}"/> </div>
				</c:when>
				<c:otherwise>
				<div class="inscription" style="width:40%;  font-size: 40px;">
				<c:out value="${requestScope.student.studentRating}"/>
				</div>
				</c:otherwise>
			</c:choose>
		
	
			
		<br>
		
				<div class="inscription"><c:out value="${completed_courses}"/></div>
				<c:if test="${empty requestScope.student.studentMarks}">
								<div class="inscription"><c:out value="${no_courses_completed}"/></div>
				</c:if>
				<c:if test="${!empty requestScope.student.studentMarks}">
					<table>
					<tr>
						<th>Название курса</th>
						<th>Оценка</th>
						<th>Подробнее</th>
					</tr>
						<c:forEach var="course" items="${requestScope.student.studentMarks}">
							<tr>
								<td><c:out value="${course.courseTitle}"/></td>
								<td><c:out value="${course.markGrade}"/></td>
								<td>
									<form action="Controller" method="post">
										<input type="hidden" name="command"	value="go_to_run_course_page" /> 
										<input type="hidden" name="run_course_id" value="${course.runCourseId}" /> 
										<input type="submit" value="Подробнее о курсе" /><br />
									</form>
								
								</td>
							</tr>
						</c:forEach>
						
						</table>	
				</c:if>
		
		
		<jsp:include page="footer.jsp" />
		


</body>
</html>