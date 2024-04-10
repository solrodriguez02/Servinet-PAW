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
    <div class="header">
        <h2 class="slogan">Conectando servicios, creando soluciones</h2>
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


