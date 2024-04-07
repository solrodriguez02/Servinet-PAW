var makeReservation = false;
const reservationBtn = document.getElementById("take-turn");
//reservationBtn.addEventListener("click", showReservationBox)
function showReservationBox(){
    makeReservation = true
    const url = new URLSearchParams(window.location.search)
    url.set("userEmail","juan@gmail.com")
    document.location.search = url
}