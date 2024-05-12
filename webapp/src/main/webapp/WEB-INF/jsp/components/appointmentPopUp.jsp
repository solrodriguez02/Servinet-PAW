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

        <h2><spring:message code="${requestForBusiness? 'popup.deny-appointment.title':'popup.appointment.title'}"/></h2>
        <p><spring:message code="${isUser? 'popup.appointment.message':'popup.deny-appointment.message'}"/> </p>

        <div class="btns-box">
            <button class="cancelLinedBtn" onclick="closePopup(${id})"><spring:message code="popup.cancel"/></button>
            <c:choose>
                <c:when test="${requestForBusiness}">
                    <button class="cancelBtn" onclick="acceptAppointment(${appointmentId},false,${loop.count})"><spring:message code="popup.appointment.deny"/></button>
                </c:when>
                <c:otherwise>
                    <button class="cancelBtn" onclick="cancelAppointment(${appointmentId},${loop.count})"><spring:message code="popup.appointment.cancel"/></button>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

</body>
</html>
