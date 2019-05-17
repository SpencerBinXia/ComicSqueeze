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

    console.log(descVal);

    var errors = "";
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

    console.log(titleVal);
    console.log(descVal);
    console.log(tagListString);
    var newSeries = {username: null, collaborative: false, creators: null, description: descVal, rating: 0, title: titleVal, tags: tagListString, timestamp: '2011-12-03T10:15:30', views: 0, weekly: false, flag: false, rateCounter: 0};
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
// NOTIFICATIONS MODAL REWORK
function notificationsClicked() {
    var modal = document.getElementById('notifications');
    modal.style.display = "block";
}
function closeNotifications() {
    var modal = document.getElementById('notifications');
    modal.style.display = "none";
}
window.onclick =function (event) {
    var modal = document.getElementById('notifications');
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
// EDIT-PROFILE MODAL REWORK
function editProfileClicked() {
    var modal = document.getElementById('edit-profile');
    modal.style.display = "block";
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
});