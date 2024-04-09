<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="navbar.jsp" />
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/appointment.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <title>Servinet - Detalle del Turno #${appointmentId}</title>
</head>
<body class="page">
<c:url value="/cancelar-turno/${appointment.id}" var="cancelUrl" />
<div class="top-bar">
    <div class="back-button">
        <a href="/webapp_war/">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-left arrow" viewBox="0 0 16 16">
            <path fill-rule="evenodd" d="M15 8a.5.5 0 0 0-.5-.5H2.707l3.147-3.146a.5.5 0 1 0-.708-.708l-4 4a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L2.707 8.5H14.5A.5.5 0 0 0 15 8"/>
        </svg>
        </a>
        <p>Volver a la lista de servicios</p>
    </div>
    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
    <form action="${cancelUrl}" method="post">
        <button type="submit" class="btn btn-primary btn-danger">Cancelar turno</button>
    </form>
    </div>
</div>
<div class="appointment-container">
    <div class="title">
        <h1 class="form-title">Detalle del Turno: #${appointment.id}</h1>
    </div>
    <c:if test="${appointment != null}">
        <div class="card border-success text-bg-sucess mb-3 fixed-card">
            <div class="card-header success-title">
                <svg xmlns="http://www.w3.org/2000/svg" width="40px" height="40px" viewBox="0 0 16 16"><path fill="#4aa80b" d="M10.97 4.97a.75.75 0 0 1 1.07 1.05l-3.99 4.99a.75.75 0 0 1-1.08.02L4.324 8.384a.75.75 0 1 1 1.06-1.06l2.094 2.093l3.473-4.425z"/></svg>
                <h2>¡Todo listo ${user.name}!</h2>
            </div>
            <div class="card-body">
                <p class="card-text">Tu turno fue solicitado con éxito</p>
                <p class="card-text">Se te enviará un mail con la decisión del proveedor del servicio</p>
            </div>
        </div>
    </c:if>
    <div class="card border-light mb-3 fixed-card">
        <div class="card-header"><h3>Información del servicio</h3></div>
        <div class="card-body">
            <h5 class="card-title">${service.name}</h5>
            <p class="card-text"><span class="highlight-text">Descripción:</span> ${service.description}<p>
                <p class="card-text"><span class="highlight-text">Precio:</span> ${service.price}</p>
        </div>
    </div>
    <div class="card border-light mb-3 fixed-card">
        <div class="card-header"><h3>Información del turno</h3></div>
        <div class="card-body">
            <p class="card-text"><span class="highlight-text">Estado del turno:</span> ${appointment.confirmed ? "Confirmado":"Pendiente de confirmación"}</p>
            <p class="card-text"><span class="highlight-text">Nombre del solicitante:</span> ${user.name} ${user.surname}</p>
            <p class="card-text"><span class="highlight-text">Fecha y hora: </span>${appointment.startDateString}</p>
            <p class="card-text"><span class="highlight-text">Lugar:</span> ${appointment.location}</p>
        </div>
    </div>
</div>
</body>
</html>
