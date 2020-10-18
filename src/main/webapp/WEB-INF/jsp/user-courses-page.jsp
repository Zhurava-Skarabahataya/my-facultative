<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Мои Курсы</title>


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
				<input type="hidden" name="command" value="go_to_user_rating_page" />
				<input type="submit" value="${my_rating}" /><br />
			</form>


		</c:if>
		<c:if test="${sessionScope.bean.userRoleId == 2}">

			<form style="display: inline; float: center" action="Controller"
				method="post">
				<input type="hidden" name="command" value="go_to_create_course_page" />
				<input type="submit" value="${create_course}" /><br />
			</form>

		</c:if>
	</div>

	<div class="courses_info_table">
	<c:if test="${sessionScope.bean.courses != null}"> 
	МОИ КУРСЫ:
		<table>
			<tr>
				<th>
					Название
				</th>
				<th>
					Дата старта
				</th>
					
				<th>
					Дата окончания
				</th>
				<th>
				Статус курса
				</th>
				<th>
				Длительность курса в часах
				</th>
				<th>
				Расписание
				</th>
				<th>
					Аудитория
				</th>
				<th>
				Количество студентов на курсе
				</th>
				<th>
				Максимальное количество студентов
					
				</th>
				<c:if test="sessionScope.bean.userRoleId == 1">
				<th>
				Преподаватель
				</th>
			</c:if>
				
				<th>Прочее
				</th>
			</tr>
		<c:forEach var="course" items="${sessionScope.bean.courses}"> 
			<tr>
				<td>
					<form action="Controller" method="post">
					<input type="hidden" name="command" value="go_to_course_page" />
					<c:out value="${course.courseName}"/>
					</form>
				
				</td>
				<td>
				<c:out value="${course.dateOfStart}"/>
				
				</td>
				<td>
				<c:out value="${course.dateOfEnd}"/>
				
				</td>
				<td>
								<c:out value="${course.courseStatusName}"/>
				</td>
				<td>
								<c:out value="${course.infoAboutCourse.courseDuration}"/>
				</td>
				<td>
								<c:out value="${course.shedule}"/>
				</td>
				<td>
								<c:out value="${course.classroomNumber}"/>
				</td>
				<td>
								<c:out value="${course.studentAmount}"/>
				</td>
				<td>
								<c:out value="${course.studentLimit}"/>
				</td>
				<c:if test="sessionScope.bean.userRoleId == 1">
				<td>
												<c:out value="${course.lecturerName}"/>
				
				</td>
				</c:if>
				<td>
								
				<form action="Controller" method="post">
					<input type="hidden" name="command" value="go_to_run_course_page" />
					<input type="submit" name="Подробности о курсе" />
					</form>
				
				</td>
				
			</tr>
		
		</c:forEach>
		
		</table>
	</c:if>
	<br>
	<div style="padding:15px">
		<c:if test="${sessionScope.bean.courses == null}"> 
	Пока нету курсов...<br><br>
	
		</c:if>
	
			<c:if test="${sessionScope.bean.userRoleId == 1}"> 
			
				<form action="Controller" method="post">
					<input type="hidden" name="command" value="go_to_available_run_courses_page" />
					<input type="submit" value="Просмотреть доступные курсы" />
					</form>
			
			</c:if>
	</div>
	
	</div>

	

	<jsp:include page="footer.jsp" />

</body>
</html>