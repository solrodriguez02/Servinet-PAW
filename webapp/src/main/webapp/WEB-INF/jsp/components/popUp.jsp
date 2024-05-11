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
<div class="popup-contenedor" id="popup">
    <div class="popup">
        <h2>${title}</h2>
        <p>${message}</p>

        <div class="btns-box">
            <button class="cancelLinedBtn" onclick="closePopup()"><spring:message code="popup.cancel"/></button>
            <form:form action="${url}" method="${method}" class="form" id="popUpForm">
                <button class="cancelBtn" type="submit">${action}</button>
            </form:form>
        </div>
    </div>
</div>

<script>
    function closePopup() {
        document.getElementById("popup").style.display = "none";
    }
</script>
</body>
</html>
