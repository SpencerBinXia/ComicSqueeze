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

function subscribeAll()
{

    var displayName = $('#displayInfo h2').text();

    $.ajax({
        type : "POST",
        url : "/subscribeAll",
        data : {
            displayName: displayName//notice that "myArray" matches the value for @RequestParam
            //on the Java side
        },
        success : function(response) {
            location.reload();
            alert("Subscribed to all series.");
        },
        error : function(e) {
            alert('Error: ' + e);
        }
    });

}

function unsubscribeAll()
{

    var displayName = $('#displayInfo h2').text();

    $.ajax({
        type : "POST",
        url : "/unsubscribeAll",
        data : {
            displayName: displayName//notice that "myArray" matches the value for @RequestParam
            //on the Java side
        },
        success : function(response) {
            location.reload();
            alert("Successfully unsubscribed to all series.");
        },
        error : function(e) {
            alert('Error: ' + e);
        }
    });
}