<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="navbar.jsp" />
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/post.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <title></title>
</head>
<body>
<c:url value="/crear-perfiles" var="postUrl"/>
<div class="postForm page">
    <form action="${postUrl}" method="post" class="form">

        <h3 class="form-title">Datos personales y contacto</h3>

        <label>
            <p class="label">Nombre:</p>
            <input type="text" class="input" name="nombre" placeholder="Ingresa tu nombre"/>
        </label>
        <label>
            <p class="label">Apellido:</p>
            <input type="text" class="input" name="apellido" placeholder="Ingresa tu apellido"/>
        </label>
        <label>
            <p class="label">Email:</p>
            <input type="text" class="input" name="email" placeholder="Ingresa tu mail para que tus clientes puedan contactarse"/>
        </label>
        <label>
            <p class="label">Teléfono</p>
            <input type="text" class="input" name="telefono" placeholder="Ingresa tu teléfono para que tus clientes puedan contactarse"/>
        </label>

        <h3 class="form-title">Datos del negocio (opcionales)</h3>
        <label>
            <p class="label">Nombre del negocio:</p>
            <input type="text" class="input" name="nombre-negocio" placeholder="Ingresa tu nombre"/>
        </label>
        <label>
            <p class="label">Email del negocio:</p>
            <input type="text" class="input" name="email-negocio" placeholder="Ingresa tu mail para que tus clientes puedan contactarse"/>
        </label>
        <label>
            <p class="label">Teléfono del negocio:</p>
            <input type="text" class="input" name="telefono-negocio" placeholder="Ingresa tu teléfono para que tus clientes puedan contacarse"/>
        </label>
        <label>
            <p class="label">Dirección física del negocio:</p>
            <input type="text" class="input" name="ubicacion-negocio" placeholder="Ingresa tu teléfono para que tus clientes puedan contactarse"/>
        </label>

        <input type="submit" value="Siguiente" class="submitBtn">
    </form>
</div>
</body>
</html>