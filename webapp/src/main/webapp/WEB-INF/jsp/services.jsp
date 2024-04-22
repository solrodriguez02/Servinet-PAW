<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="navbar.jsp" />

<html>
<head>
    <link href="${pageContext.request.contextPath}/css/services.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <title></title>
</head>
<body>
<c:set var="numParameters" value="0"/>
<c:url var="Path" value="/servicios">
    <c:if test="${not empty param.categoria}"><c:param name="categoria" value="${param.categoria}" /> <c:set var="numParameters" value="${numParameters+1}"/></c:if>
    <c:if test="${not empty param.query}"><c:param name="query" value="${param.query}" /><c:set var="numParameters" value="${numParameters+1}"/></c:if>
    <c:if test="${not empty paramValues.ubicacion}"><c:forEach var="ubicaciones" items="${paramValues.ubicacion}"><c:param name="ubicacion" value="${ubicaciones}"/> <c:set var="numParameters" value="${numParameters+1}"/></c:forEach></c:if>
</c:url>

<c:choose>
    <c:when test="${empty param or (numParameters == 0 and not empty param.pagina)}">
        <c:set var="filtersPath" value="${Path}?"/>
    </c:when>
    <c:otherwise>
        <c:set var="filtersPath" value="${Path}&"/>
    </c:otherwise>
</c:choose>


