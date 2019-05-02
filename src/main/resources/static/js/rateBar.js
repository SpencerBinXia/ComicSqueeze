$(document).ready(function(){

//<button onclick="alert($('#limeBar').rateit('value'))">Get value</button>

    $('#limeBar').click(function() {
        var userRating = ($(this).rateit('value'));

        $.ajax({
            type : "POST",
            url : "/ratereview",
            data : {
                userRating: userRating //notice that "myArray" matches the value for @RequestParam
                //on the Java side
            },
            success : function(response) {
                location.reload();
                alert("Successfully rated!");
            },
            error : function(e) {
                alert('Error: ' + e);
            }
        })

    });
});

function rateReview()
{
    var userRating = ($('#limeBar').rateit('value'));

    $.ajax({
        type : "POST",
        url : "/ratereview",
        data : {
            userRating: userRating //notice that "myArray" matches the value for @RequestParam
            //on the Java side
        },
        success : function(response) {
        },
        error : function(e) {
            alert('Error: ' + e);
        }
    })
}