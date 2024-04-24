<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="navbar.jsp" />
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/profile.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <title></title>
</head>
<body>
<c:set var="isUser" value="true" scope="request" />
<div class="page">
    <div class="header">
        <h2>Mis turnos</h2>
        <div>
            <a href="${pageContext.request.contextPath}/turnos/?confirmados=true">
                <button class="btn-basic btn-left ${confirmed? 'selected':''}" >Proximos</button></a>
            <a href="${pageContext.request.contextPath}/turnos/?confirmados=false">
                <button class="btn-basic btn-right ${!confirmed? 'selected':''}" >Solicitudes</button></a>
        </div>
    </div>
    <div class="appointments-container">
        <c:forEach items="${appointmentList}" var="appointment" varStatus="loop">
            <c:set var="appointment" value="${appointment}" scope="request" />
            <c:set var="loop" value="${loop}" scope="request" />
            <c:set var="serviceName" value="${appointment.serviceName}" scope="request" />
            <c:set var="email" value="${appointment.businessEmail}" scope="request" />
            <jsp:include page="appointmentContainer.jsp"/>
        </c:forEach>
        <c:if test="${ empty appointmentList}">
            <p>No se han encontrado resultados</p>
        </c:if>
    </div>

</div>
</body>
</html>

<jsp:include page="appointmentScript.jsp" />