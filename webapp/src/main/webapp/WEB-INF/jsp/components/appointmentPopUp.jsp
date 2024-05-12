<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/popUp.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <title></title>
</head>
<body>

<div class="popup-contenedor" id="${id}">
    <div class="popup">
        <h2>aaa</h2>
        <p>aaa</p>

        <div class="btns-box">
            <button class="cancelLinedBtn" onclick="closePopup(${id})"><spring:message code="popup.cancel"/></button>
            <c:choose>
                <c:when test="${confirmed}">
                    <button class="cancelBtn" onclick="cancelAppointment(${appointmentId},${loop.count})">Cancelar</button>
                </c:when>
                <c:otherwise>
                    <button class="cancelBtn" onclick="acceptAppointment(${appointmentId},false,${loop.count})">Rechazar</button>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

</body>
</html>
