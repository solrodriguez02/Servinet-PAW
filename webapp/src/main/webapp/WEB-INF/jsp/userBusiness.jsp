<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:include page="navbar.jsp" />
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/profile.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <title><spring:message code="title.my-businesses"/></title>
</head>
<body>
<div class="page">
    <div class="header">
        <h2><spring:message code="businesses.my-businesses"/></h2>
        <a href="${pageContext.request.contextPath}/negocios/consultas" class="none-decoration">
            <button class="btn center-vertically"><i class="material-icons ">notifications_active</i><spring:message code="businesses.questions"/></button>
        </a>
    </div>

    <div class="boxes-container">
        <c:forEach items="${businessList}" var="business" varStatus="loop">
            <a class="none-decoration" href="${pageContext.request.contextPath}/negocio/${business.businessid}">
                <div class="box preview-box">
                    <!--img class="preview-box-img" src="${pageContext.request.contextPath}/images/{business.imageId}" alt="Imagen del servicio"-->
                    <p class="preview-box-text"><c:out value="${business.businessName}"/></p>
                </div>
            </a>
        </c:forEach>
    </div>
</div>
</body>
</html>


