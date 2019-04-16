/*
    This image
 */
function loadProfilePic(){
    var user = firebase.auth().currentUser;
    var storageRef = firebase.storage().ref();
    if(user) {
        storageRef.child(user.email + "/" + "profPic").getDownloadURL().then(function (url) {
            if (url) {
                console.log(url);
                url = encodeURIComponent(url);
                $.ajax({
                    type: "GET",
                    url: "/updateImg?img="+url,
                    cache: false,
                    success: function (response) {
                        window.location.assign("yourprofile");

                    },
                    error: function (e) {
                        console.log("Failure", e);
                    }
                });
            }
        }).catch(function (error) {
            // Handle any errors
        });
    }
    else {
        console.log("no user")
    }
}

