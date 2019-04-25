var newTagList = [];

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
        alert("Make sure the ( " + errors + ") field(s) have content.");
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
    // var buttons = document.getElementById("editTagsForm");
    // while (buttons.firstChild) {
    //     buttons.removeChild(buttons.firstChild);
    // }
    // console.log("RESET");
    newTagList.length = 0; // resets the tag list since the form is closed.
}
function addTag() {
    var tag = document.getElementById("addInput").value;
    console.log("add tag: " + tag);
    document.getElementById("addInput").value = "";
    if(newTagList.includes(tag)){
        console.log("duplicate");
        alert("Cannot have duplicate tags.")
        return false;
    } else {
        newTagList.push(tag);
        console.log("tags list: " + newTagList);
    }
    document.getElementById("seriesTags").value = newTagList.join(", ");
}
function deleteTag() {
    var tag = document.getElementById("deleteInput").value;
    console.log("delete tag: " + tag);
    document.getElementById("deleteInput").value = "";
    for( var i = 0; i < newTagList.length; i++){
        if ( newTagList[i] == tag) {
            newTagList.splice(i, 1);
            i--;
            console.log("tags list: " + newTagList);
            document.getElementById("seriesTags").value = newTagList.join(", ");
            return;
        }
    }
}

// function populateEditSeries() {
//     var tags = document.getElementById("curTags").innerText;
//     var tagsArray = tags.split(', ');
//     console.log(tagsArray);
//     var form = document.getElementById("editTagsForm");
//     for (var i = 0; i < tagsArray.length; i++){
//         var tagButton = document.createElement("button");
//         newTagList.push(tagsArray[i]);
//         tagButton.id = 'oldTag' + i;
//         tagButton.innerText = tagsArray[i];
//         tagButton.classList.add("tagBtn");
//         tagButton.addEventListener('click', function(){
//             deleteEditTag(tagButton);
//         });
//         form.appendChild(tagButton);
//     }
// }

// function deleteEditTag(tag) {
//     // occurs when clicking a newly created tag in the create series popup.
//     var tagText = tag.innerText;
//     console.log("delete tag: " + tagText);
//     for( var i = 0; i < newTagList.length; i++){
//         if ( newTagList[i] == tagText) {
//             newTagList.splice(i, 1);
//             i--;
//         }
//     }
//     console.log("tags list: " +newTagList);
//     var btn = document.getElementById(tag.id);
//     console.log(tag.id);
//     // document.getElementById("editTagsForm").remove(btn);
//     btn.remove();
// }
// function addEditTag(tag) {
//     // occurs when clicking the add tag button after inputting the tag name.
//     console.log("add tag: " +tag);
//     document.getElementById("editTagField").value = "";
//     if(newTagList.includes(tag)){
//         console.log("tags list: " +newTagList);
//         return 0;
//     } else {
//         newTagList.push(tag);
//         console.log("tags list: " +newTagList);
//         return 1;
//     }
// }

function popEditSeries() {
    var newTags = document.getElementById("curTags").innerText;
    console.log(newTags);
    newTagList = newTags.split(', ');
    console.log(newTagList);
    document.getElementById("seriesTags").value = newTags;
}

function editSeries(){
    var descVal = $('#seriesDescField').val();
    console.log("Desc:" + descVal);
    // var tagListString = newTagList.join(", "); // creates comma separated string of tags
    var tagsVal = $('#seriesTags').val();
    console.log("Tags:" + tagsVal);

    $('#descID').text(descVal);
    $('#curTags').text(tagsVal);

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

    /**
    $("#limeBar").rate({
        selected_symbol_type: 'image',
        max_value: 5,
        step_size: 1,
        symbols: {
            image: {
                base: '<div style="background-image: url(/../images/icons/lime_BW_icon.png);" class="im2">&nbsp;</div>',
                hover: '<div style="background-image: url(/../images/icons/lime_COLOR_icon.png);" class="im2">&nbsp;</div>',
                selected: '<div style="background-image: url(/../images/icons/lime_COLOR_icon.png);" class="im2">&nbsp;</div>'
            },
        }
    });
     **/
});