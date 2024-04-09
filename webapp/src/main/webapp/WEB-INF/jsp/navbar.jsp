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
                    <!--Esto claramente habria que cambiarlo por un for each que itere sobre las categorias del enum-->
                    <!--A preguntar en clase, nose como hacerlo ya que navbar no se llama directamente desde el HelloWorldController -->
                    <a href="${pageContext.request.contextPath}/servicios/?categoria=Arreglos calificados">Arreglos calificados</a>
                    <a href="${pageContext.request.contextPath}/servicios/?categoria=Belleza">Belleza</a>
                    <a href="${pageContext.request.contextPath}/servicios/?categoria=Consultoria">Consultoria</a>
                    <a href="${pageContext.request.contextPath}/servicios/?categoria=Eventos y Celebraciones">Eventos y Celebraciones</a>
                    <a href="${pageContext.request.contextPath}/servicios/?categoria=Exteriores">Exteriores</a>
                    <a href="${pageContext.request.contextPath}/servicios/?categoria=Limpieza">Limpieza</a>
                    <a href="${pageContext.request.contextPath}/servicios/?categoria=Mascotas">Mascotas</a>
                    <a href="${pageContext.request.contextPath}/servicios/?categoria=Salud">Salud</a>
                    <a href="${pageContext.request.contextPath}/servicios/?categoria=Transporte">Transporte</a>
                </div>
            </div>
            <a class="nav-item" href="${pageContext.request.contextPath}/publicar">Publicar</a>
            <!--
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


