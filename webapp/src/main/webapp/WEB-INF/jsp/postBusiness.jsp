<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="navbar.jsp" />
<html>
<head>
    <title>Registra tu emprendimiento en Servinet</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/post.css">
</head>
<body>
<c:url value="/registrar-negocio" var="postUrl"/>
<div class="postForm page">
    <form:form action="${postUrl}" modelAttribute="BusinessForm" method="post" class="form">
    <h2 class="form-title highlight-text"><spring:message code="register.create-business"/></h2>
    <label>
        <p class="label"><spring:message code="business-name"/></p>
        <spring:message code="input.business" var="inputName"/>
        <form:input type="text" class="input" path="businessName" placeholder="${inputName}" required="true"/>
        <form:errors path="businessName" cssClass="error" element="p"/>
    </label>
    <label>
        <p class="label"><spring:message code="business-email"/></p>
        <spring:message code="input.business-email" var="inputEmail"/>
        <form:input type="email" class="input" path="businessEmail" placeholder="${inputEmail}" required="true"/>
        <form:errors path="businessEmail" cssClass="error" element="p"/>
    </label>
    <label>
        <p class="label"><spring:message code="telephone"/></p>
        <spring:message code="input.telephone" var="inputTelephone"/>
        <form:input type="text" class="input" path="businessTelephone" placeholder="${inputTelephone}" required="true"/>
        <form:errors path="businessTelephone" cssClass="error" element="p"/>
    </label>
    <label>
        <p class="label"><spring:message code="address"/></p>
        <spring:message code="input.business-address" var="inputAddress"/>
        <form:input type="text" class="input" path="businessLocation" placeholder="${inputAddress}" />
        <form:errors path="businessLocation" cssClass="error" element="p"/>
    </label>
    <input type="submit" value="<spring:message code="register.submit"/>" class="submitBtn">
    </form:form>
</body>
</html>
