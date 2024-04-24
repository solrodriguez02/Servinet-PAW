<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/profile.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/business.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <title>Appointment container</title>
</head>
<body>

    <div class="appointment-container" id="${loop.count}">
        <div class="box appointment-box">
            <span class="appointment-field day"><c:out value="${appointment.startDateString}"/></span>
            <span class="appointment-field time"><i class="material-icons icon">schedule</i>    <c:out value="${appointment.startDateTimeString}"/>
                    <c:if test="${appointment.duration}">
                        - <c:out value="${appointment.endDateTimeString}"/>
                    </c:if>
                    </span>
            <a class="appointment-field service-name" href="${pageContext.request.contextPath}/servicio/${appointment.serviceid}">
                <c:out value="${serviceName}"/>
            </a>
            <span class="appointment-field id"> #<c:out value="${appointment.id}"/></span>

            <div class="decision-container appointment-field">
                <c:choose>
                    <c:when test="${confirmed || isUser}">
                        <button onclick="cancelAppointment(${appointment.id},${loop.count})" class="decision-btn accept-btn" ><i class="material-icons  icon cancel-icon">delete</i></button>
                    </c:when>
                    <c:otherwise>
                        <button onclick="acceptAppointment(${appointment.id},true,${loop.count})" class="decision-btn accept-btn" ><i class="material-icons  icon accept-icon">check</i></button>
                        <button onclick="acceptAppointment(${appointment.id},false,${loop.count})" class="decision-btn decision-btn"><i class="material-icons  deny-icon">add</i></button>
                    </c:otherwise>
                </c:choose>
            </div>
            <button class="appointment-field accordion-btn" onclick="showAccordion(${loop.count},this)">
                <i class="material-icons icon">arrow_drop_down</i>
            </button>
        </div>
        <div class="accordion-container">
            <c:if test="${confirmed}">
                <div class="appointment-field contact-container">
                    <c:if test="${not isUser}">
                        <span class="accordion-field"><i class="material-icons icon">account_circle</i>
                            <c:out value="${name}"/></span>
                    </c:if>
                    <span class=" accordion-field"><i class="material-icons icon">mail</i> <c:out value="${email}"/></span>
                </div>
            </c:if>
            <div class="appointment-field contact-container">
                <span class="appointment-field accordion-field"><i class="material-icons icon">house</i>
                    <c:choose>
                        <c:when test="${appointment.homeService}" >
                            <c:out value="${appointment.location}"/></c:when>
                        <c:otherwise>En el domicilio del profesional</c:otherwise>
                    </c:choose>
                </span>
            </div>
            <c:if test="${isUser}">
                <a href="${pageContext.request.contextPath}/turno/${appointment.serviceid}/${appointment.id}" class="none-decoration info-access">
                    <button class=" center-vertically info-btn"><i class="material-icons icon info-icon ">info</i> Info </button>
                </a>
            </c:if>
        </div>

    </div>
</body>



