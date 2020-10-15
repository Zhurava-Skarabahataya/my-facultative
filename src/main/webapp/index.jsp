<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<c:set var="userLogin" scope="session" value="${null}" />
<c:redirect url="Controller?command=go_to_welcome_page"/>
