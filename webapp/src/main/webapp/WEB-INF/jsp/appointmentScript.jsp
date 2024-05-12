<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script src="${pageContext.request.contextPath}/js/fetch.js"></script>
<script type="text/javascript">
    const businessUrl = '${pageContext.request.contextPath}/negocio/${businessId}'
    const deleteAppointmentUrl = '${pageContext.request.contextPath}/cancelar-turno/'
    function acceptAppointment(appointmentId,accepted, componentId){

        const data = new FormData();
        data.append('accepted', accepted);

        const url = businessUrl + '/solicitud-turno/' + appointmentId;
        if ( send(url,'POST', data) === 0)
            document.getElementById(componentId).style.display = "none";
    }

    function cancelAppointment(appointmentId,componentId) {

        const url = deleteAppointmentUrl + appointmentId;

        if ( send(url,'DELETE',{}) === 0 )
            document.getElementById(componentId).style.display = "none";
    }

    function showAccordion( id, btn ) {
        btn.classList.toggle('active')
        document.getElementById(id).classList.toggle('active')
    }

    function showPopUpApp(componentId){
        document.getElementById(componentId).style.display = "block";
    }

    function closePopup(componentId) {
        document.getElementById(componentId).style.display = "none";
    }

</script>