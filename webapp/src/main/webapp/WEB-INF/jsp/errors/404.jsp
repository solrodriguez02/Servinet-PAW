<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="../navbar.jsp" />
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/noneAppointment.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <title>Page not found</title>
</head>
<body>
<div class="page">

    <img class="img svg" src="${pageContext.request.contextPath}/resources/404.svg" alt="">
    <h2 class="header-text">Esta pagina no existe.</h2>

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
