function updateProfilePic() {
    // get a new file reader
    var reader  = new FileReader();
    //get the file picked
    var file    = document.querySelector('input[type=file]').files[0]; //sames as here
    var user = firebase.auth().currentUser;

    reader.onloadend = function () {
        // when done loading file as url send to firebase
        user.updateProfile({
            //Reader.result is the img url
            // if we store this in our db and get it back when coming to profle it should work
            photoURL: reader.result
        }).then(function() {
            console.log("success");
        }).catch(function(error) {
            console.log(error);
        });
        // set the img container src to url of img file
        document.getElementById("profPic").src = reader.result;
        console.log(user.photoURL);
        console.log(reader.result.toSource);
    }
    reader.readAsDataURL(file);

}