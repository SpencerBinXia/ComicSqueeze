// PAST COMICS MODAL REWORK
function pastWeeklyClicked() {
    var modal = document.getElementById('past-comics');
    modal.style.display = "block";
}
function closePastWeekly() {
    var modal = document.getElementById('past-comics');
    modal.style.display = "none";
}
window.onclick =function (event) {
    var modal = document.getElementById('past-comics');
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
// NEW WEEKLY COMIC MODAL MODAL REWORK
function newWeeklyClicked() {
    var modal = document.getElementById('new-weekly');
    modal.style.display = "block";
}
function closeNewWeekly() {
    var modal = document.getElementById('new-weekly');
    modal.style.display = "none";
}
window.onclick =function (event) {
    var modal = document.getElementById('new-weekly');
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
// VOTE MODAL REWORK
function voteClicked() {
    var modal = document.getElementById('vote');
    modal.style.display = "block";
    $(".horizontal_slick").slick({
        dots: true,
        arrows: true,
        vertical: false,
        infinite: false,
        // slidesToShow: 4,
        // slidesToScroll: 4,
        rows: 2,
        slidesPerRow: 4,
        verticalSwiping: false,
    });
}
function closeVote() {
    var modal = document.getElementById('vote');
    modal.style.display = "none";
}
window.onclick =function (event) {
    var modal = document.getElementById('vote');
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

function voteForPage() {

}

$(document).ready(function(){
    // $('#votePageBtn').click(function(e) {
    //     $(".horizontal_slick").slick({
    //         dots: true,
    //         arrows: true,
    //         vertical: false,
    //         infinite: false,
    //         // slidesToShow: 4,
    //         // slidesToScroll: 4,
    //         rows: 2,
    //         slidesPerRow: 4,
    //         verticalSwiping: false,
    //     });
    // });
});