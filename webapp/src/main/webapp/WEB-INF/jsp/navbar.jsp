<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value='/resources/logo.png'/>">
    <link href="${pageContext.request.contextPath}/css/navbar.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap">
</head>
<body>
    <div class="nav-bar page">
        <a href="${pageContext.request.contextPath}/">
            <div class="logo-img-container">
                <img class="img" src="${pageContext.request.contextPath}/resources/servinet.png" alt="<spring:message code="servinet.logo"/>">
            </div>
        </a>
        <div class="nav-items">
            <div class="dropdown">
                <p class="nav-item"><spring:message code="navbar.categories"/></p>
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
                <p class="nav-item"><spring:message code="navbar.account"/></p>
                <div class="dropdown-content">
                    <a href="${pageContext.request.contextPath}/perfil"><spring:message code="navbar.profile"/></a>
                    <a href="${pageContext.request.contextPath}/turnos/?confirmados=true"><spring:message code="navbar.appointments"/></a>
                    <a href="${pageContext.request.contextPath}/negocios"><spring:message code="navbar.businesses"/></a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>


