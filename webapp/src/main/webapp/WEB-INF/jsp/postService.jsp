<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:include page="navbar.jsp" />
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/post.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <title><spring:message code="title.create-service"/></title>
</head>
<body>
<c:url value="/crear-servicio/${businessId}" var="postUrl"/>
<div class="postForm page">
    <form:form action="${postUrl}" method="post" class="form" modelAttribute="serviceForm" enctype="multipart/form-data">
        <h3 class="form-title"><spring:message code="service.create"/></h3>
        <label>
           <p> <spring:message code="service-name"/> </p>
            <spring:message code="input.service.name" var="serviceName"/>
            <form:errors path="title" cssClass="error"/>
            <form:input type="text" class="input" path="title" placeholder="${serviceName}" required="true"/>
        </label>
      <div class="service-div">
        <label>
            <p> <spring:message code="input.service.image"/> </p>
            <form:input type="file" class="input" path="image" accept=".png, .jpg, .jpeg"/>
            <form:errors path="image" cssClass="error"/>
        </label>
        </div>
        <label>
            <p> <spring:message code="service.description"/> </p>
            <spring:message code="input.service.description" var="serviceDescription"/>
            <form:errors path="description" cssClass="error"/>
            <form:input type="text" class="input" path="description" placeholder="${serviceDescription}"/>
        </label>
            <p><spring:message code="service.location"/></p>
                <form:checkbox id="homeservice" path="homeserv" />
                <form:errors path="homeserv" cssClass="error"/>
                <label for="homeservice"><spring:message code="service.home-service"/></label>
            <div class="service-div">
                <form:select path="neighbourhood" multiple="true" checkboxes="true" class="input" >
                    <c:forEach items="${neighbourhoods}" var="item">
                        <form:option value="${item}"><c:out value="${item.value}"/></form:option>
                    </c:forEach>
                </form:select>
                <form:errors path="neighbourhood" cssClass="error"/>
                <spring:message code="input.service.location" var="serviceLocation"/>
                <form:errors path="location" cssClass="error"/>
                <form:input type="text" class="input" path="location" value="" id="locationInput" placeholder="${serviceLocation}"/>
            </div>
            <form:errors path="" cssClass="error"/>
        <label>
            <p class="label"><spring:message code="service.price"/></p>
            <label>
                <form:checkbox id="additionalCharges" path="additionalCharges" />
                <form:errors path="additionalCharges" cssClass="error"/>
                <label for="additionalCharges"><spring:message code="service.additionalCharges"/></label>
            </label>

            <div class="service-div">
            <form:select path="pricingtype" class="input" id="priceType">
                <c:forEach items="${pricingTypes}" var="item">
                    <form:option  value="${item}">${item.value}</form:option>
                </c:forEach>
            </form:select>
            <spring:message code="input.service.price" var="servicePrice"/>
            <form:errors path="price" cssClass="error"/>
            <form:input type="text" class="input" path="price" id="priceTag" placeholder="${servicePrice}"/>
            </div>
        </label>

        <label>
            <p class="label"><spring:message code="service.category"/></p>
            <form:select path="category" class="input">
                <c:forEach items="${categories}" var="item">
                <form:option value="${item}">${item.value}</form:option>
                </c:forEach>
            </form:select>
        </label>
            <p class="label"><spring:message code="service.duration"/></p>
            <spring:message code="input.service.duration" var="serviceDuration"/>
            <form:errors path="minimalduration" cssClass="error"/>
            <div class="btn-group" role="group" aria-label="Basic example">
                <c:forEach var="durType" items="${durationTypes}" varStatus="loop">
                    <span class="r-pills">
                        <form:radiobutton hidden="hidden" path="minimalduration" value="${durType.value}" />
                        <label for="minimalduration${loop.index+1}"><spring:message code="${durType.codeMsg}"/>
                        </label>
                    </span>
                </c:forEach>
            </div>
        <div class="align-center">
            <input type="submit" value="Publicar" class="btn submit-btn">
        </div>
    </form:form>
</div>
</body>
</html>
<script>
    const homeservice = document.getElementById('homeservice');
    const locationInput = document.getElementById('locationInput');
    homeservice.addEventListener('change', () => {
        if (homeservice.checked) {
            locationInput.style.display = 'none';
            locationInput.querySelector('input').value = '';
        } else {
            locationInput.style.display = 'block';
        }
    });
    const priceLabel = document.getElementById('priceType');
    const priceInput = document.getElementById('priceTag');
    priceLabel.addEventListener('change', () => {
        if (priceLabel.value == 'TBD') {
            priceInput.style.display = 'none';
            priceInput.querySelector('input').value = '';
        } else {
            priceInput.style.display = 'block';
        }
    })

    document.querySelectorAll('select[multiple][checkboxes] option').forEach(option => {
        if (option.selected) {
            option.textContent = '☑️ ' + option.textContent;
        } else {
            option.textContent = '🔳 ' + option.textContent;
        }
    });

    document.querySelector('select[multiple][checkboxes]').addEventListener('mousedown', function (e) {
        e.preventDefault();
        const initialPosition = e.target.parentElement.scrollTop;
        if (e.target.tagName !== 'OPTION') return;
        e.target.selected = !e.target.selected;
        if (e.target.selected) {
            e.target.textContent = e.target.textContent.replace('🔳', '☑️');
        } else if (!e.target.selected) {
            e.target.textContent = e.target.textContent.replace('☑️', '🔳');
        }
        setTimeout(() => {
            e.target.parentElement.scrollTop = initialPosition;
        }, 0);
        e.target.dispatchEvent(new Event('change'));
        return false;
    });

</script>