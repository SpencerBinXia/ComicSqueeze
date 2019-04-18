function createIssue(){
    //titleVal = document.getElementById("titleField").value;
    //descVal = document.getElementById("descField").value;
    //tagVal = document.getElementById("tagField").value;
    var issueTitleVal = $('#issueField').val();
    var issueDescVal = $('#issueDescField').val();
    var newIssue = {username: null, title: issueTitleVal, description: issueDescVal, series: null, timestamp: '2011-12-03T10:15:30', pagecount: 0};
    return $.ajax({
        type: "POST",
        url: "/createIssue",
        contentType: "application/JSON",
        dataType: "json",
        data: JSON.stringify(newIssue),
        cache: false,
        success: function (result) {
            console.log(result);
            if (result.status === "OK")
            {
                console.log("success");
                var redirectSeries = "/series/" + result.username + "/" + result.seriesTitle  + "/" + result.issueTitle;
                window.location.assign(redirectSeries);
            }
            else
            {
                alert("Create new issue failed.");
            }
        },
        error: function (e) {
            alert("Create new issue failed!");
        }
    });
}