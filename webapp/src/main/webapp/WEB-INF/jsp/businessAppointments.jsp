<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="navbar.jsp" />
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/profile.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/business.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <title>Business</title>
</head>
<body>
<c:set var="isUser" value="false" scope="request" />
<div class="page">
    <div class="header">
        <h2><c:out value="${business.businessName}"/></h2>
        <div>
            <a href="${pageContext.request.contextPath}/negocio/${businessId}/turnos/?confirmados=true">
                <button >Proximos</button></a>
            <a href="${pageContext.request.contextPath}/negocio/${businessId}/turnos/?confirmados=false">
                <button >Solicitudes</button></a>
        </div>
    </div>
    <div class="appointments-container">
        <c:forEach items="${appointmentList}" var="appointment" varStatus="loop">
            <c:set var="appointment" value="${appointment}" scope="request" />
            <c:set var="loop" value="${loop}" scope="request" />
            <c:set var="serviceName" value="${serviceMap[appointment.serviceid].name}" scope="request" />
            <c:set var="email" value="${userMap[appointment.userid].email}" scope="request" />
            <c:set var="name" value="${userMap[appointment.userid].fullName}" scope="request"/>
            <jsp:include page="appointmentContainer.jsp"/>
        </c:forEach>
        <c:if test="${ empty appointmentList}">
            <p>No se han encontrado resultados</p>
        </c:if>
    </div>
</div>

</body>
</html>

