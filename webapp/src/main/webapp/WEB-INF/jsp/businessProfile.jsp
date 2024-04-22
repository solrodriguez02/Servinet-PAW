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
  <h2><c:out value="${business.businessName}"/></h2>
  <div class="boxes-container">
    <c:forEach items="${serviceMap}" var="service" varStatus="loop">
      <a class="none-decoration" href="${pageContext.request.contextPath}/servicio/${service.key}">
        <div class="box service-box">
          <p class="box-text"><c:out value="${service.value.name}"/></p>
        </div>
      </a>
    </c:forEach>
  </div>
</div>
</body>
</html>
