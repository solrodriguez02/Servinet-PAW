<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:include page="navbar.jsp" />
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/noneAppointment.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <title><spring:message code="title.invalid"/></title>
</head>
<body>
<c:set var="textCallToAction"><spring:message code="invalid.back-to-home"/></c:set>
<div class="page">
    <c:choose>
        <c:when test="${argument == 'servicionoexiste'}">
            <h2 class="header-text"><spring:message code="invalid.service"/></h2>
        </c:when>
        <c:when test="${argument == 'turnonoexiste'}">
            <h2 class="header-text"><spring:message code="invalid.appointment"/></h2>
        </c:when>
        <c:when test="${argument == 'turnoyaconfirmado'}">
            <h2 class="header-text"><spring:message code="invalid.appointment-confirmed"/>.</h2>
        </c:when>
        <c:when test="${argument == 'operacionprohibida'}">
            <h2 class="header-text"><spring:message code="invalid.access"/></h2>
        </c:when>
        <c:when test="${argument == 'negocionoexiste'}">
            <h2 class="header-text"><spring:message code="invalid.business"/></h2>
        </c:when>


    </c:choose>
    <div class="align-center">
        <a class="none-decoration" href="${pageContext.request.contextPath}/">
            <button class="btn">
                <label class="btn-text">${textCallToAction}</label>
            </button>
        </a>
    </div>
</div>
</body>
</html>
