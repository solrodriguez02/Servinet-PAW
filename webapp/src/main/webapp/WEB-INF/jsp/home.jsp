<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="navbar.jsp" />
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/home.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <title></title>
</head>
<body>
<div class="page">
    <div class="header">
        <h2 class="slogan">Slogan Servinet</h2>
    </div>
    <h3>Busca por categorias</h3>
    <div class="boxes-container">
        <c:forEach items="${categories}" var="category" varStatus="loop">
            <c:if test="${loop.index <= 5}">
                <a class="none-decoration" href="${pageContext.request.contextPath}/servicios/?categoria=${category.value}">
                    <div class="box">
                        <h4 class="box-text"><c:out value="${category.value}"/></h4>
                    </div>
                </a>
            </c:if>
        </c:forEach>
    </div>


    <h3 class="container-text">Busca por cercania</h3>
    <div class="boxes-container">
        <c:forEach items="${neighbourhoods}" var="neighbourhood" varStatus="loop">
            <c:if test="${loop.index <= 5}">
                <a class="none-decoration" href="${pageContext.request.contextPath}/servicios/?ubicacion=${neighbourhood.value}">
                    <div class="box">
                        <h4 class="box-text"><c:out value="${neighbourhood.value}"/></h4>
                    </div>
                </a>
            </c:if>
        </c:forEach>
    </div>

    <div class="btn-container">
        <button class="btn">
            <a href="${pageContext.request.contextPath}/servicios" class="none-decoration btn-text">Ver todos los servicios</a>
        </button>
    </div>
</div>
</body>
</html>


