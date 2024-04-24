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
                    <c:forEach var="category" items="${categories}">
                        <c:url value="/servicios" var="categoryChange">
                            <c:if test="${not empty param.ubicacion}"><c:param name="ubicacion" value="${param.ubicacion}" /></c:if>
                            <c:if test="${not empty param.query}"><c:param name="query" value="${param.query}" /></c:if>
                            <c:if test="${not empty param.page}"><c:param name="page" value="${param.page}" /></c:if>
                            <c:param name="categoria" value="${category.value}"/>
                        </c:url>
                        <a href="${categoryChange}">${category.value}</a>
                    </c:forEach>
                </div>
            </div>

            <div class="dropdown">
                <p class="nav-item">Mi cuenta</p>
                <div class="dropdown-content">
                    <a href="${pageContext.request.contextPath}/perfil">Mi perfil</a>
                    <a href="${pageContext.request.contextPath}/turnos">Mis turnos</a>
                    <a href="${pageContext.request.contextPath}/negocios">Mis negocios</a>
                </div>
            </div>
            <a class="nav-item" href="${pageContext.request.contextPath}/registrar-negocio">
                <button class="btn navbar-btn"><label class="btn-text">Publicar</label></button>
            </a>

        </div>
    </div>
</body>
</html>