<div class="page">
    <div class="filters-info">
        <div class="flex">
            <form class="form-box" action="${pageContext.request.contextPath}/servicios" method="GET">
                <div class="search-container">
                    <input type="text" class="search-box" placeholder="Que estas buscando?" name="query" value="<c:out value="${param.query}"/>"/>
                    <button type="submit" class="search-button"><i class="material-icons">search</i></button>
                </div>
            </form>
            <div class="align-right">
                <label>
                    <p class="filters-text" onclick="toggleFilters()" id="toggleFiltersButton"><i class="material-icons">filter_alt</i>Filtrar por<i class="material-icons" id="filtersIcon">expand_more</i></p>
                </label>
            </div>
        </div>

        <c:if test="${category!=null}">
            <div class="category-header">
                <h2><c:out value="${category}"/></h2>
            </div>
        </c:if>

        <div class="align-right">
            <div class="filters-box transparent" id="filters">
                <h3>Calificacion</h3>
                <c:forEach items="${ratings}" var="rate">
                    <c:url value="/servicios" var="ratingChange">
                        <c:if test="${not empty param.categoria}"><c:param name="categoria" value="${param.categoria}" /></c:if>
                        <c:if test="${not empty param.query}"><c:param name="query" value="${param.query}" /></c:if>
                        <c:forEach var="ubicaciones" items="${paramValues.ubicacion}"><c:param name="ubicacion" value="${ubicaciones}"/></c:forEach>
                        <c:param name="calificacion" value="${rate.name}"/>
                    </c:url>
                    <a class="none-decoration filter-text" href="${ratingChange}">
                        <c:out value="${rate.name}"/>:
                        <c:forEach begin="1" end="${rate.minValue}">
                            <i class="material-icons stars">star</i>
                        </c:forEach>
                        <c:if test="${rate.minValue != 5}">
                            o mas
                        </c:if>
                    </a>
                </c:forEach>
                <h3>Ubicacion</h3>
                <c:forEach items="${neighbourhoods}" var="neighbourhood">
                    <c:url value="/servicios" var="locationChange">
                        <c:if test="${not empty param.categoria}"><c:param name="categoria" value="${param.categoria}" /></c:if>
                        <c:if test="${not empty param.query}"><c:param name="query" value="${param.query}" /></c:if>
                        <c:if test="${not empty param.calificacion}"><c:param name="calificacion" value="${param.calificacion}"/></c:if>
                        <c:forEach var="ubicaciones" items="${paramValues.ubicacion}"><c:if test="${ubicaciones != neighbourhood.value}"><c:param name="ubicacion" value="${ubicaciones}"/></c:if> </c:forEach>
                        <c:param name="ubicacion" value="${neighbourhood.value}"/>
                    </c:url>
                    <a class="none-decoration filter-text" href="${locationChange}"><c:out value="${neighbourhood.value}"/></a>
                </c:forEach>
            </div>
        </div>

        <c:if test="${not empty paramValues.ubicacion or not empty paramValues.calificacion}">
            <div class="filters-selected">
                <h4>Filtros seleccionados:</h4>
                <c:forEach items="${paramValues.ubicacion}" var="location">
                <button class="filter-container">
                        <c:out value="${location}"/>
                    <c:url value="/servicios" var="locationRemove">
                        <c:if test="${not empty param.categoria}"><c:param name="categoria" value="${param.categoria}" /></c:if>
                        <c:if test="${not empty param.query}"><c:param name="query" value="${param.query}" /></c:if>
                        <c:forEach var="calificaciones" items="${paramValues.calificacion}"><c:param name="calificacion" value="${calificaciones}"/></c:forEach>
                        <c:forEach items="${paramValues.ubicacion}" var="paramUb">
                            <c:if test="${paramUb != location}">
                                <c:param name="ubicacion" value="${paramUb}"/>
                            </c:if>
                        </c:forEach>
                    </c:url>
                        <a href="${locationRemove}"><i class="material-icons close-filter-icon">close</i></a>
                    </button>
                </c:forEach>
                <c:if test="${not empty param.calificacion}">
                    <button class="filter-container">
                        <c:out value="${param.calificacion}"/>
                        <c:url value="/servicios" var="rateRemove">
                            <c:if test="${not empty param.categoria}"><c:param name="categoria" value="${param.categoria}" /></c:if>
                            <c:if test="${not empty param.query}"><c:param name="query" value="${param.query}" /></c:if>
                            <c:forEach var="ubicaciones" items="${paramValues.ubicacion}"><c:param name="ubicacion" value="${ubicaciones}"/></c:forEach>
                        </c:url>
                        <a href="${rateRemove}"><i class="material-icons close-filter-icon">close</i></a>
                    </button>
                </c:if>
            </div>
        </c:if>

        <p class="comment">Resultados de busqueda: ${resultsAmount}</p>
    </div>

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
                        <a class="service-text" href="${pageContext.request.contextPath}/servicio/${item.id}">
                            <div class="service-data-container">
                                <div class="service-img-container">
                                    <img class="img service-img" src="${pageContext.request.contextPath}/images/${item.imageId}" alt="Imagen del servicio">
                                </div>
                                <div class="service-info">
                                    <div class="service-header">
                                        <h3> <c:out value="${item.name}"/></h3>
                                        <p class="align-right">$
                                            <c:choose>
                                                <c:when test="${item.pricing == TBDPricing}">
                                                    <p class="TBD-comment"><c:out value="${TBDPricing}"/></p>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:out value="${item.price}"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </p>
                                    </div>
                                    <p class="item"> <i class="material-icons">location_on</i>
                                        <c:if test="${not empty item.location}">
                                            <c:out value="${item.location}"/>,
                                        </c:if>
                                        <c:out value="${item.neighbourhoodAvailable}"/>
                                    </p>
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
                        <a class="page-text" href="${filtersPath}pagina=0"><< Primer pagina</a>
                        <a class="page-text" href="${filtersPath}pagina=${page-1}">< Anterior</a>
                    </c:when>
                    <c:otherwise>
                        <label class="none-page-text">Primer pagina</label>
                        <label class="none-page-text">Anterior</label>
                    </c:otherwise>
                </c:choose>


                <div class="page-nums">
                    <c:choose>
                        <c:when test="${pageCount < 6}">
                            <c:forEach var="i" begin="0" end="${pageCount-1}">
                                <a class="none-decoration" href="${filtersPath}pagina=${i}">
                                    <c:choose>
                                        <c:when test="${i == page}">
                                            <label class="page-num selected-page">${i}</label>
                                        </c:when>
                                        <c:otherwise>
                                            <label class="page-num none-selected-page">${i}</label>
                                        </c:otherwise>
                                    </c:choose>
                                </a>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${page < 3}">
                                    <c:forEach var="i" begin="0" end="3">
                                        <a class="none-decoration" href="${filtersPath}pagina=${i}">
                                            <c:choose>
                                                <c:when test="${i == page}">
                                                    <label class="page-num selected-page">${i}</label>
                                                </c:when>
                                                <c:otherwise>
                                                    <label class="page-num none-selected-page">${i}</label>
                                                </c:otherwise>
                                            </c:choose>
                                        </a>
                                    </c:forEach>
                                    <label class="none-selected-page">...</label>
                                    <a class="none-decoration" href="${filtersPath}pagina=${pageCount-1}"><label class="page-num none-selected-page"> <c:out value="${pageCount-1}"/> </label></a>
                                </c:when>
                                <c:when test="${page > pageCount-1-3}">
                                    <a class="none-decoration" href="${filtersPath}pagina=0"><label class="page-num none-selected-page"> 0 </label></a>
                                    <label class="none-selected-page">...</label>
                                    <c:forEach var="i" begin="${pageCount-1-3}" end="${pageCount-1}">
                                        <a class="none-decoration" href="${filtersPath}pagina=${i}">
                                            <c:choose>
                                                <c:when test="${i == page}">
                                                    <label class="page-num selected-page">${i}</label>
                                                </c:when>
                                                <c:otherwise>
                                                    <label class="page-num none-selected-page">${i}</label>
                                                </c:otherwise>
                                            </c:choose>
                                        </a>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <a class="none-decoration" href="${filtersPath}pagina=0"><label class="page-num none-selected-page"> 0 </label></a>
                                    <label class="page-num none-selected-page"> ... </label>
                                    <a class="none-decoration" href="${filtersPath}pagina=${page-1}"><label class="page-num none-selected-page"> <c:out value="${page-1}"/> </label></a>
                                    <a class="none-decoration" href="${filtersPath}pagina=${page}"><label class="page-num selected-page"> <c:out value="${page}"/> </label></a>
                                    <a class="none-decoration" href="${filtersPath}pagina=${page+1}"><label class="page-num none-selected-page"> <c:out value="${page+1}"/> </label></a>
                                    <label class="page-num none-selected-page"> ... </label>
                                    <a class="none-decoration" href="${filtersPath}pagina=${pageCount-1}"><label class="page-num none-selected-page"> <c:out value="${pageCount-1}"/> </label></a>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </div>


                <c:choose>
                    <c:when test="${page < pageCount-1}">
                        <a class="page-text" href="${filtersPath}pagina=${page+1}">Siguiente ></a>
                    </c:when>
                    <c:otherwise>
                        <label class="none-page-text">Siguiente</label>
                    </c:otherwise>
                </c:choose>

                <c:choose>
                    <c:when test="${page != pageCount-1}">
                        <a class="page-text" href="${filtersPath}pagina=${pageCount-1}">Ultima pagina >></a>
                    </c:when>
                    <c:otherwise>
                        <label class="none-page-text">Ultima pagina</label>
                    </c:otherwise>
                </c:choose>

            </div>
        </c:otherwise>
    </c:choose>
</div>

<script>
    function toggleFilters() {
        var showFilters = document.getElementById('filters');
        var icon = document.getElementById('filtersIcon');

        if (showFilters.classList.contains('transparent')) {
            showFilters.classList.remove('transparent');
            icon.textContent= "expand_less";
        } else {
            showFilters.classList.add('transparent');
            icon.textContent= "expand_more";
        }
    }
</script>
</body>
</html>
