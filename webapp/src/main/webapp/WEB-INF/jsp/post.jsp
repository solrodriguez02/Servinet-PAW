<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="navbar.jsp" />
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/post.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <title>Service Creation</title>

</head>
<body>
<c:url value="/crearservicio" var="postUrl"/>
<div class="postForm page">
    <form action="${postUrl}" method="post" class="form" enctype="multipart/form-data">

        <h3 class="form-title">Crea un nuevo servicio</h3>
        <label>
            Nombre:
            <input type="text" class="input" name="titulo" placeholder="Elegi un nombre para tu servicio"/>
        </label>
      <div class="service-div">
        <label>
            Selecciona una imagen para tu servicio:
            <input type="file" class="input" name="imageInput" accept=".png, .jpg, .jpeg" required/>
        </label>
        </div>
        <label>
            Descripcion:
            <input type="text" class="input" name="descripcion" placeholder="Describi tu servicio"/>
        </label>

        <label id="label">
                Ubicacion:
            <label>
                <input type="checkbox" id="homeservice" name="homeserv" />
                <label for="homeservice">Servicio a domicilio</label>
            </label>
            <div class="service-div">
                <select name="neighbourhood" class="input" >
                    <c:forEach items="${neighbours}" var="item">
                        <option value="${item}">${item.value}</option>
                    </c:forEach>
                </select>
                <input type="text" class="input" name="ubicacion" value="" id="locationInput" placeholder="Ingresa la ubicacion donde brindarias el servicio"/>
            </div>
        </label>

        <label  >
            <p class="label">Precio:
            <label>
                <input type="checkbox" id="additionalCharges" name="additionalCharges" />
                <label for="additionalCharges">Puede incluir cargos addicionales</label>
            </label>
                <div class="service-div">
            <select name="pricingtype" class="input" id="priceType">
                <c:forEach items="${pricingTypes}" var="item">
                    <option  value="${item}">${item.value}</option>
                </c:forEach>
            </select>
            <input type="text" class="input" name="precio" id="priceTag" placeholder="Ingresa el precio de tu servicio"/>
            </div>
            </p>
        </label>

        <label>
            <p class="label">Categoria:</p>
            <select name="categoria" class="input">
                <c:forEach items="${categories}" var="item">
                <option value="${item}">${item.value}</option>
                </c:forEach>
            </select>
        </label>
        <label>
            <p class="label">Duracion minima del servicio (en minutos):</p>
            <input type="number" class="input" name="minimalduration" />
        </label>
        <input type="submit" value="Publicar" class="submitBtn">
    </form>
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



</script>