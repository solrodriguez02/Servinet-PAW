<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:include page="navbar.jsp" />
<html>
<head>
  <link href="${pageContext.request.contextPath}/css/profile.css" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/css/publish.css" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <title><spring:message code="title.businesses"/></title>
</head>
<body>
<c:url value="/registrar-negocio" var="registerBusiness"/>
<div class="page">
  <div class="header">
    <h3><spring:message code="business.select-to-add"/></h3>
    <a href="${registerBusiness}" class="none-decoration">
      <button class="btn center-vertically btn-new"><i class="material-icons business-icon">storefront</i><spring:message code="business.new"/></button>
    </a>
  </div>

  <div class="boxes-container">
    <c:forEach items="${businessList}" var="business" varStatus="loop">
      <a class="none-decoration" href="${pageContext.request.contextPath}/crear-servicio/${business.businessid}">
        <div class="box preview-box">
          <p class="preview-box-text"><c:out value="${business.businessName}"/></p>
          <p class="add-service-text"><spring:message code="business.add-service"/><i class="material-icons add-icon">add</i></p>
        </div>
      </a>
    </c:forEach>
  </div>
</div>
</body>
</html>


