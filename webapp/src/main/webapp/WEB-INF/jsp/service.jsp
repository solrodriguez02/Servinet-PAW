<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
        <c:if test="${option==null}">
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
                    <div class="flex">
                        <p class="category-text"><c:out value="${service.category}"/></p>
                        <c:if test="${avgRating>0}">
                            <div class="align-right service-rate">
                                <p><c:out value="${avgRating}"/></p>
                                <i class="material-icons yellow-star service-rate-star">star</i>
                            </div>
                        </c:if>
                    </div>
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
        </c:if>

        <!--form action="${deleteUrl}" method="post">
            <input type="submit" value="Borrar servicio" class="deleteBtn"/>
        </form-->

        <c:if test="${option==null}">
            <div class="switch-btn-container align-center">
                <div class="switch-btn">
                    <button class="btn-basic btn-left btn-selected" id="toggleQuestionsButton" onclick="toggleQuestions()">Preguntas</button>
                    <button class="btn-basic btn-right" id="toggleReviewsButton" onclick="toggleReviews()">Opiniones</button>
                </div>
            </div>
        </c:if>

        <div class="rq-container">


            <c:if test="${option!='rw'}">
            <div class="rq-info" id="questions">
                <h3>Preguntas y respuestas</h3>

                <h4>Pregunta lo que queres saber</h4>
                <c:url value="/preguntar/${serviceId}" var="askUrl"/>
                <form:form action="${askUrl}" method="post" modelAttribute="questionForm">
                    <div class="flex">
                        <form:input path="question" type="text" class="input" placeholder="Escribi una pregunta"/>

                        <!-- ¡¡¡¡¡¡¡¡¡¡¡¡¡¡ ID DEL USUARIO HARDCODEADO !!!!!!!!!!!!! -->

                        <form:input path="userId" type="text" element="p" class="transparent" value="1"/>
                        <input type="submit" value="Enviar" class="send-btn">
                    </div>
                    <form:errors path="question" element="p" cssClass="error"/>
                </form:form>

                <c:choose>
                    <c:when test="${questions == null}">
                        <p class="comment">Se el primero en preguntar!</p>
                    </c:when>
                    <c:otherwise>
                        <h4>Ultimas realizadas</h4>
                        <c:forEach items="${questions}" var="qst">
                            <div class="question-box">
                                <div class="flex">
                                    <p class="text"><c:out value="${qst.question}"/></p>
                                    <p class="date"><c:out value="${qst.date}"/></p>
                                </div>
                                <c:if test="${qst.response != null}">
                                    <div class="response-container">
                                        <i class="material-icons">subdirectory_arrow_right</i>
                                        <p class="text"><c:out value="${qst.response}"/></p>
                                    </div>
                                </c:if>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
            </c:if>

            <c:if test="${option!='qst'}">
                <c:choose>
                    <c:when test="${option==null}"><div id="reviews" class="rq-info transparent"></c:when>
                    <c:otherwise><div id="reviews" class="rq-info"></c:otherwise>
                </c:choose>

                <h3>Opiniones</h3>
                <h4>Agrega tu opinion</h4>
                <c:url value="/opinar/${serviceId}" var="rateUrl"/>
                <form:form action="${rateUrl}" method="post" modelAttribute="reviewForm">

                    <!-- ¡¡¡¡¡¡¡¡¡¡¡¡¡¡ ID DEL USUARIO HARDCODEADO !!!!!!!!!!!!! -->

                    <form:input path="questionUserId" type="number" class="transparent" value="1"/>

                    <c:forEach begin="1" end="5" var="i">
                        <i class="material-icons star" onclick="selectRate(${i})">star</i>
                    </c:forEach>
                    <form:input path="rating" type="hidden" id="rating" value="0"/>
                    <div class="flex">
                        <form:input  path="comment" type="text" class="input" placeholder="Escribi una opinion"/>
                        <input type="submit" value="Enviar" class="send-btn">
                    </div>
                    <form:errors path="rating" element="p" cssClass="error"/>
                </form:form>

                <c:choose>
                    <c:when test="${reviews == null}">
                        <p class="comment">Se el primero en opinar!</p>
                    </c:when>
                    <c:otherwise>
                        <h4>Ultimas realizadas</h4>
                        <c:forEach items="${reviews}" var="review">
                            <c:set value="${review.rating}" var="rate"/>
                            <div class="question-box">
                                <div class="flex">
                                    <div class="stars-container">
                                        <c:forEach begin="1" end="${rate}" var="i">
                                            <i class="material-icons yellow-star">star</i>
                                        </c:forEach>
                                        <c:forEach begin="${rate+1}" end="5" var="i">
                                            <i class="material-icons gray-star">star</i>
                                        </c:forEach>
                                    </div>
                                    <p class="date"><c:out value="${review.date}"/></p>
                                </div>
                                <p class="text"><c:out value="${review.comment}"/></p>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>

            </div>
            </c:if>
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

        function selectRate(rating) {
            // Establecer el valor del input oculto con el puntaje seleccionado
            document.getElementById("rating").value = rating;
            // Cambiar el color de las estrellas seleccionadas
            var star = document.getElementsByClassName("star");
            for (var i = 0; i < star.length; i++) {
                if (i < rating) {
                    star[i].style.color = "#ffd528";
                } else {
                    star[i].style.color = "#b9b9b9";
                }
            }
        }
    </script>
</body>
</html>