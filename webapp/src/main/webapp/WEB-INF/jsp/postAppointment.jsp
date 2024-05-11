<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:include page="navbar.jsp" />
<html lang="es">
<head>
    <link href="${pageContext.request.contextPath}/css/post.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <title><spring:message code="title.reserve-appointment"/></title>
</head>
<body>
<c:url value="/contratar-servicio/${serviceId}" var="postUrl"/>
<div class="postForm page">
    <form:form action="${postUrl}" method="post" class="form" modelAttribute="appointmentForm">
        <h3 class="form-title above-dropdown"><spring:message code="appointment.create" arguments="${service.name}"/></h3>

        <!--
        <label>
            <p class="label"><message code="appointment.name-solicitor"/></p>
            <message code="input.name" var="name"/>
            <input type="text" class="input" path="name" placeholder="{name}" value="{user.name}"/>
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
-->
        <c:choose>
            <c:when test="${service.homeService}">
                <label for="select">
                    <div class="select">
                        <p class="label"><spring:message code="appointment.home-service"/></p>
                        <p class="label"><spring:message code="input.appointment-neighbourhood"/></p>
                        <form:select id="select" class="appointment-select" path="neighbourhood">
                            <option value=""></option>
                            <c:forEach var="neighbour" items="${service.neighbourhoodAvailable}">
                                <option value="${neighbour}"><c:out value="${neighbour}"/></option>
                            </c:forEach>
                        </form:select>
                    <!--        <option value="${service.location}"><c:out value="${service.location}"/></option> -->
                    </div>
                    <p class="label"><spring:message code="address"/></p>
                    <spring:message code="input.address" var="address"/>
                    <form:input type="text" class="input" path="location" placeholder="${address}" required="true"/>
                </label>
            </c:when>
            <c:otherwise>
                <p class="label"><spring:message code="appointment.location"/><c:out value="${service.location}"/></p>
                <!--
                <label class="transparent">
                    <input required="true" type="text" class="input" path="" placeholder="Ingrese su direccion" value="-"/>
                </label>
                -->
            </c:otherwise>
        </c:choose>

        <label>
            <p class="label"><spring:message code="appointment.date"/></p>
            <form:input required="true" type="datetime-local" class="input" path="date" />
            <form:errors path="date" cssClass="error" element="p"/>
        </label>
        <c:if test="${service.duration > 0}">
            <p><spring:message code="appointment.duration-general"/><span class="highlight-text"><spring:message code="appointment.duration-defined" arguments="${service.duration}"/></span></p>
        </c:if>
        <c:if test="${service.duration == 0}">
            <p><spring:message code="appointment.duration-general"/><span class="highlight-text"><spring:message code="appointment.duration-not-defined"/></span></p>
        </c:if>

        <div class="align-center btns-container">
            <spring:message code="cancel" var="cancel"/>
            <a href="${pageContext.request.contextPath}/"><input type="button" value="${cancel}" class="cancelBtn"></a>
            <spring:message code="appointment.submit" var="submit"/>
            <input type="submit" value="${submit}" class="btn submit-btn">
        </div>
    </form:form>
</div>
</body>
</html>