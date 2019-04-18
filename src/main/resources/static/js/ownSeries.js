function createIssue(){
    var issueTitleVal = $('#issueField').val();
    var issueDescVal = $('#issueDescField').val();

    var errors = "";
    if(issueTitleVal == "" || issueTitleVal == null){
        errors += "title ";
    }
    if(issueDescVal == "" || issueDescVal == null){
        errors += "description ";
    }
    if(errors != "") {
        alert("Make sure the ( " + errors + ") field(s) have content.")
        return false;
    }

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
                var redirectSeries = "/series/" + result.username + "/" + result.seriesTitle;
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

function resetIssueForm() {
    document.getElementById("issueForm").reset();
}

$(document).ready(function(){
    $(".owl-carousel").owlCarousel({
        loop: true,
        nav: true
    });
});