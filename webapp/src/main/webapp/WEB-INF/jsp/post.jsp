<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="navbar.jsp" />
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/post.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <title></title>
</head>
<body>
    <c:url value="/datospersonales" var="postUrl"/>
    <div class="postForm page">
        <form action="${postUrl}" method="post" class="form">

            <h3 class="form-title">Crea un nuevo servicio</h3>

            <label>
                <p class="label">Nombre:</p>
                <input type="text" class="input" name="titulo" placeholder="Elegi un nombre para tu servicio"/>
            </label>
            <label>
                <p class="label">Descripcion:</p>
                <input type="text" class="input" name="descripcion" placeholder="Describi tu servicio"/>
            </label>
            <label>
                <p class="label">Ubicacion:</p>
                <input type="text" class="input" name="ubicacion" placeholder="Ingresa la ubicacon donde brindarias el servicio"/>
            </label>
            <label>
                <p class="label">Categoria:</p>
                <select name="categoria" class="input">
                    <option>Limpieza</option>
                    <option>Belleza</option>
                    <option>Peluqueria</option>
                </select>
            </label>

            <input type="submit" value="Publicar" class="submitBtn">

        </form>
    </div>
</body>
</html>

