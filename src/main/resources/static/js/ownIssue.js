function deletePage() {
    if (confirm('Are you sure you want to delete this page?')) {
        // Delete the page
        console.log("page deleted.")
    } else {
        // Do nothing
    }

}

function deleteIssue() {
    if (confirm('Are you sure you want to delete this issue?')) {
        // Delete the page
        $.ajax({
            type: "GET",
            url: "/deleteIssue",
            cache: false,
            success: function (result) {
                //reload page
                window.location.assign("/yourprofile");
            },
            error: function (e) {
                alert("Update bio failed!");
            }
        });
    } else {
        // Do nothing
    }

}

$(document).ready(function(){
    $(".owl-carousel").owlCarousel({
        loop: false,
        nav: true
    });
});