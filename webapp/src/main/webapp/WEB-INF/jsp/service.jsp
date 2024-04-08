<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="navbar.jsp" />
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/service.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <title></title>
</head>
<c:url var="deleteUrl" value="/${service.id}/deleteservicio" />
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
                    <form action="${deleteUrl}" method="post">
                        <input type="submit" value="Borrar servicio" class="deleteBtn"/>
                    </form>
                </div>
            </div>
        </div>

        <label>
            <input type="datetime-local"/>
        </label>

        <div class="take-turn-container">
            <label>
                <input type="datetime-local"/>
            </label>
            <form>
                <button class="take-turn-btn" type="submit" >Reservar turno
                </button>
            </form>

        </div>

    </div>

</body>
</html>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/service.js"></script>
<script>
    console.log( document)
</script>