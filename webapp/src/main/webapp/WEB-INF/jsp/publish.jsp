<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="navbar.jsp"/>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/publish.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <title>Business</title>
</head>
<body>
<c:url value="/registrar-negocio" var="registerBusiness"/>
<c:url value="/crear-servicio" var="createService"/>
<div class="page">
    <div class="align-center btns-container">
        <a class="nav-item" href="${registerBusiness}">
            <button class="btn business-btn"><label class="btn-text business-btn-text"><i class="material-icons business-icon">storefront</i>Crear negocio</label></button>
        </a>
        <a class="nav-item" href="${createService}">
            <button class="btn"><label class="btn-text service-btn-text">Agregar servicio<i class="material-icons add-icon">add</i></label></button>
        </a>
    </div>
</div>
</body>
</html>


