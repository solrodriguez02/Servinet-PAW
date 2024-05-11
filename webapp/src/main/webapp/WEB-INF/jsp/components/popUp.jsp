<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
            <a id="url" href="">
                <button class="cancelBtn">${action}</button>
            </a>
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
