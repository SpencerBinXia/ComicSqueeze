function updateBio(){
    val = document.getElementById("bioField").value;
    console.log(val);
    $('#bioID').text(val);
    return $.ajax({
        type: "GET",
        url: "/updateBio?bio=" + val,
        cache: false,
        success: function (result) {
            $('#bioID').text(val);
            //reload page
            window.location.assign("yourprofile");
        },
        error: function (e) {
            alert("Update bio failed!");
        }
    });
}

function createSeries(){
    //titleVal = document.getElementById("titleField").value;
    //descVal = document.getElementById("descField").value;
    //tagVal = document.getElementById("tagField").value;
    var titleVal = $('#titleField').val();
    var descVal = $('#descField').val();
    var tagVal = $('#tagField').val();
    console.log(titleVal);
    console.log(descVal);
    console.log(tagVal);
    var newSeries = {username: null, collaborative: false, creators: null, description: descVal, rating: 0, title: titleVal, tags: tagVal, timestamp: '2011-12-03T10:15:30', views: 0, weekly: false, flag: false};
    return $.ajax({
        type: "POST",
        url: "/createSeries",
        contentType: "application/JSON",
        dataType: "json",
        data: JSON.stringify(newSeries),
        cache: false,
        success: function (result) {
            console.log(result);
            if (result.status === "OK")
            {
                console.log("success");
                var redirectSeries = "/series/" + result.username + "/" + result.seriesTitle;
                window.location.assign(redirectSeries);
            }
            else
            {
                alert("Create new series failed.");
            }
        },
        error: function (e) {
            alert("Create series failed!");
        }
    });
}