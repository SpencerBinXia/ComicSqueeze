// this function is used even though it says its not
// this function resets the sign in form if the user wants to
// changing comment to push again
var success = true;
function resetForm() {
    //reset the form
    var sign = document.getElementById("signIn");
    sign.innerText = "SIGN IN";
    sign.onclick = signIn;
    document.getElementById("forgot").hidden = false;
    document.getElementById("passWord").hidden = false;
    document.getElementById("passLbl"). hidden = false;
    document.getElementById("passLbl").innerText = "Password:"
    var forgot = document.getElementById("forgot");
    forgot.hidden = false;
    forgot.innerText = "Forgot Password?";
    forgot.href= "javascript:forgotPassword();";
}
function forgotPassword() {
    //Change for to reset form
    document.getElementById("passWord").hidden = true;
    document.getElementById("passLbl").hidden = true;
    var signIn = document.getElementById("signIn");
    signIn.innerText = "Reset";
    // if reset is clicked then send email as specified
    signIn.onclick = sendResetEmail;
    var forgot = document.getElementById("forgot");
    forgot.innerText = "SIGN IN";
    forgot.href= "javascript:resetForm();";

}

function showPass() {
    var x = document.getElementById("passWord");
    if (x.type === "password") {
        x.type = "text";
    } else {
        x.type = "password";
    }
}
function showRegPass() {
    var x = document.getElementById("regPassWord");
    if (x.type === "password") {
        x.type = "text";
    } else {
        x.type = "password";
    }
}

function sendPasswordResetFromLink(){
    var user = firebase.auth().currentUser;
    var auth = firebase.auth();
    auth.sendPasswordResetEmail(user.email).then(function () {
        // Email sent.
    }).catch(function (error) {
        // An error happened.
    });
}
function sendResetEmail() {
    //send reset email and change form back
    var email = document.getElementById("email").value;
    var auth = firebase.auth();
    auth.sendPasswordResetEmail(email).then(function () {
        // Email sent.
    }).catch(function (error) {
        // An error happened.
    });
    var lbl = document.getElementById("passLbl");
    lbl.hidden = false;
    lbl.innerText = "Recovery email sent";

}
function signOut() {
    firebase.auth().signOut().then(function() {
        redirectToIndex();
    }).catch(function(error) {
        // An error happened.
    });
}

function redirectToIndex() {
    window.location.href = "/logout"
}

// function to sign in
function signIn() {

    //Sign out any user signed in already
    // signOut(); not needed as no sign in button once logged in so must log out first anyway
    // get the credentials
    var email = document.getElementById("email").value;
    var password = document.getElementById("passWord").value;
    // sign in

    firebase.auth().signInWithEmailAndPassword(email, password).then(redirectToProfile())
    // catch errors
        .catch(function(error) {
            // Handle Errors here.
            success = false;
            var errorCode = error.code;
            var errorMessage = error.message;
            if (errorCode === 'auth/wrong-password') {
                alert('Wrong password.');
            } else {
                alert(errorMessage);
            }
            console.log(error);
        });


}

function redirectToProfile() {
    // if a new user is signed in redirect to profile

    if (success) {
        firebase.auth().onAuthStateChanged(function (user) {
            if (user) {
                return $.ajax({
                    type: "GET",
                    url: "/signin?userName=" + user.displayName,
                    cache: false,
                    success: function (response) {
                        window.location.assign("/yourprofile");
                        loadProfilePic();

                    },
                    error: function (e) {
                        console.log("Failure", e);
                    }
                });
            } else {
                // No user is signed in.
            }
        });
    }
}

function updateProfilePic() {
    // get a new file reader
    var reader  = new FileReader();
    //get the file picked
    var file    = document.querySelector('input[type=file]').files[0]; //sames as here
    var user = firebase.auth().currentUser;
    var storageRef = firebase.storage().ref(user.email+"/"+"profPic");
    var task = storageRef.put(file);
    task.on('state_changed',
        function progress(snapshot){
            var percent = (snapshot.bytesTransferred/snapshot.totalBytes)*100;
            var uploader = document.getElementById("uploader");
            uploader.value = percent;

        },
        function error(err){},
        function complete() {
            loadProfilePic();
            previewFile(file);
        }
        );

    function previewFile(file) {
        var reader = new FileReader();
        reader.onloadend  = function(){
            document.getElementById("profPic").src = reader.result;
        }
        reader.readAsDataURL(file);
    }

}

$(document).ready(function(){
    $("#inputUsername").keypress(function(e){
        // var ingnore_key_codes = [8, 46];
        // if ($.inArray(e.keyCode, ingnore_key_codes) >= 0){
        //     if(e.keyCode == 190 && !e.shiftKey){
        //         alert("Username cannot contain '.'")
        //         e.preventDefault();
        //     }
        //     if(e.keyCode == 52 && e.shiftKey){
        //         alert("Username cannot contain '$'")
        //         e.preventDefault();
        //     }
        //     if(e.keyCode == 219 && !e.shiftKey){
        //         alert("Username cannot contain '['")
        //         e.preventDefault();
        //     }
        //     if(e.keyCode == 221 && !e.shiftKey){
        //         alert("Username cannot contain ']'")
        //         e.preventDefault();
        //     }
        // }
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

