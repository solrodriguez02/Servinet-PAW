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
        <button >Pendientes</button>
        <button>Solicitudes</button>
    </div>
    <div class="appointment-container">
        <c:forEach items="${requestedAppointments}" var="appointment" varStatus="loop">
            <div class="box appointment-box" id="${loop.count}">
                <span class="appointment-field day"><c:out value="${appointment.startDate.dayOfWeek} ${appointment.startDate.dayOfMonth} ${appointment.startDate.month}"/></span>
                <span class="appointment-field time"><i class="material-icons icon">schedule</i><c:out value="${appointment.startDate.hour}:${appointment.startDate.minute}"/>
                    <c:if test="${serviceMap[appointment.serviceid].duration}>0">
                        <c:out value="${appointment.endDate.hour}:${appointment.endDate.minute}"/>
                    </c:if>
                </span>
                <a class="appointment-field service-day" href="${pageContext.request.contextPath}/servicio/${appointment.serviceid}">
                    <c:out value="${serviceMap[appointment.serviceid].name}"/>
                </a>
                <div class="decision-container">
                    <button onclick="acceptAppointment(${appointment.id},true,${loop.count})" class=" decision-btn" id="decision-btn"><i class="material-icons  accept-icon">check</i></button>
                    <button onclick="acceptAppointment(${appointment.id},false,${loop.count})" class="decision-btn"><i class="material-icons  deny-icon">add</i></button>
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
                console.error('Error al enviar la solicitud POST');
            }
            document.getElementById(componentId).style.display = "none";
        })
        .catch(error => {
            console.error('Error de red:', error);
        });
        /*
                const form = new FormData();
                form.append("accepted", accepted.toString() )

        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'multipart/form-data'
            },
            body: form
        };

*/


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


/*

$.ajax({
            url : url,
            method: 'POST',
            data : {
                accepted: true
            },
            success : function() {
                console.log('Solicitud POST enviada exitosamente');
                document.getElementById(componentId).style.display = "none";
            },
            error: function(e){
                console.log("ERROR: ", e);
            }
        });
    $(document).ready(function() {
        $('#decision-btn').click(function() {
            var url = '/java/jdk-install';
            $.getJSON(url, function(data) {
                $('title').empty();
                $('title').append(data.title);
                $("meta[name='Keywords']").attr("content", data.keywords);
                $("meta[name='Description']").attr("content", data.description);
                $('article').empty();
                $('article').append(data.content);
                console.log(data);
                console.log($("meta[name='Keywords']").attr("content"));
                console.log($("meta[name='Description']").attr("content"));
            });
            return false;
        });
    });*/
</script>