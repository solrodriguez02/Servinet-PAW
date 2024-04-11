<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/navbar.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap">
    <title></title>
</head>
<body>
    <div class="nav-bar page">
        <a href="${pageContext.request.contextPath}/">
            <div class="logo-img-container">
                <img class="img" src="${pageContext.request.contextPath}/resources/logo.png" alt="Logo Servinet">
            </div>
        </a>
        <div class="nav-items">
            <div class="dropdown">
                <p class="nav-item">Categorias</p>
                <div class="dropdown-content">
                    <c:forEach var="categoria" items="${categories}">
                        <a href="${pageContext.request.contextPath}/servicios/?categoria=${categoria.value}">${categoria.value}</a>
                    </c:forEach>
                </div>
            </div>
            <a class="nav-item" href="${pageContext.request.contextPath}/registrar-datos-personales">Publicar</a>
            <!--
            <a class="nav-item" href="${pageContext.request.contextPath}/registrar-datos-personales">Publicar</a>
            <div class="dropdown">
                <p class="nav-item">Mi cuenta</p>
                <div class="dropdown-content">
                    <a href="${pageContext.request.contextPath}/micuenta">Mi perfil</a>
                    <a href="${pageContext.request.contextPath}/misturnos">Mis turnos</a>
                    <a href="${pageContext.request.contextPath}/misservicios">Mis servicios</a>
                </div>
            </div>
            -->
        </div>
    </div>
</body>
</html>


