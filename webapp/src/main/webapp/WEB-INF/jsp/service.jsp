<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="navbar.jsp" />
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/service.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <title></title>
</head>
<body>
    <div class="page">
        <h2><c:out value="${service.name}"/> (id: <c:out value="${service.id}"/>) </h2>

        <!--tal vez para un primer sprint se puede seleccionar cualquier fecha y horario
        (no se muestran los turnos que ya estan tomados) y una vez hecho el input se
         muestra un mensaje de error si es que esa fecha y hora ya fue tomada. -->

        <div class="info-container">
            <div class="img-container">
                <img class="service-img img" src="https://goldbricksgroup.com/wp-content/uploads/2021/08/y9DpT-600x390.jpg" alt="Imagen del servicio">
            </div>
            <div class="info-box">
                <div class="info-text">
                    <p class="category-text">${service.category}</p>
                    <p>${service.location}</p>
                    <p>${service.description}</p>
                </div>
            </div>
        </div>

        <div class="take-turn-container">
            <label>
                <input type="datetime-local"/>
            </label>
            <button class="take-turn-btn" id="take-turn">Reservar turno</button>
        </div>
    </div>
    <!--c:if test="${makeReservation}">
        <div id="take-turn-modal" style="display: block">
            <h2>hola</h2>
        </div>
    <!/c:if> -->
</body>
</html>

<script src="../../js/service.js"></script>