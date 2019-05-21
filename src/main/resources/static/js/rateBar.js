$(document).ready(function(){

//<button onclick="alert($('#limeBar').rateit('value'))">Get value</button>

    $('#limeBar').click(function() {
        var userRating = ($(this).rateit('value'));

        $.ajax({
            type : "POST",
            url : "/ratereview",
            data : {
                userRating: userRating,
                userReview: ""//notice that "myArray" matches the value for @RequestParam
                //on the Java side
            },
            success : function(response) {
                //location.reload();
                console.log("rated");
            },
            error : function(e) {
                alert('Error: ' + e);
            }
        })

    });
});

function rateReview()
{
    var userRating = ($('#reviewBar').rateit('value'));
    var userReview = $("#reviewField").val();
    console.log(userReview);

    $.ajax({
        type : "POST",
        url : "/ratereview",
        data : {
            userRating: userRating,
            userReview: userReview//notice that "myArray" matches the value for @RequestParam
            //on the Java side
        },
        success : function(response) {
            location.reload();
            alert("Successfully reviewed!");
        },
        error : function(e) {
            alert('Error: ' + e);
        }
    })
}