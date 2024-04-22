<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="navbar.jsp" />
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="es">
<head>
    <link href="${pageContext.request.contextPath}/css/post.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <title>Servinet - Reserva tu turno</title>
</head>
<body>
<c:url value="/nuevo-turno/${serviceId}" var="postUrl"/>
<div class="postForm page">
    <form action="${postUrl}" method="post" class="form">
        <h3 class="form-title above-dropdown">Contratar el servicio: ${service.name}</h3>

        <h4 class="form-title above-dropdown">Información de contacto</h4>
        <label>
            <p class="label">Nombre:</p>
            <input required type="text" class="input" name="nombre" placeholder="Ingrese su nombre" value="${name}"/>
        </label>
        <label>
            <p class="label">Apellido:</p>
            <input required type="text" class="input" name="apellido" placeholder="Ingrese su apellido" value="${surname}"/>
        </label>
        <label>
            <p class="label">Email:</p>
            <input required type="text" class="input" name="email" placeholder="Ingresa su direccion de email de contacto"/>
        </label>
        <label>
            <p class="label">Telefono:</p>
            <input required type="text" class="input" name="telefono" placeholder="Ingresa su telefono"/>
        </label>
        <c:choose>
            <c:when test="${service.homeService}">
                <label for="select">
                    <div class="select">
                        <p class="label">Este servicio se realiza a domicilio, seleccione su barrio:</p>
                        <select id="select" class="appointment-select" name="barrios">
                            <option value=""></option>
                            <option value="${service.neighbourhoodAvailable}"><c:out value="${service.neighbourhoodAvailable}"/></option>
                        </select>
                    </div>
                    <p class="label">Direccion de residencia:</p>
                    <input required type="text" class="input" name="lugar" placeholder="Ingrese su direccion"/>
                </label>
            </c:when>
            <c:otherwise>
                <p class="label">Deberá presentarse en <c:out value="${service.location}"/></p>
                <label class="transparent">
                    <input required type="text" class="input" name="lugar" placeholder="Ingrese su direccion" value="-"/>
                </label>
            </c:otherwise>
        </c:choose>

        <label>
            <p class="label">Fecha:</p>
            <input required type="datetime-local" class="input" name="fecha" value="${date}"/>
        </label>
        <c:if test="${service.duration > 0}">
            <p>Recuerde que la duracion estimada del turno es de <span class="highlight-text">${service.duration} minutos</span></p>
        </c:if>
        <c:if test="${service.duration == 0}">
            <p>Recuerde que para este servicio <span class="highlight-text">no hay una duracion definida por el proveedor del mismo</span></p>
        </c:if>
        <div class="align-center btns-container">
            <a href="${pageContext.request.contextPath}/"><input type="button" value="Cancelar" class="cancelBtn"></a>
            <input type="submit" value="Reservar turno" class="btn submit-btn">
        </div>
    </form>
</div>
</body>
</html>