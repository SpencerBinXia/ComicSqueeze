function deletePage() {
    if (confirm('Are you sure you want to delete this page?')) {
        // Delete the page
        console.log("page deleted.")
    } else {
        // Do nothing
    }

}

$(document).ready(function(){
    $(".owl-carousel").owlCarousel({
        loop: true,
        nav: true
    });
});