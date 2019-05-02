function subscribe()
{

    $.ajax({
        type : "GET",
        url : "/subscribe",
        data : {
            //notice that "myArray" matches the value for @RequestParam
            //on the Java side
        },
        success : function(response) {
            location.reload();
            alert("Successfully subscribed!");
        },
        error : function(e) {
            alert('Error: ' + e);
        }
    });
}

function unsubscribe()
{

    $.ajax({
        type : "GET",
        url : "/unsubscribe",
        data : {
            //notice that "myArray" matches the value for @RequestParam
            //on the Java side
        },
        success : function(response) {
            location.reload();
            alert("Successfully unsubscribed!");
        },
        error : function(e) {
            alert('Error: ' + e);
        }
    });
}