<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:include page="../navbar.jsp" />
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/noneAppointment.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <title><spring:message code="error.pagenotfound"/></title>
</head>
<body>
<div class="page">

    <img class="img svg" src="${pageContext.request.contextPath}/resources/404.svg" alt="">
    <h2 class="header-text"><spring:message code="error.pagenotfound"/></h2>

    <div class="align-center">
        <a class="none-decoration" href="${pageContext.request.contextPath}/">
            <button class="btn">
                <label class="btn-text"><spring:message code="error.backtohome"/></label>
            </button>
        </a>
    </div>
</div>
</body>
</html>
