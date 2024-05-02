<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="navbar.jsp" />
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/appointment.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <title><spring:message code="title.appointment" arguments="${appointment.id}"/></title>
</head>
<body>
<div class="page">
    <div class="appointment-container">
        <div class="title">
            <h1 class="form-title"><spring:message code="appointment.detail" arguments="${appointment.id}"/></h1>
        </div>
        <c:if test="${appointment != null}">
            <div class="box">
                <div class="box-info">
                    <div class="align-center">
                        <c:choose>
                            <c:when test="${confirmed}">
                                <i class="material-icons confirmed-icon">check</i>
                                <h2><spring:message code="appointment.ready" arguments="${user.name}"/></h2>
                            </c:when>
                            <c:otherwise>
                                <i class="material-icons waiting-icon">schedule</i>
                                <h2><spring:message code="appointment.waiting-confirmation"/></h2>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="align-center">
                        <c:choose>
                            <c:when test="${confirmed}">
                                <p><spring:message code="appointment.confirmed"/></p>
                            </c:when>
                            <c:otherwise>
                                <div class="state-text">
                                    <p><spring:message code="appointment.successfully-requested"/></p>
                                    <p><spring:message code="appointment.mail-send"/></p>
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
                <p><span class="highlight-text">Servicio:</span>
                    <a class="service-name none-decoration" href="${pageContext.request.contextPath}/servicio/${appointment.serviceid}">
                        <c:out value="${service.name}"/></a><p>
                <p><span class="highlight-text">Descripción:</span> ${service.description}<p>
                <p><span class="highlight-text">Precio:</span> ${service.price}</p>

                <h3 class="appointment-info">Información del turno</h3>
                <p><span class="highlight-text">Estado del turno:</span> ${appointment.confirmed ? "Confirmado":"Pendiente de confirmación"}</p>
                <p><span class="highlight-text">Nombre del solicitante:</span> ${user.name} ${user.surname}</p>
                <p><span class="highlight-text">Fecha y hora: </span>${appointment.startDateString}</p>
                <p><span class="highlight-text">Lugar:</span> ${service.homeService? appointment.location : service.location}</p>
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
