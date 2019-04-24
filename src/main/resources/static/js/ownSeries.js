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

function resetEditSeriesForm() {
    document.getElementById("editSeriesForm").reset();
}
function editSeries(){
    var descVal = $('#descField').val();
    var tagListString = tagList.join(", "); // creates comma separated string of tags

    //var edittedSeries = {username: null, collaborative: false, creators: null, description: descVal, rating: 0, title: titleVal, tags: tagListString, timestamp: '2011-12-03T10:15:30', views: 0, weekly: false, flag: false};
    //Check if there were any changes even made
    //If no changes made, then save changes button does not do anything
    //If changes made, then update the series being edited, with the values that were changed
    //At the moment, if edit series clicked, and a tag entered, it does not check the tag against the existing tags
    //However, a new tag list is created for each edit series "session", so it does detect duplicate tags entered within the same session

}
function deleteSeries() {
    if (confirm('Are you sure you want to delete this series?')) {
        // Delete the page
        $.ajax({
            type: "GET",
            url: "/deleteSeries",
            cache: false,
            success: function (result) {
                //reload page
                window.location.assign("/yourprofile");
            },
            error: function (e) {
                alert("Delete series failed!");
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