<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<script src="${pageContext.request.contextPath}/js/fetch.js"></script>
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
        if ( send(url,'POST', data) === 0)
            document.getElementById(componentId).style.display = "none";
    }

    function cancelAppointment(appointmentId,componentId) {

        const url = deleteAppointmentUrl + appointmentId;
        if( !confirm("Esta seguro que desea cancelar el turno?"))
            return
        if ( send(url,'DELETE',{}) === 0 )
            document.getElementById(componentId).style.display = "none";
    }

    function showAccordion( id, btn ) {
        btn.classList.toggle('active')
        document.getElementById(id).classList.toggle('active')
    }

</script>