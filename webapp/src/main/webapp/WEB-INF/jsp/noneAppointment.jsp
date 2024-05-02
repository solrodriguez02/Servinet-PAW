<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:include page="navbar.jsp"/>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/noneAppointment.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <title><spring:message code="title.appointment-not-found"/></title>
</head>
<body>
<div class="page">
    <c:choose>
        <c:when test="${argument == 'cancelado'}">
            <h2 class="header-text"><spring:message code="appointment.canceled"/></h2>
            <div class="align-center">
                <a class="none-decoration" href="${pageContext.request.contextPath}/contratar-servicio/${serviceId}">
                    <label class="light-btn"><spring:message code="appointment.new"/></label>
                </a>
                <a class="none-decoration" href="${pageContext.request.contextPath}/">
                    <button class="btn">
                        <label class="btn-text"><spring:message code="appointment.back-to-home"/></label>
                    </button>
                </a>
            </div>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${argument == 'rechazado'}">
                    <h2 class="header-text"><spring:message code="appointment.rejected"/></h2>
                </c:when>
                <c:when test="${argument == 'noexiste'}">
                    <h2 class="header-text"><spring:message code="appointment.canceled-no-service"/></h2>
                </c:when>
            </c:choose>
            <div class="align-center">
                <a class="none-decoration" href="${pageContext.request.contextPath}/">
                    <button class="btn">
                        <label class="btn-text"><spring:message code="appointment.back-to-home"/></label>
                    </button>
                </a>
            </div>
        </c:otherwise>
    </c:choose>

</div>
</body>
</html>


