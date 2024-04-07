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

<c:choose>
    <c:when test="${category==null}">
        <c:set var="categoryPath" value="${pageContext.request.contextPath}/?"/>
        <c:choose>
            <c:when test="${location==null}">
                <c:set var="filtersPath" value="${pageContext.request.contextPath}/?" />
            </c:when>
            <c:otherwise>
                <c:set var="filtersPath" value="${pageContext.request.contextPath}/?ubicacion=${location}&" />
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <c:set var="categoryPath" value="${pageContext.request.contextPath}/?categoria=${category}&"/>
        <c:choose>
            <c:when test="${location==null}">
                <c:set var="filtersPath" value="${pageContext.request.contextPath}/?categoria=${category}&" />
            </c:when>
            <c:otherwise>
                <c:set var="filtersPath" value="${pageContext.request.contextPath}/?categoria=${category}&ubicacion=${location}&" />
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>

<div class="page">
<c:choose>
    <c:when test="${isServicesEmpty}">
        <div class="not-found-page">
            <p class="not-found-text"><i class="material-icons">search_off</i> No se encontraron resultados de busqueda, lo sentimos.</p>
        </div>
    </c:when>
    <c:otherwise>
        <div class="filters-info">
            <div class="header">
                <c:if test="${category!=null}">
                    <div class="category-header">
                        <h3>Categoria: <c:out value="${category}"/></h3>
                    </div>
                </c:if>
                <div class="align-right">
                    <div class="dropdown">
                        <p class="filters-text"><i class="material-icons">filter_alt</i>Filtrar por ubicacion</p>
                        <div class="dropdown-content">
                            <c:forEach items="${neighbourhoods}" var="neighbourhood">
                                <a href="${categoryPath}ubicacion=${neighbourhood.value}"><c:out value="${neighbourhood.value}"/></a>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>

            <div class="filters-selected">
                <c:if test="${location != null}">
                    <h5>Filtros seleccionados:</h5>
                    <button class="filter-container">
                        <c:out value="${location}"/>
                        <a href="${categoryPath}"><i class="material-icons close-filter-icon">close</i></a>
                    </button>
                </c:if>
            </div>
        </div>

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
                    <a class="page-text" href="${filtersPath}pagina=${page-1}">< Anterior</a>
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
                    <a class="page-text" href="${filtersPath}pagina=${page+1}">Siguiente ></a>
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
