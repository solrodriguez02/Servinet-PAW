<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="navbar.jsp" />
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/post.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><spring:message code="title.join-servinet"/></title>
</head>
<body>
<c:url value="/registrarse" var="postUrl"/>
<div class="postForm page">
    <form:form action="${postUrl}" modelAttribute="registerUserForm" method="post" class="form">
        <h2 class="form-title highlight-text"><spring:message code="register.create-profile"/></h2>
        <label>
            <p class="label"><spring:message code="name"/></p>
            <spring:message code="input.name" var="inputName"/>
            <form:errors path="name" cssClass="error" element="p"/>
            <form:input type="text" cssClass="input" path="name" placeholder="${inputName}"/>
        </label>
        <label>
            <p class="label"><spring:message code="lastname"/></p>
            <spring:message code="input.lastname" var="inputLastname"/>
            <form:errors path="surname" cssClass="error" element="p"/>
            <form:input type="text" cssClass="input" path="surname" placeholder="${inputLastname}"/>
        </label>
        <label>
            <p class="label"><spring:message code="email"/></p>
            <spring:message code="input.email" var="inputEmail"/>
            <form:errors path="email" cssClass="error" element="p"/>
            <form:input id="email" cssClass="input" path="email" placeholder="${inputEmail}"/>
        </label>
        <label>
            <p class="label"><spring:message code="telephone"/></p>
            <spring:message code="input.telephone" var="inputTelephone"/>
            <form:errors path="telephone" cssClass="error" element="p"/>
            <form:input type="text" cssClass="input" path="telephone" placeholder="${inputTelephone}" value="+54 9 "/>
        </label>
        <label>
            <p class="label"><spring:message code="username"/></p>
            <spring:message code="input.username" var="inputUsername"/>
            <form:errors path="username" cssClass="error" element="p"/>
            <form:input type="text" cssClass="input" path="username" placeholder="${inputUsername}"/>
        </label>
        <label>
            <p class="label"><spring:message code="password"/></p>
            <spring:message code="input.password" var="inputPassword"/>
            <form:errors path="password" cssClass="error" element="p"/>
            <form:input type="password" id="password" cssClass="input" path="password" placeholder="${inputPassword}"/>
        </label>
            <form:errors path="" cssClass="error"/>
        <label>
            <p class="label"><spring:message code="repeat-password"/></p>
            <spring:message code="input.repeat-password" var="inputRepeatPassword"/>
            <form:errors path="passwordConfirmation" cssClass="error" element="p"/>
            <form:input type="password" id="repassword" cssClass="input" path="passwordConfirmation" placeholder="${inputRepeatPassword}"/>
        <p>
        </label>
            <spring:message code="register.already-registered"/> <a href="<c:url value="/login"/>"><spring:message code="login"/></a>
        </p>
        <div class="align-center">
            <input type="submit" value="<spring:message code="register.submit"/>" class="btn submit-btn">
        </div>
    </form:form>

</div>
</body>
</html>
