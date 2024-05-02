<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:include page="navbar.jsp" />
<html>
<head>
  <link href="${pageContext.request.contextPath}/css/profile.css" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <title><spring:message code="title.business" arguments="${business.businessName}"/></title>
</head>
<body>
<div class="page">
  <div class="header">
    <h2><c:out value="${business.businessName}"/></h2>
    <c:url value="/borrar-negocio/${businessId}" var="deleteUrl"/>
    <div class="flex center-vertically">
      <button class="cancelBtn" id="deleteBtn" onclick="deleteBusiness()" >
        <i class="material-icons ">delete</i>
      </button>
      <a href="${pageContext.request.contextPath}/crear-servicio/${businessId}" class="none-decoration mr">
        <button class="btn center-vertically"><i class="material-icons ">add</i> Agregar servicio</button>
      </a>
      <c:if test="${not empty serviceList}" >
        <a href="${pageContext.request.contextPath}/negocio/${businessId}/turnos/?confirmados=false" class="none-decoration">
          <button class="btn center-vertically"><i class="material-icons ">calendar_today</i><spring:message code="business.appointments"/></button>
        </a>
      </c:if>
    </div>
  </div>

  <div class="boxes-container">
    <c:forEach items="${serviceList}" var="service" varStatus="loop">
      <a class="none-decoration" href="${pageContext.request.contextPath}/servicio/${service.id}">
        <div class="box preview-box">
          <img class="preview-box-img" src="${pageContext.request.contextPath}/images/${service.imageId}" alt=<spring:message code="business.service-image"/>>
          <p class="preview-box-text"><c:out value="${service.name}"/></p>
        </div>
      </a>
    </c:forEach>
    <c:if test="${empty serviceList}" >
      <div class="not-found-page">
        <spring:message code="business.not-found"/>
      </div>
    </c:if>
  </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/js/fetch.js"></script>
<script>

  function deleteBusiness(){
    if (!confirm('<spring:message code="business.confirm-delete"/>'))
      return

    if (send('${deleteUrl}','DELETE',{}) === 0)
      window.location.href = "${pageContext.request.contextPath}/negocios";
      //window.location.href = "${pageContext.request.contextPath}/operacion-invalida/?argumento=negocionoexiste";

  }
</script>
</html>
