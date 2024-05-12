<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="navbar.jsp" />
<html>
<head>
    <title><spring:message code="title.reset-password"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css" />
    <link href="${pageContext.request.contextPath}/css/post.css" rel="stylesheet" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<c:url value="/restablecer-clave/${token}" var="postUrl"/>
<body>
<div class="postForm page">
    <form:form modelAttribute="PasswordResetForm" action="${postUrl}" method="post" class="form">
    <h2 class="form-title highlight-text"><spring:message code="reset-password"/></h2>
        <label>
            <p class="label"><spring:message code="password"/></p>
            <spring:message code="input.recover-password" var="inputPassword"/>
            <form:errors path="password" cssClass="error" element="p"/>
            <form:input type="password" id="password" cssClass="input" path="password" placeholder="${inputPassword}" required="true"/>
        </label>
        <label>
            <p class="label"><spring:message code="repeat-password"/></p>
            <spring:message code="input.recover-repeat-password" var="inputRepeatPassword"/>
            <form:errors path="passwordConfirmation" cssClass="error" element="p"/>
            <form:input type="password" id="repassword" cssClass="input" path="passwordConfirmation" placeholder="${inputRepeatPassword}" required="true"/>
        </label>
        <div class="align-center">
            <input type="submit" value="<spring:message code="reset-password.submit"/>" class="btn submit-btn">
        </div>
    </form:form>
</div>
</body>

</html>
