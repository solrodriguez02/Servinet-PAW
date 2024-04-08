<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="navbar.jsp" />
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="es">
<head>
    <link href="${pageContext.request.contextPath}/css/post.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
        <c:if test="${service.homeService}">
            <label for="select">
             <div class="select">
                <p class="label">Este servicio se realiza a domicilio, seleccione su barrio:</p>
                <select id="select" class="appointment-select" name="barrios">
                    <option value=""></option>
                    <option value="${service.location}">${service.location}</option>
                </select>
               </div>
                <p class="label">Direccion de residencia:</p>
                <input required type="text" class="input" name="lugar" placeholder="Ingrese su direccion" value="${description}"/>
            </label>
        </c:if>
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
        <div class="options">
            <a href="/webapp_war/"><input type="button" value="Cancelar" class="cancelBtn"></a>
            <input type="submit" value="Reservar turno" class="submitBtn">
        </div>
    </form>
</div>
</body>
</html>