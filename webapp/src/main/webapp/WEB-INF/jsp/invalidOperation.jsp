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
        <c:when test="${argument == 'servicionoexiste'}">
            <h2 class="header-text">Este servicio ya no existe, lo sentimos.</h2>
        </c:when>
        <c:when test="${argument == 'turnonoexiste'}">
            <h2 class="header-text">Este turno ya no existe, lo sentimos.</h2>
        </c:when>
        <c:when test="${argument == 'turnoyaconfirmado'}">
            <h2 class="header-text">Esta operacion ya no es valida pues el turno ya se ha confirmado anteriormente.</h2>
        </c:when>

    </c:choose>
    <div class="align-center">
        <a class="none-decoration" href="${pageContext.request.contextPath}/">
            <button class="btn">
                <label class="btn-text">Volver al inicio</label>
            </button>
        </a>
    </div>
</div>
</body>
</html>
