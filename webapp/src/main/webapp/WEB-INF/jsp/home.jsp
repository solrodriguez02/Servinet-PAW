<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="navbar.jsp" />
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/home.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/services.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <title></title>
</head>
<body>
<div class="page">
    <div class="header">
        <h2 class="slogan">Conectando servicios, creando soluciones</h2>
        <div class="align-center">
            <form class="form-box" action="${pageContext.request.contextPath}/servicios" method="GET">
                <div class="search-container">
                    <input type="text" class="search-box" placeholder="Que estas buscando?" name="query" value="<c:out value="${param.query}"/>"/>
                    <button type="submit" class="search-button"><i class="material-icons">search</i></button>
                </div>
            </form>
        </div>
    </div>
    <h3>Busca por categorias</h3>
    <div class="boxes-container" id="someCategories">
        <c:forEach items="${categories}" var="category" varStatus="loop">
            <c:if test="${loop.index < 5}">
                <a class="none-decoration" href="${pageContext.request.contextPath}/servicios/?categoria=${category.value}">
                    <div class="box">
                        <h4 class="box-text"><c:out value="${category.value}"/></h4>
                        <i class="material-icons icon">${category.icon}</i>
                    </div>
                </a>
            </c:if>
        </c:forEach>
    </div>

    <div class="boxes-container boxes-container-wrap hide" id="allCateogries">
        <c:forEach items="${categories}" var="category" varStatus="loop">
                <a class="none-decoration" href="${pageContext.request.contextPath}/servicios/?categoria=${category.value}">
                    <div class="box">
                        <h4 class="box-text"><c:out value="${category.value}"/></h4>
                        <i class="material-icons icon">${category.icon}</i>
                    </div>
                </a>
        </c:forEach>
    </div>

    <div class="show-btn-container">
        <button class="show-btn" id="toggleCategoryButton" onclick="toggleCategory()">Mostrar mas</button>
    </div>

    <h3 class="container-text">Busca por cercania</h3>
    <div class="boxes-container" id="someLocations">
        <c:forEach items="${neighbourhoods}" var="neighbourhood" varStatus="loop">
            <c:if test="${loop.index < 5}">
                <a class="none-decoration" href="${pageContext.request.contextPath}/servicios/?ubicacion=${neighbourhood.value}">
                    <div class="box location-box">
                        <h4 class="box-text"><c:out value="${neighbourhood.value}"/></h4>
                    </div>
                </a>
            </c:if>
        </c:forEach>
    </div>

    <div class="boxes-container boxes-container-wrap hide" id="allLocations">
        <c:forEach items="${neighbourhoods}" var="neighbourhood" varStatus="loop">
                <a class="none-decoration" href="${pageContext.request.contextPath}/servicios/?ubicacion=${neighbourhood.value}">
                    <div class="box location-box">
                        <h4 class="box-text"><c:out value="${neighbourhood.value}"/></h4>
                    </div>
                </a>
        </c:forEach>
    </div>

    <div class="show-btn-container">
        <button class="show-btn" id="toggleLocationButton" onclick="toggleLocation()">Mostrar mas</button>
    </div>

    <div class="recommended-services-containers">
        <h3 class="container-text">Servicios recomendados</h3>
        <c:forEach items="${recommendedServices}" var="item">
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

    <div class="btn-container">
        <button class="btn">
            <a href="${pageContext.request.contextPath}/servicios" class="none-decoration btn-text">Ver todos los servicios</a>
        </button>
    </div>
</div>


<script>
    function toggleCategory() {
        var someCategories = document.getElementById('someCategories');
        var allCateogries = document.getElementById('allCateogries');
        var toggleButton = document.getElementById('toggleCategoryButton');

        if (someCategories.classList.contains('hide')) {
            someCategories.classList.remove('hide');
            allCateogries.classList.add('hide');
            toggleButton.textContent = 'Mostrar mas';
        } else {
            someCategories.classList.add('hide');
            allCateogries.classList.remove('hide');
            toggleButton.textContent = 'Mostrar menos';

        }
    }

    function toggleLocation() {
        var someLocations = document.getElementById('someLocations');
        var allLocations = document.getElementById('allLocations');
        var toggleButton = document.getElementById('toggleLocationButton');

        if (someLocations.classList.contains('hide')) {
            someLocations.classList.remove('hide');
            allLocations.classList.add('hide');
            toggleButton.textContent = 'Mostrar mas';
        } else {
            someLocations.classList.add('hide');
            allLocations.classList.remove('hide');
            toggleButton.textContent = 'Mostrar menos';

        }
    }

</script>
</body>
</html>


