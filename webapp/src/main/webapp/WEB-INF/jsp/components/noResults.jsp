<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/noneAppointment.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <title><spring:message code="global.no-results"/></title>
</head>
<body>
<c:set var="noResults"><spring:message code="global.no-results"/></c:set>
<div class="column-container">

    <div class="no-result-img-container">
        <img class="img" src="${pageContext.request.contextPath}/resources/noResults.svg" alt=""/>
    </div>
    <p class="no-results-text">${not empty message? message:noResults}</p>

    <c:if test="${ not empty urlCallToAction }">
        <div class="align-center">
            <a class="none-decoration" href="${urlCallToAction}">
                <button class="btn">
                    <label class="btn-text">${textCallToAction}</label>
                </button>
            </a>
        </div>
    </c:if>
</div>
</body>
</html>
