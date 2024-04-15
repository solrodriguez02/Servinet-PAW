<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="navbar.jsp" />
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/service.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <title></title>
</head>
<c:url var="deleteUrl" value="/${service.id}/eliminar-servicio" />
<c:url value="/contratar-servicio/${serviceId}" var="contratarUrl"/>
<body>
    <div class="page">
        <h2><c:out value="${service.name}"/></h2>

        <!--tal vez para un primer sprint se puede seleccionar cualquier fecha y horario
        (no se muestran los turnos que ya estan tomados) y una vez hecho el input se
         muestra un mensaje de error si es que esa fecha y hora ya fue tomada. -->

        <div class="info-container">
            <div class="img-container">
                <img class="service-img img" src="${pageContext.request.contextPath}/images/${service.imageId}" alt="Imagen del servicio">
            </div>
            <div class="info-box">
                <div class="info-text">

                    <p class="category-text"><c:out value="${service.category}"/></p>
                    <p class="text-with-icon"> <i class="material-icons icon">location_on</i><c:out value="${service.location}"/></p>
                    <p class="text-with-icon"> <i class="material-icons icon">house</i>
                        <c:choose>
                            <c:when test="${service.homeService}">
                                A domicilio
                            </c:when>
                            <c:otherwise>
                                En el domicilio del profesional
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <p class="text-with-icon"> <i class="material-icons icon">schedule</i>
                        <c:choose>
                            <c:when test="${service.duration < 60}">
                                <c:out value="${service.duration}"/>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${service.duration / 60}"/>h
                                <c:if test="${service.duration % 60 != 0}">
                                    <c:out value="${service.duration % 60}"/>min
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <p class="text-with-icon"><i class="material-icons icon">attach_money</i><c:out value="${service.price}"/>
                        <label class="comment">${service.pricing}</label>
                    </p>
                    <c:if test="${service.additionalCharges}">
                        <p class="text-with-icon"><i class="material-icons icon">warning</i>Puede incluir costos adicionales</p>
                    </c:if>

                    <div class="btn-container">
                        <button class="btn">
                            <a href="${contratarUrl}" class="none-decoration btn-text">Sacar turno</a>
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <p class="text-description"><c:out value="${service.description}"/></p>
        <!--form action="${deleteUrl}" method="post">
            <input type="submit" value="Borrar servicio" class="deleteBtn"/>
        </form-->

        <div class="switch-btn-container align-center">
            <div class="switch-btn">
                <button class="btn-basic btn-left btn-selected" id="toggleQuestionsButton" onclick="toggleQuestions()">Preguntas</button>
                <button class="btn-basic btn-right" id="toggleReviewsButton" onclick="toggleReviews()">Opiniones</button>
            </div>
        </div>

        <div class="rq-container">
            <div class="rq-info" id="questions">
                <h3>Preguntas y respuestas</h3>

                <h4>Pregunta lo que queres saber</h4>
                <c:url value="" var="askUrl"/>
                <form action="${askUrl}" method="post" class="post-question">
                    <input type="text" class="input" name="pregunta" placeholder="Escribi una pregunta"/>
                    <input type="submit" value="Enviar" class="send-btn">
                </form>

                <h4>Ultimas realizadas</h4>
                <div class="question-box">
                    <p class="text">esto es una pregunta</p>
                    <div class="response-container">
                        <i class="material-icons">subdirectory_arrow_right</i>
                        <p class="text">esto es una rta</p>
                    </div>
                </div>
            </div>

            <div class="rq-info transparent" id="reviews">
                <h3>Opiniones</h3>
                <h4>Agrega tu opinion</h4>
                <c:url value="" var="rateUrl"/>
                <form action="${rateUrl}" method="post" class="post-question">
                    <input type="text" class="input" name="pregunta" placeholder="Escribi una reseña"/>
                    <input type="submit" value="Enviar" class="send-btn">
                </form>

                <h4>Ultimas realizadas</h4>
                <c:set value="5" var="rate"/>
                <div class="stars-container">
                    <c:forEach begin="1" end="${rate}" var="i">
                        <i class="material-icons yellow-star">star</i>
                    </c:forEach>
                    <c:forEach begin="${rate+1}" end="5" var="i">
                        <i class="material-icons gray-star">star</i>
                    </c:forEach>
                </div>
                <p class="text">Esto es un comentario</p>
            </div>
        </div>

    </div>

    <script>
        function toggleQuestions() {
            var showQuestions = document.getElementById('questions');
            var showReviews = document.getElementById('reviews');
            var toggleQuestionsButton = document.getElementById('toggleQuestionsButton');
            var toggleReviewsButton = document.getElementById('toggleReviewsButton');

            if (showQuestions.classList.contains('transparent')) {
                showQuestions.classList.remove('transparent');
                showReviews.classList.add('transparent');
                toggleQuestionsButton.classList.add('btn-selected');
                toggleReviewsButton.classList.remove('btn-selected');
            }
        }

        function toggleReviews() {
            var showQuestions = document.getElementById('questions');
            var showReviews = document.getElementById('reviews');
            var toggleQuestionsButton = document.getElementById('toggleQuestionsButton');
            var toggleReviewsButton = document.getElementById('toggleReviewsButton');

            if (showReviews.classList.contains('transparent')) {
                showReviews.classList.remove('transparent');
                showQuestions.classList.add('transparent');
                toggleReviewsButton.classList.add('btn-selected');
                toggleQuestionsButton.classList.remove('btn-selected');
            }
        }
    </script>
</body>
</html>