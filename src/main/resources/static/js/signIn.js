// this function is used even though it says its not
// this function resets the sign in form if the user wants to
// changing comment to push again
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

    }).catch(function(error) {
        // An error happened.
    });
}
// function to sign in
function signIn() {

    //Sign out any user signed in already
    signOut();
    // get the credentials
    var email = document.getElementById("email").value;
    var password = document.getElementById("passWord").value;
    // sign in
    firebase.auth().signInWithEmailAndPassword(email, password)
     // catch errors
        .catch(function(error) {
            // Handle Errors here.
            var errorCode = error.code;
            var errorMessage = error.message;
            if (errorCode === 'auth/wrong-password') {
                alert('Wrong password.');
            } else {
                alert(errorMessage);
            }
            console.log(error);
        });
    // if a new user is signed in redirect to profile
    firebase.auth().onAuthStateChanged(function(user) {
        if (user) {
            redirectToProfile(user);
        } else {
            // No user is signed in.
        }
    });
    
}

function redirectToProfile(user) {

    return $.ajax({
        type: "GET",
        url: "/currentprof?userName="+user.displayName,
        cache: false,
        success: function (response) {
            window.location.assign("currentprof?userName="+user.displayName);

        },
        error: function (e) {
            console.log("Failure", e);
        }
});}