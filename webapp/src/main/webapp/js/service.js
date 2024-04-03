var makeReservation = false;

function showReservationBox(){
    makeReservation = true
    document.getElementById("take-turn-modal").showModal()
}

const reservationBtn = document.getElementById("take-turn");
reservationBtn.addEventListener("click", showReservationBox())
