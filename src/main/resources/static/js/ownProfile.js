var tagList = []; // contains tags, exists until hitting "close" or "save" (save to be implemented through updateBio())
var inviteList = [];
var inviteCounter = 0;

function updateBio(){
    var bio = document.getElementById("bioField").value;
    console.log(bio);
    var newBio = bio.replace(/\n\r?/g, '%0D%0A');
    return $.ajax({
        type: "GET",
        url: "/updateBio?bio=" + newBio,
        cache: false,
        success: function (result) {
            // $('#bioID').text(newBio);
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

function deleteInvite(invite) {
    // occurs when clicking a newly created tag in the create series popup.
    var inviteText = invite.innerText;
    console.log("delete invite: " + inviteText);
    for( var i = 0; i < inviteList.length; i++){
        if ( inviteList[i] == inviteText) {
            inviteList.splice(i, 1);
            i--;
        }
    }
    console.log("invite list: " + inviteList);
    var btn = document.getElementById(invite.id);
    btn.parentNode.removeChild(btn);
}

function addInvite(invite) {
    // occurs when clicking the add tag button after inputting the tag name.
    console.log("add invite: " + invite);
    document.getElementById("inviteField").value = "";
    if(inviteList.includes(invite)){
        console.log("invite list: " +inviteList);
        return 0;
    } else {
        inviteList.push(invite);
        console.log("invite list: " + inviteList);
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
    var collabVal = $('#seriesMode').val();
    var collabBool = false;
    console.log(descVal);
    console.log(collabVal);


    var errors = "";
    if(collabVal == "group")
    {
        collabBool = true;
    }
    if(titleVal == "" || titleVal == null){
        errors += "title ";
    }
    if(descVal == "" || descVal == null){
        errors += "description ";
    }
    if(errors != "") {
        alert("Make sure the ( " + errors + ") field(s) have content.")
        return false;
    }

    var tagListString = tagList.join(","); // creates comma separated string of tags
    tagListString = tagListString.replace(/\s*,\s*/g, ",");

    if (collabBool == true)
    {
        var inviteListString = inviteList.join(","); // creates comma separated string of invites
        inviteListString = inviteListString.replace(/\s*,\s*/g, ",");
    }
    else
    {
        inviteListString = "default";
    }

    console.log(titleVal);
    console.log(descVal);
    console.log(tagListString);
    var newSeries = {username: null, collaborative: collabBool, creators: inviteListString, description: descVal, rating: 0, title: titleVal, tags: tagListString, timestamp: '2011-12-03T10:15:30', views: 0, weekly: false, flag: false, rateCounter: 0};
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

// FOLLOWING MODAL REWORK
function followingClicked() {
    var modal = document.getElementById('subscriptions');
    modal.style.display = "block";
    // here so slick calculates heights at the right time
    $(".subscriptions_slick").slick({
        dots: true,
        arrows: true,
        vertical: false,
        infinite: false,
        // slidesToShow: 4,
        // slidesToScroll: 4,
        rows: 2,
        slidesPerRow: 5,
        verticalSwiping: false,
    });
    $(".collaborative_slick").slick({
        dots: true,
        arrows: true,
        vertical: false,
        infinite: false,
        // slidesToShow: 4,
        // slidesToScroll: 4,
        rows: 2,
        slidesPerRow: 5,
        verticalSwiping: false,
    });
}
function closeFollowing() {
    var modal = document.getElementById('subscriptions');
    modal.style.display = "none";
}
window.onclick =function (event) {
    var modal = document.getElementById('subscriptions');
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
// EDIT-PROFILE MODAL REWORK
function editProfileClicked() {
    var modal = document.getElementById('edit-profile');
    modal.style.display = "block";
    $("textarea#bioField").val(document.getElementById('bioID').innerText);
}
function closeEditProfile() {
    var modal = document.getElementById('edit-profile');
    modal.style.display = "none";
}
window.onclick =function (event) {
    var modal = document.getElementById('edit-profile');
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
// CREATE SERIES MODAL REWORK
function createSeriesClicked() {
    var modal = document.getElementById('create-series');
    modal.style.display = "block";
}
function closeCreateSeries() {
    var modal = document.getElementById('create-series');
    modal.style.display = "none";
    resetSeriesForm();
}
window.onclick =function (event) {
    var modal = document.getElementById('create-series');
    if (event.target == modal) {
        modal.style.display = "none";
        resetSeriesForm();
    }
}

// CREATE SERIES MODAL REWORK
function collabSeriesClicked() {
    var modal = document.getElementById('collabSeries');
    modal.style.display = "block";
}
function closeCollabSeries() {
    var modal = document.getElementById('collabSeries');
    modal.style.display = "none";
}
window.onclick =function (event) {
    var modal = document.getElementById('collabSeries');
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

$(document).ready(function(){
    $(".owl-carousel").owlCarousel({
        loop: false,
        nav: true
    });
    $(".vertical_slick").slick({
        dots: true,
        arrows: false,
        vertical: true,
        infinite: false,
        // slidesToShow: 8,
        // slidesToScroll: 8,
        rows: 4,
        slidesPerRow: 1,
        verticalSwiping: true,
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
    $("#tagField").keypress(function(e){
        var regex = new RegExp("^[a-zA-Z0-9]+$");
        var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
        if (regex.test(str)) {
        } else {
            alert("Username may only contain alphanumeric characters.");
            e.preventDefault();
            return false;
        }
    });
    $("#seriesMode").on('change', function() {
        if ($(this).val() == "group")
        {
            $("#inviteField").show();
            $("#inviteLabel").show();
            $("#inviteBtn").show();
        }
        else
        {
            $("#inviteField").hide();
            $("#inviteLabel").hide();
            $("#inviteBtn").hide();
        }
    });

    $("#inviteBtn").on('click', function () {
        // occurs when clicking the add tag button after inputting the tag name.
        var inviteName = document.getElementById("inviteField").value;
        var form = document.getElementById('inviteForm');
        console.log("add invite: " + inviteName);
        if(inviteName == "" || inviteName == null) {
            alert("Please enter a user to invite.");
        }
        else if(inviteList.includes(inviteName)){
            console.log("invite list: " +inviteList);
            alert("Duplicate invites are invalid.");
        } else {
            $.ajax({
                type : "POST",
                url : "/findInvitedUser",
                dataType: "text",
                data : {
                    invitedUser: inviteName //notice that "myArray" matches the value for @RequestParam
                    //on the Java side
                },
                success : function(response) {
                    console.log(response);
                    if (response == "OK")
                    {
                        inviteCounter++;
                        var invButton = document.createElement("button");
                        invButton.id = 'invite' + inviteCounter;
                        invButton.innerText = inviteName;
                        invButton.classList.add("invNameBtn");
                        invButton.addEventListener('click', function () {
                            deleteInvite(invButton);
                        });
                        form.appendChild(invButton);
                        inviteList.push(inviteName);
                        console.log("invite list: " + inviteList);
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
    });
});