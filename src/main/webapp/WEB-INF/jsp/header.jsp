<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.welcomepage" var="loc"
	scope="session" />
	<fmt:setBundle basename="localization.localization" var="loc2"
	scope="session" />
		<fmt:message bundle="${loc}" key="button.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="button.name.en" var="en_button" />
<fmt:message bundle="${loc}" key="button.name.by" var="by_button" />
<fmt:message bundle="${loc2}" key="header.university_name" var="university_name" />
</head>
<body>
<div style=" overflow: hidden;
  background-color: #f1f1f1;
  position:relative;
   padding: 20px;
  text-align: center;
  background: #1abc9c;
  color: white;
  font-size: 30px;">

<c:out value="${university_name}"/>

<form style="display:inline;float:left;" action="Controller" method="post">
		<input type="hidden" name="command" value="change_language" /> <input
			type="hidden" name="local" value="ru" /> <input type="hidden"
			name="uri" value="${adress}" /> <input type="submit"
			value="${ru_button}" />
	</form>
	<form style="display:inline;float:left;" action="Controller" method="post">
		<input type="hidden" name="command" value="change_language" /> <input
			type="hidden" name="local" value="en" /> <input type="hidden"
			name="uri" value="${adress}" /><input type="submit"
			value="${en_button}" />
	</form>
	<form style="display:inline;float:left;" action="Controller" method="post">
		<input type="hidden" name="command" value="change_language" /> <input
			type="hidden" name="local" value="be" /> <input type="hidden"
			name="uri" value="${adress}" /><input type="submit"
			value="${by_button}" />
	</form>
	

	

</div>
</body>
</html>