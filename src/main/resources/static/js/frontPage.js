// PAST COMICS MODAL REWORK
function pastWeeklyClicked() {
    var modal = document.getElementById('past-comics');
    modal.style.display = "block";
    $(".horizontal_slick_past").slick({
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
    $("#topArtists").slick({
        dots: true,
        arrows: true,
        vertical: false,
        infinite: false,
        slidesToShow: 5,
        slidesToScroll: 1,
        // rows: 2,
        // slidesPerRow: 4,
        verticalSwiping: false,
    });
    $("#recentIssues").slick({
        dots: true,
        arrows: false,
        vertical: true,
        infinite: false,
        // slidesToShow: 5,
        // slidesToScroll: 1,
        rows: 2,
        slidesPerRow: 5,
        verticalSwiping: true,
    });

    $("#weeklyIssueName").keypress(function (e) {
        var regex = new RegExp("^[^$#[';\/\\]]*$");
        var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
        if (regex.test(str)) {
        } else {
            alert("Titles may not contain the following characters: $, #, [, ], \, /, ;, '");
            e.preventDefault();
            return false;
        }
    });
    $("#weeklyIssueDescription").keypress(function (e) {
        var regex = new RegExp("^[^$#[';\/\\]]*$");
        var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
        if (regex.test(str)) {
        } else {
            alert("Titles may not contain the following characters: $, #, [, ], \, /, ;, '");
            e.preventDefault();
            return false;
        }
    });

});