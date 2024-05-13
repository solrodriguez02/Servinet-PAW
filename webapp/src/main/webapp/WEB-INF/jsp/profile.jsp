<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:include page="navbar.jsp"/>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/profile.css" rel="stylesheet" />

    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <title><spring:message code="title.profile"/></title>
</head>
<body>
<c:url value="/logout" var="logout"/>
<div class="page">
    <div class="box profile-box">
        <div class="profile-info-container profile-field">
            <h3><c:out value="${user.fullName}"/></h3>
            <p class="accordion-field center-vertically"><i class="material-icons icon">account_circle</i>  <c:out value="${user.username}"/></p>
            <p class="accordion-field center-vertically"><i class="material-icons icon">mail</i>  <c:out value="${user.email}"/></p>
        </div>
        <div class="align-center">
            <a href="${logout}">
                <button class="cancelBtn">
                    <spring:message code="profile.logout"/>
                </button>
            </a>
        </div>
    </div>

    <c:if test="${user.provider}">
    <div class="header">
        <h2><spring:message code="profile.businesses"/></h2>
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
        <c:if test="${empty businessList}" >
            <div class="not-found-page">
                <spring:message code="profile.no-services"/>
            </div>
        </c:if>
    </div>

    </c:if>
</div>
</body>
</html>


