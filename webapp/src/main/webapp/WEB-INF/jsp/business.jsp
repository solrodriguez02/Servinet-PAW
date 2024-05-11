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

<c:url value="/borrar-negocio/${businessId}" var="deleteUrl"/>

<c:set var="title" scope="request"><spring:message code="popup.business.title"/></c:set>
<c:set var="message" scope="request"><spring:message code="popup.business.message"/></c:set>
<c:set var="action" scope="request"><spring:message code="popup.delete"/></c:set>
<c:set var="url" value="${deleteUrl}" scope="request"/>

<jsp:include page="components/popUp.jsp" />

<c:set var="urlCreateService" value="${pageContext.request.contextPath}/crear-servicio/${businessId}" />
<div class="page">
  <div class="header">
    <h2><c:out value="${business.businessName}"/></h2>
    <div class="flex center-vertically">
      <button class="cancelBtn" id="deleteBtn" onclick="showPopUp()" >
        <i class="material-icons ">delete</i>
      </button>
      <c:if test="${not empty serviceList}" >
        <a href="${urlCreateService}" class="none-decoration mr">
          <button class="btn center-vertically"><i class="material-icons ">add</i> <spring:message code="business.add-service"/></button>
        </a>
        <a href="${pageContext.request.contextPath}/negocio/${businessId}/turnos/?confirmados=false" class="none-decoration">
          <button class="btn center-vertically"><i class="material-icons ">calendar_today</i> <spring:message code="business.appointments"/></button>
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
    <c:if test="${ empty serviceList}">
      <c:set var="urlCallToAction" value="${urlCreateService}" scope="request" />
      <c:set var="message" scope="request"><spring:message code="business.not-found"/></c:set>
      <c:set var="textCallToAction" scope="request">+ <spring:message code="business.add-service"/></c:set>
      <jsp:include page="components/noResults.jsp"/>
    </c:if>
  </div>
</div>
</body>

<script>
  function showPopUp() {
    document.getElementById("popup").style.display = "block";
  }
</script>
</html>
