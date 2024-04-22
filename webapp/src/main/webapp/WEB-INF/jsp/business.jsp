<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="navbar.jsp" />
<html>
<head>

    <link href="${pageContext.request.contextPath}/css/business.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <title>Business</title>
</head>
<body>
<div class="page">
    <h2><c:out value="${business.businessName}"/></h2>
    <div class="boxes-container">
        <c:forEach items="${serviceMap}" var="service" varStatus="loop">
                <a class="none-decoration" href="${pageContext.request.contextPath}/servicio/${service.key}">
                    <div class="box service-box">
                        <p class="box-text"><c:out value="${service.value.name}"/></p>
                    </div>
                </a>
        </c:forEach>
    </div>
    <div>
        <button >Proximos</button>
        <button>Solicitudes</button>
    </div>
    <div class="appointments-container">
        <c:forEach items="${requestedAppointments}" var="appointment" varStatus="loop">
            <div class="appointment-container" id="${loop.count}">
                <div class="box appointment-box">
                    <span class="appointment-field day"><c:out value="${appointment.startDateString}"/></span>
                    <span class="appointment-field time"><i class="material-icons icon">schedule</i>    <c:out value="${appointment.startDateTimeString}"/>
                        <c:if test="${serviceMap[appointment.serviceid].duration}>0">
                            <c:out value="${appointment.endDateTimeString}"/>
                        </c:if>
                    </span>
                    <a class="appointment-field service-name" href="${pageContext.request.contextPath}/servicio/${appointment.serviceid}">
                        <c:out value="${serviceMap[appointment.serviceid].name}"/>
                    </a>
                    <span class="appointment-field id"> #<c:out value="${appointment.id}"/></span>
                    <div class="decision-container appointment-field correct-btn">
                        <button onclick="acceptAppointment(${appointment.id},true,${loop.count})" class="decision-btn accept-btn" ><i class="material-icons  icon accept-icon">check</i></button>
                        <button onclick="acceptAppointment(${appointment.id},false,${loop.count})" class="decision-btn decision-btn"><i class="material-icons  deny-icon">add</i></button>
                    </div>
                    <button class="appointment-field accordion-btn correct-btn" onclick="showAccordion(${loop.count},this)">
                        <i class="material-icons icon">arrow_drop_down</i>
                    </button>
                </div>
                <div class="accordion-container">
                    <span class="appointment-field location"><i class="material-icons icon">location_on</i>
                        Ubicacion:
                        <c:choose>
                        <c:when test="${serviceMap[appointment.serviceid].homeService}" >
                            <c:out value="${appointment.location}"/></c:when>
                        <c:otherwise>La del servicio</c:otherwise>
                        </c:choose>
                    </span>
                </div>
            </div>
        </c:forEach>
        <c:if test="${requestedAppointments.size()}==0">
            <p>No se han encontrado solicitudes</p>
        </c:if>
    </div>
</div>

</body>
</html>

<script type="text/javascript">


    const businessUrl = '${pageContext.request.contextPath}/negocio/${businessId}'
    function acceptAppointment(appointmentId,accepted, componentId){

        const data = new FormData();
        data.append('accepted', accepted);

     //   console.log(data)
        const url = businessUrl+'/solicitud-turno/'+appointmentId ;

        fetch(url, {
            method: 'POST',
            body: data
        })
        .then(response => {
            if (response.ok) {
                console.log('Solicitud POST enviada exitosamente');
            } else {
                alert("el turno ya no existe");
            }
            document.getElementById(componentId).style.display = "none";
        })
        .catch(error => {
            console.error('Error de red:', error);
        });
/*
      console.log(form)
        fetch(url, options)
            .then(response => {
                if (response.ok) {
                    console.log('Solicitud POST enviada exitosamente');
                } else {
                    console.error('Error al enviar la solicitud POST');
                }
            })
            .catch(error => {
                console.error('Error de red:', error);
            });

        document.getElementById(componentId).style.display = "none";
*/

    }


    function showAccordion( id, btn ) {
            btn.classList.toggle('active')
            document.getElementById(id).classList.toggle('active')
    }

</script>