<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="navbar.jsp" />
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/appointment.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>
<div class="page">
    <div class="appointment-container">
        <div class="title">
            <h1 class="form-title">Detalle del Turno: #${appointment.id}</h1>
        </div>
        <c:if test="${appointment != null}">
            <div class="box">
                <div class="box-info">
                    <div class="align-center">
                        <c:choose>
                            <c:when test="${confirmed}">
                                <i class="material-icons confirmed-icon">check</i>
                                <h2>¡Todo listo ${user.name}!</h2>
                            </c:when>
                            <c:otherwise>
                                <i class="material-icons waiting-icon">schedule</i>
                                <h2>Esperando confirmacion</h2>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="align-center">
                        <c:choose>
                            <c:when test="${confirmed}">
                                <p>Su turno fue confirmado</p>
                            </c:when>
                            <c:otherwise>
                                <div class="state-text">
                                    <p>Su turno fue solicitado con éxito</p>
                                    <p>Se le enviará un mail con la decisión del proveedor del servicio</p>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </c:if>

        <div class="box">
            <div class="box-info">
                <h3>Información del servicio</h3>
                <p><span class="highlight-text">Servicio:</span> ${service.name}<p>
                <p><span class="highlight-text">Descripción:</span> ${service.description}<p>
                <p><span class="highlight-text">Precio:</span> ${service.price}</p>

                <h3 class="appointment-info">Información del turno</h3>
                <p><span class="highlight-text">Estado del turno:</span> ${appointment.confirmed ? "Confirmado":"Pendiente de confirmación"}</p>
                <p><span class="highlight-text">Nombre del solicitante:</span> ${user.name} ${user.surname}</p>
                <p><span class="highlight-text">Fecha y hora: </span>${appointment.startDateString}</p>
                <p><span class="highlight-text">Lugar:</span> ${appointment.location}</p>
            </div>
        </div>

        <c:url value="/cancelar-turno/${appointment.id}" var="cancelUrl" />
        <div class="align-center">
            <form action="${cancelUrl}" method="post">
                <button type="submit" class="cancelBtn appointment-cancelBtn">Cancelar turno</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
