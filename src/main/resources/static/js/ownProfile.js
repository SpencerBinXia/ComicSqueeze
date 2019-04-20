var tagList = []; // contains tags, exists until hitting "close" or "save" (save to be implemented through updateBio())

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
            // reset create series modal
            resetSeriesForm();
            //reload page
            window.location.assign("yourprofile");
        },
        error: function (e) {
            alert("Update bio failed!");
        }
    });
}

function deleteTag(tag) {
    // occurs when clicking a newly created tag in the create series popup.
    var tagText = tag.innerText;
    console.log("delete tag: " + tagText);
    for( var i = 0; i < tagList.length; i++){
        if ( tagList[i] == tagText) {
            tagList.splice(i, 1);
            i--;
        }
    }
    console.log("tags list: " +tagList);
    var btn = document.getElementById(tag.id);
    btn.parentNode.removeChild(btn);
}
function addTag(tag) {
    // occurs when clicking the add tag button after inputting the tag name.
    console.log("add tag: " +tag);
    document.getElementById("tagField").value = "";
    if(tagList.includes(tag)){
        console.log("tags list: " +tagList);
        return 0;
    } else {
        tagList.push(tag);
        console.log("tags list: " +tagList);
        return 1;
    }
}
function resetSeriesForm() {
    document.getElementById("seriesForm").reset();
    var buttons = document.getElementById("tagsForm");
    while (buttons.firstChild) {
        buttons.removeChild(buttons.firstChild);
    }
    tagList.length = 0; // resets the tag list since the form is closed.
}

function createSeries(){
    var titleVal = $('#titleField').val();
    var descVal = $('#descField').val();
    var tagVal = $('#tagField').val();

    var errors = "";
    if(titleVal == "" || titleVal == null){
        errors += "title ";
    }
    if(descVal == "" || descVal == null){
        errors += "description ";
    }
    if(tagVal == "" || tagVal == null){
        errors += "tags ";
    }
    if(errors != "") {
        alert("Make sure the ( " + errors + ") field(s) have content.")
        return false;
    }

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
                var redirectIssue = "/series/" + result.username + "/" + result.seriesTitle;
                window.location.assign(redirectIssue);
            }
            else
            {
                alert("Series title already in use on your account.");
            }
        },
        error: function (e) {
            alert("Create series failed!");
        }
    });
}

$(document).ready(function(){
    $(".owl-carousel").owlCarousel({
        loop: false,
        nav: true
    });
});