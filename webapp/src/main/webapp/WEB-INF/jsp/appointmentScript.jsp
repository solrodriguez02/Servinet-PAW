<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<script type="text/javascript">
    const businessUrl = '${pageContext.request.contextPath}/negocio/${businessId}'
    const deleteAppointmentUrl = '${pageContext.request.contextPath}/cancelar-turno/'
    function acceptAppointment(appointmentId,accepted, componentId){

        if(!accepted)
            if (!confirm("Esta seguro que desea rechazar el turno?"))
                return

        const data = new FormData();
        data.append('accepted', accepted);

        const url = businessUrl + '/solicitud-turno/' + appointmentId;
        send(url,'POST', data, componentId)
    }

    function cancelAppointment(appointmentId,componentId) {

        const url = deleteAppointmentUrl + appointmentId;
        if( !confirm("Esta seguro que desea cancelar el turno?"))
            return
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