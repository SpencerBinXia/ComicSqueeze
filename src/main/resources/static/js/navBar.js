// NOTIFICATIONS MODAL REWORK
function notificationsClicked() {
    var modal = document.getElementById('notifications');
    modal.style.display = "block";
}
function closeNotifications() {
    var modal = document.getElementById('notifications');
    modal.style.display = "none";
}
window.onclick =function (event) {
    var modal = document.getElementById('notifications');
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
// LOGIN MODAL REWORK
function loginClicked() {
    var modal = document.getElementById('sign-In');
    modal.style.display = "block";
}
function closeLogin() {
    var modal = document.getElementById('sign-In');
    modal.style.display = "none";
}
window.onclick =function (event) {
    var modal = document.getElementById('sign-In');
    if (event.target == modal) {
        modal.style.display = "none";
    }
}