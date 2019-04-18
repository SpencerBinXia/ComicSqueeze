/*
    function to load profile pic from firebase
 */

function loadProfilePic(){
    // get the user
    var user = firebase.auth().currentUser;
    // root storage reference
    var storageRef = firebase.storage().ref();
    if(user) {
        // this is where the profile pic is stored as i specified when uploading it
        storageRef.child(user.email + "/" + "profPic").getDownloadURL().then(function (url) {
            if (url) {
                //encoded bc being sent to server
                console.log(url);
                url = encodeURIComponent(url);
                //ajax function to pass url to server
                $.ajax({
                    type: "GET",
                    url: "/updateImg?img="+url,
                    cache: false,
                    success: function (response) {


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

