<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="navbar.jsp" />
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/post.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <title></title>
</head>
<body>
<c:url value="/crear" var="postUrl"/>
<div class="postForm page">
    <form action="${postUrl}" method="post" class="form">

        <h3 class="form-title">Datos personales y contacto</h3>

        <label class="transparent">
            <input type="text" name="titulo" value="${title}"/>
        </label>
        <label class="transparent">
            <input type="text" name="descripcion" value="${description}"/>
        </label>
        <label class="transparent">
            <input type="text" name="ubicacion" value="${location}"/>
        </label>
        <label class="transparent">
            <input type="text" name="categoria" value="${category}"/>
        </label>


        <label>
            <p class="label">Nombre:</p>
            <input type="text" class="input" name="nombre" placeholder="Ingresa tu nombre"/>
        </label>
        <label>
            <p class="label">Apellido:</p>
            <input type="text" class="input" name="descripcion" placeholder="Ingresa tu apellido"/>
        </label>
        <label>
            <p class="label">Email:</p>
            <input type="text" class="input" name="ubicacion" placeholder="Ingresa tu mail para que tus clientes puedan contactarse"/>
        </label>

        <input type="submit" value="Publicar" class="submitBtn">

    </form>
</div>
</body>
</html>
