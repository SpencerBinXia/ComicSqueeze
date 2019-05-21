var newTagList = [];
var newCollabList = [];

// WRITE REVIEW MODAL REWORK
function writeReviewClicked() {
    var modal = document.getElementById('write-review');
    modal.style.display = "block";
}
function closeWriteReview() {
    var modal = document.getElementById('write-review');
    modal.style.display = "none";
}
window.onclick =function (event) {
    var modal = document.getElementById('write-review');
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
// WRITE REVIEW MODAL REWORK
function flagSeriesClicked() {
    var modal = document.getElementById('flag');
    modal.style.display = "block";
}
function closeFlagSeries() {
    var modal = document.getElementById('flag');
    modal.style.display = "none";
}
window.onclick =function (event) {
    var modal = document.getElementById('flag');
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

// ADD ISSUE MODAL REWORK
function addIssueClicked() {
    var modal = document.getElementById('add-issue');
    modal.style.display = "block";
}
function closeAddIssue() {
    var modal = document.getElementById('add-issue');
    modal.style.display = "none";
    resetIssueForm();
}
window.onclick =function (event) {
    var modal = document.getElementById('add-issue');
    if (event.target == modal) {
        modal.style.display = "none";
        resetIssueForm();
    }
}
// EDIT SERIES MODAL REWORK
function editSeriesClicked() {
    var modal = document.getElementById('edit-series');
    modal.style.display = "block";
    popEditSeries();
}
function closeEditSeries() {
    var modal = document.getElementById('edit-series');
    modal.style.display = "none";
    resetEditSeriesForm();
}
window.onclick =function (event) {
    var modal = document.getElementById('edit-series');
    if (event.target == modal) {
        modal.style.display = "none";
        resetEditSeriesForm();
    }
}

function flagSeries() {
    //var modal = document.getElementById('flag');
    //var body = document.getElementById('flagReason');
    var body = $('#flagReason');
    console.log(body.val());
    console.log(curSeriesTitle);
    console.log(curSeriesUser);

    $.ajax({
        type: "GET",
        data: {
            reportBody: body.val(),
            reportSeriesTitle: curSeriesTitle,
            reportSeriesUser: curSeriesUser,
            link: "series/" + curSeriesUser + "/" + curSeriesTitle,
            type: "report",
        },
        url: "/reportSeries",
        cache: false,
        success: function (result) {
            console.log(result);
            console.log("success report");

        },
        error: function(e){
            console.log("Report failed");
        }
    });


}

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
    newCollabList.length = 0;
}
function addTag() {
    var tag = document.getElementById("addInput").value;
    console.log("add tag: " + tag);
    document.getElementById("addInput").value = "";
    if(newTagList.includes(tag)){
        console.log("duplicate");
        alert("Cannot have duplicate tags.")
        return false;
    }
    else if (tag == "" || tag == null) {
        alert("Tag cannot be empty.")
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

function addCollab() {
    var collab = document.getElementById("addCollabInput").value;
    console.log("add collab: " + collab);
    document.getElementById("addCollabInput").value = "";
    if(newCollabList.includes(collab)){
        console.log("duplicate");
        alert("Cannot have duplicate collaborators.");
        return false;
    }
    else if (collab == "" || collab == null) {
        alert("Invited collaborator cannot be empty.");
        return false;
    } else {
                $.ajax({
                    type : "POST",
                    url : "/findInvitedUser",
                    dataType: "text",
                    data : {
                        invitedUser: collab //notice that "myArray" matches the value for @RequestParam
                        //on the Java side
                    },
                    success : function(response) {
                        console.log(response);
                        if (response == "OK")
                        {
                            newCollabList.push(collab);
                            console.log("collab list: " + newCollabList);
                            document.getElementById("seriesCreators").value = newCollabList.join(",");
                        }
                        else
                        {
                            alert("This is not a valid user.");
                            return 0;
                        }
                    },
                    error : function(e) {
                        console.log(e);
                        alert('Error: AJAX failed');
                        return 0;
                    }
                });
            }
}

function deleteCollab() {
    var collab = document.getElementById("deleteCollabInput").value;
    console.log("delete collab: " + collab);
    document.getElementById("deleteCollabInput").value = "";
    for( var i = 0; i < newCollabList.length; i++){
        if ( newCollabList[i] == collab) {
            newCollabList.splice(i, 1);
            i--;
            console.log("collab list: " + newCollabList);
            document.getElementById("seriesCreators").value = newCollabList.join(",");
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
    var newCollabs = document.getElementById("curCreators").innerText;
    console.log(newTags);
    console.log(newCollabs);
    newTagList = newTags.split(', ');
    newCollabList = newCollabs.split(',');
    console.log(newTagList);
    console.log(newCollabList);
    document.getElementById("seriesTags").value = newTags;
    document.getElementById("seriesCreators").value = newCollabs;
}

function editSeries(){
    var descVal = $('#seriesDescField').val();
    console.log("Desc:" + descVal);
    // var tagListString = newTagList.join(", "); // creates comma separated string of tags
    var tagsVal = $('#seriesTags').val();
    tagsVal = tagsVal.replace(/\s*,\s*/g, ",");
    console.log("Tags:" + tagsVal);
    var collabVal = $('#seriesCreators').val();
    if (collabVal != undefined && collabVal != null) {
        collabVal = collabVal.replace(/\s*,\s*/g, ",");
    }
    else
    {
        collabVal = "default";
    }

    //const description = $('#descID').text(descVal);
    //const tags = $('#curTags').text(tagsVal);
    //console.log(description)
    //console.log(tags)
    $.ajax({
        type: "GET",
        //url: "/editSeries?description=?" + descVal + "?tags=" + tagsVal,
        url: "/editSeries",
        data:{
            description: descVal,
            tags: tagsVal,
            creators: collabVal
        },
        cache: false,
        success: function (result) {
            //reload page
            location.reload();
            //window.location.assign("/series/" + result.username + "/" + result.seriesTitle);
        },
        error: function (e) {
            alert("Update series failed!");
        }
    });

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
            data:{
                seriesOwner: curSeriesUser,
                seriesTitle: curSeriesTitle
            },
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
    $("#addInput").keypress(function(e){
        var regex = new RegExp("^[a-zA-Z0-9]+$");
        var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
        if (regex.test(str)) {
        } else {
            alert("Tags may only contain alphanumeric characters.");
            e.preventDefault();
            return false;
        }
    });
    $("#deleteInput").keypress(function(e){
        var regex = new RegExp("^[a-zA-Z0-9]+$");
        var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
        if (regex.test(str)) {
        } else {
            alert("Tags may only contain alphanumeric characters.");
            e.preventDefault();
            return false;
        }
    });
    $(".horizontal_slick").slick({
        dots: true,
        arrows: true,
        vertical: false,
        infinite: false,
        slidesToShow: 4,
        slidesToScroll: 4,
        // rows: 1,
        // slidesPerRow: 4,
        verticalSwiping: false,
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