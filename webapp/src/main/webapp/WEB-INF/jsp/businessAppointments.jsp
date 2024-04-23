<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<jsp:include page="navbar.jsp" />
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/profile.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/business.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/global.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <title>Business</title>
</head>
<body>
<div class="page">
    <div class="header">
        <h2><c:out value="${business.businessName}"/></h2>
        <div>
            <a href="${pageContext.request.contextPath}/negocio/${businessId}/turnos/?confirmados=true">
                <button >Proximos</button></a>
            <a href="${pageContext.request.contextPath}/negocio/${businessId}/turnos/?confirmados=false">
                <button >Solicitudes</button></a>
        </div>
    </div>
    <div class="appointments-container">
        <c:forEach items="${appointmentList}" var="appointment" varStatus="loop">
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
                        <c:choose>
                            <c:when test="${confirmed}">
                                <button onclick="cancelAppointment(${appointment.id},${loop.count})" class="decision-btn accept-btn" ><i class="material-icons  icon cancel-icon">delete</i></button>
                            </c:when>
                            <c:otherwise>
                                <button onclick="acceptAppointment(${appointment.id},true,${loop.count})" class="decision-btn accept-btn" ><i class="material-icons  icon accept-icon">check</i></button>
                                <button onclick="acceptAppointment(${appointment.id},false,${loop.count})" class="decision-btn decision-btn"><i class="material-icons  deny-icon">add</i></button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <button class="appointment-field accordion-btn correct-btn" onclick="showAccordion(${loop.count},this)">
                        <i class="material-icons icon">arrow_drop_down</i>
                    </button>
                </div>
                <div class="accordion-container">
                    <c:if test="${confirmed}">
                        <c:set var="user" value="${userMap[appointment.userid]}"/>
                        <div class="appointment-field contact-container">
                            <span class="accordion-field"><i class="material-icons icon">account_circle</i> Solicitante:
                                <c:out value="${user.name}"/></span>
                            <span class=" accordion-field"><i class="material-icons icon">mail</i> Mail:<c:out value="${user.email}"/></span>
                        </div>
                    </c:if>
                    <span class="appointment-field accordion-field"><i class="material-icons icon">location_on</i>
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
        <c:if test="${ empty appointmentList}">
            <p>No se han encontrado resultados</p>
        </c:if>
    </div>
</div>

</body>
</html>

<script type="text/javascript">

    const businessUrl = '${pageContext.request.contextPath}/negocio/${businessId}'
    const deleteAppointmentUrl = '${pageContext.request.contextPath}/cancelar-turno/'
    function acceptAppointment(appointmentId,accepted, componentId){

        if(!accepted)
            confirm("Esta seguro que desea rechazar el turno?")

        const data = new FormData();
        data.append('accepted', accepted);

        const url = businessUrl + '/solicitud-turno/' + appointmentId;
        send(url,'POST', data, componentId)
    }

    function cancelAppointment(appointmentId,componentId) {

        const url = deleteAppointmentUrl + appointmentId;
        confirm("Esta seguro que desea cancelar el turno?")
        send(url,'DELETE',{}, componentId)
    }

    function send( url, method, data, componentId){
        fetch(url, {
            method: method,
            body: data
        })
        .then(response => {
            if (!response.ok) {
                alert("el turno ya no existe");
            } else
                document.getElementById(componentId).style.display = "none";
        })
        .catch(error => {
            console.error('Error de red:', error);
        });

    }


    function showAccordion( id, btn ) {
            btn.classList.toggle('active')
            document.getElementById(id).classList.toggle('active')
    }

</script>