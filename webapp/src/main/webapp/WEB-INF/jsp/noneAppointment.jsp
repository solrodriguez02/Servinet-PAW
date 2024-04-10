<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="navbar.jsp" />
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/noneAppointment.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <title></title>
</head>
<body>
<div class="page">
    <c:choose>
        <c:when test="${argument == 'cancelado'}">
            <h2 class="header-text">Su turno ha sido cancelado.</h2>
            <div class="align-center">
                <a class="none-decoration" href="${pageContext.request.contextPath}/contratar-servicio/${serviceId}">
                    <label class="light-btn">Sacar nuevo turno</label>
                </a>
                <a class="none-decoration" href="${pageContext.request.contextPath}/">
                    <button class="btn">
                        <label class="btn-text">Volver al inicio</label>
                    </button>
                </a>
            </div>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${argument == 'rechazado'}">
                    <h2 class="header-text">El turno ha sido rechazado</h2>
                </c:when>
                <c:when test="${argument == 'noexiste'}">
                    <h2 class="header-text">Este servicio ya no existe, por lo que su turno fue cancelado, lo sentimos.</h2>
                </c:when>
            </c:choose>
            <div class="align-center">
                <a class="none-decoration" href="${pageContext.request.contextPath}/">
                    <button class="btn">
                        <label class="btn-text">Volver al inicio</label>
                    </button>
                </a>
            </div>
        </c:otherwise>
    </c:choose>

</div>
</body>
</html>


