<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="navbar.jsp" />
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/home.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <title></title>
</head>
<body>
<div class="page">

    <div class="services-container">
        <c:forEach items="${services}" var="item">
        <div class="service-box">
            <a class="service-text" href="${pageContext.request.contextPath}/${item.id}">
                <div class="service-data-container">
                    <div class="service-img-container">
                        <img class="img service-img" src="https://goldbricksgroup.com/wp-content/uploads/2021/08/y9DpT-600x390.jpg" alt="Imagen del servicio">
                    </div>
                    <div class="service-info">
                        <h3>${item.name} ID ${item.id}</h3>
                        <p>${item.location}</p>
                        <p>${item.description}</p>
                    </div>
                </div>
            </a>
        </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
