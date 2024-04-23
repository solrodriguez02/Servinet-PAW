<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="navbar.jsp" />
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/profile.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <title></title>
</head>
<body>
<div class="page">
    <div class="header">
        <h2>Mis turnos</h2>
        <div>
            <a href="${pageContext.request.contextPath}/${userId}/turnos/?confirmados=true">
                <button >Proximos</button></a>
            <a href="${pageContext.request.contextPath}/${userId}/turnos/?confirmados=false">
                <button >Solicitudes</button></a>
        </div>
    </div>
    <div class="appointments-container">
        <c:forEach items="${appointmentList}" var="appointment" varStatus="loop">
            <c:set var="appointment" value="${appointment}" scope="request" />
            <c:set var="loop" value="${loop}" scope="request" />
            <jsp:include page="appointmentContainer.jsp"/>
        </c:forEach>
        <c:if test="${ empty appointmentList}">
            <p>No se han encontrado resultados</p>
        </c:if>
    </div>

</div>
</body>
</html>


