<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="navbar.jsp" />
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/css/post.css" rel="stylesheet" type="text/css"/>
    <title>Solicitar un restablecimiento de contraseña</title>
</head>
<c:url value="/olvide-mi-clave" var="postUrl"/>
<body>
<div class="postForm page">
    <form:form modelAttribute="requestPasswordRecoveryForm" action="${postUrl}" method="post" class="form">
    <h2 class="form-title highlight-text"><spring:message code="recover-password"/></h2>
    <label>
        <spring:message code="input.recover-email" var="recoverMail"/>
        <form:errors path="email" cssClass="error"/>
        <form:input type="email" path="email" class="input" placeholder="${recoverMail}"/>
        <p>
        <spring:message code="recover.explanation"/>
        </p>
    </label>
        <input type="submit" value="<spring:message code="recover.submit"/>" class="submitBtn large-centered-btn"/>
    </form:form>
</div>
</body>
</html>
