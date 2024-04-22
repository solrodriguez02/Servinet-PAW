<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="navbar.jsp" />
<html>
<head>

  <link href="${pageContext.request.contextPath}/css/business.css" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <title>Business</title>
</head>
<body>
<div class="page">
  <div class="business-header">
    <h2><c:out value="${business.businessName}"/></h2>
    <a href="${pageContext.request.contextPath}/negocio/${businessId}/turnos/?confirmados=false">
      <button class="view-appointments-btn">Turnos</button>
    </a>

  </div>

  <div class="boxes-container">
    <c:forEach items="${serviceList}" var="service" varStatus="loop">
      <a class="none-decoration" href="${pageContext.request.contextPath}/servicio/${service.id}">
        <div class="box service-box">
          <img class="service-img" src="${pageContext.request.contextPath}/images/${service.imageId}" alt="Imagen del servicio">
          <p class="box-text"><c:out value="${service.name}"/></p>
        </div>
      </a>
    </c:forEach>
  </div>
</div>
</body>
</html>
