<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="navbar.jsp" />
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/service.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <title></title>
</head>
<body>
    <div class="page">
        <h2><c:out value="${service.name}"/></h2>

        <!--tal vez para un primer sprint se puede seleccionar cualquier fecha y horario
        (no se muestran los turnos que ya estan tomados) y una vez hecho el input se
         muestra un mensaje de error si es que esa fecha y hora ya fue tomada. -->

        <div class="info-container">
            <div class="img-container">
                <c:choose>
                    <c:when test="${service.imageurl == null}">
                        <img class="service-img img" src="https://goldbricksgroup.com/wp-content/uploads/2021/08/y9DpT-600x390.jpg" alt="Imagen del servicio">
                    </c:when>
                    <c:otherwise>
                        <img class="service-img img" src="${service.imageurl}" alt="Imagen del servicio">
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="info-box">
                <div class="info-text">
                    <p class="category-text"><c:out value="${service.category}"/></p>
                    <p class="text-with-icon"> <i class="material-icons icon">location_on</i><c:out value="${service.location}"/></p>
                    <p class="text-with-icon"> <i class="material-icons icon">schedule</i>
                        <c:choose>
                            <c:when test="${service.duration < 60}">
                                <c:out value="${service.duration}"/>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${service.duration / 60}"/>h
                                <c:if test="${service.duration % 60 != 0}">
                                    <c:out value="${service.duration % 60}"/>min
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <p class="text-with-icon"><i class="material-icons icon">attach_money</i><c:out value="${service.price}"/></p>
                    <p class="text-description"><c:out value="${service.description}"/></p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>

