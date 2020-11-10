<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>

<style>
<%@ include file="/css/footer.css"%>
</style>

<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.footer.footer" var="loc_footer"
	scope="session" />

<fmt:message bundle="${loc_footer}" key="belmagun"	var="belmagun" />
<fmt:message bundle="${loc_footer}" key="to_main_page"	var="to_main_page" />
<fmt:message bundle="${loc_footer}" key="about_us"	var="about_us" />
<fmt:message bundle="${loc_footer}" key="departments"	var="departments" />
<fmt:message bundle="${loc_footer}" key="courses"	var="courses" />
<fmt:message bundle="${loc_footer}" key="contacts"	var="contacts" />
<fmt:message bundle="${loc_footer}" key="news"	var="news" />
<fmt:message bundle="${loc_footer}" key="company_name"	var="company_name" />
<fmt:message bundle="${loc_footer}" key="company_text"	var="company_text" />
<fmt:message bundle="${loc_footer}" key="adress_country"	var="adress_country" />
<fmt:message bundle="${loc_footer}" key="adress_town"	var="adress_town" />
<fmt:message bundle="${loc_footer}" key="adress_street"	var="adress_street" />
<fmt:message bundle="${loc_footer}" key="adress_building"	var="adress_building" />
<fmt:message bundle="${loc_footer}" key="about"	var="about" />
<fmt:message bundle="${loc_footer}" key="text"	var="text" />


</head>
<body>

<footer class="footer-distributed">

			<div class="footer-left">

				<h3><c:out value="${belmagun}"/><span><img alt="" src="image/logo.png" width="50"></span></h3>

				<p class="footer-links">
					<a href="Controller?command=go_to_welcome_page" ><c:out value="${to_main_page}"/></a>
					
					<a href="Controller?command=go_to_about_university_page"><c:out value="${about_us}"/></a>
				
					<a href="Controller?command=go_to_departments_page"><c:out value="${departments}"/></a>
				
					<a href="Controller?command=go_to_available_run_courses_page"><c:out value="${courses}"/></a>
					
					<a href="Controller?command=go_to_contacts_page"><c:out value="${contacts}"/></a>
					
					<a href="Controller?command=go_to_news_page"><c:out value="${news}"/></a>
				</p>

			</div>

			<div class="footer-center">

				<div>
					<i class="fa fa-map-marker"></i>
					<p><span><c:out value="${adress_country}"/> <br> 
					<c:out value="${adress_town}"/> 
					<c:out value="${adress_street}"/> 
					<c:out value="${adress_building}"/> </p></span> 
				</div>

				<div>
					<i class="fa fa-phone"></i>
					<p>+375 (29) 314 15 92</p>
				</div>

				<div>
					<i class="fa fa-envelope"></i>
					<p><a href="mailto:support@company.com">belmagun@belmagun.com</a></p>
				</div>

			</div>

			<div class="footer-right">

				<p class="footer-company-about">
					<span><c:out value="${about}"/></span>
					<c:out value="${text}"/>
					
				</p>

				<div class="footer-icons">

					<a href="#"><i class="fa fa-facebook"></i></a>
					<a href="#"><i class="fa fa-twitter"></i></a>
					<a href="#"><i class="fa fa-linkedin"></i></a>
					<a href="#"><i class="fa fa-github"></i></a>

				</div>

			</div>
				<p class="footer-company-name"><c:out value="${company_name}"/> - <c:out value="${company_text}"/> © 2020</p>

		</footer>


</body>
</html>