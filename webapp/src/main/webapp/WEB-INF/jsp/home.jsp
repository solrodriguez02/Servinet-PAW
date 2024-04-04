<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="navbar.jsp" />
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/home.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <title></title>
</head>
<body>
<div class="page">
<c:choose>
    <c:when test="${isServicesEmpty}">
        <div class="not-found-page">
            <p class="not-found-text"><i class="material-icons">search_off</i> No se encontraron resultados de busqueda, lo sentimos.</p>
        </div>
    </c:when>
    <c:otherwise>
        <div class="services-container">
            <c:forEach items="${services}" var="item">
            <div class="service-box">
                <a class="service-text" href="${pageContext.request.contextPath}/${item.id}">
                    <div class="service-data-container">
                        <div class="service-img-container">
                            <c:choose>
                                <c:when test="${item.imageurl == null}">
                                    <img class="img service-img" src="https://goldbricksgroup.com/wp-content/uploads/2021/08/y9DpT-600x390.jpg" alt="Imagen del servicio">
                                </c:when>
                                <c:otherwise>
                                    <img class="img service-img" src="${item.imageurl}" alt="Imagen del servicio">
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="service-info">
                            <div class="service-header">
                                <h3> <c:out value="${item.name}"/></h3>
                                <p class="align-right">$ <c:out value="${item.price}"/> </p>
                            </div>
                            <p class="item"> <i class="material-icons">location_on</i> <c:out value="${item.location}"/> </p>
                            <p class="item"> <c:out value="${item.description}"/></p>
                        </div>
                    </div>
                </a>
            </div>
            </c:forEach>
        </div>

        <div class="page-manage-box">
            <c:choose>
                <c:when test="${page != 0}">
                    <c:choose>
                        <c:when test="${category!=null}">
                            <a class="page-text" href="${pageContext.request.contextPath}/?pagina=${page-1}&categoria=${category}">< Anterior</a>
                        </c:when>
                        <c:otherwise>
                            <a class="page-text" href="${pageContext.request.contextPath}/?pagina=${page-1}">< Anterior</a>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <label class="none-page-text">Anterior</label>
                </c:otherwise>
            </c:choose>

            <c:url value="/" var="getPage"/>
            <form action="${getPage}" method="get">
                <input class="page-input" type="text" id="inputPagina" name="pagina" value="${page}"/>
            </form>

            <c:choose>
                <c:when test="${isMoreServices}">
                    <c:choose>
                        <c:when test="${category!=null}">
                            <a class="page-text" href="${pageContext.request.contextPath}/?pagina=${page+1}&categoria=${category}">Siguiente ></a>
                        </c:when>
                        <c:otherwise>
                            <a class="page-text" href="${pageContext.request.contextPath}/?pagina=${page+1}">Siguiente ></a>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <label class="none-page-text">Siguiente</label>
                </c:otherwise>
            </c:choose>
        </div>
    </c:otherwise>
</c:choose>
</div>
</body>
</html>
