/*
    This function is for sending the Literally Canvas JSON to firebase to be stored and retrieved later
    when the user wants to make edits
 */
function uploadJSONtoFirebase(username,comicseries,comicissue,pagenumber,pagedata){
    // first we get the current user
    var user = firebase.auth().currentUser;
    // we store this new comic under their unique id
    // we either create a ne series, issue or page number object or it alread exists and we overide it
    // then we save the pagedata
    var result = firebase.database().ref(username+'/'+comicseries+'/'+comicissue+'/'+pagenumber).set({
        pagedata: pagedata,
    }, function(error) {
        if (error) {
            // The write failed...
        } else {
            console.log("complete");
        }
    });
    console.log(result);
}
function retrieveJSONFromFirebase(username,comicseries,comicissue,pagenumber,lc){
    // first we get the current user
    var user = firebase.auth().currentUser;
    var result = null;
    // we store this new comic under their unique id
    // we either create a ne series, issue or page number object or it alread exists and we overide it
    // then we save the pagedata
    var ref = firebase.database().ref(username+'/'+comicseries+'/'+comicissue+'/'+pagenumber+'/pagedata');
    ref.once('value')
        .then(function(dataSnapshot) {
            console.log("editing");
            lc.loadSnapshot(JSON.parse(dataSnapshot.val()));
        });

}
function uploadPagetoDB(username,currentSeries,currentIssue,pageNumber,img){

    var storageRef = firebase.storage().ref(username+"/"+currentSeries+"/"+currentIssue+"/"+pageNumber);
    var task = storageRef.putString(img,'data_url');
    task.on('state_changed',
        function progress(snapshot){


        },
        function error(err){},
        function complete() {
            storageRef = firebase.storage().ref();
            storageRef.child(username+"/"+currentSeries+"/"+currentIssue+"/"+pageNumber).getDownloadURL().then(function (url) {
                url = encodeURIComponent(url);
                return $.ajax({
                    type: "GET",
                    url: "/pageDB?username="+username+"&"+"seriesTitle="+currentSeries+"&"+"issueTitle="+currentIssue+"&"+"pageNumber="+pageNumber+"&imgurl="+url,
                    cache: false,
                    success: function (response) {
                        window.location.assign("/yourprofile");

                    },
                    error: function (e) {
                        console.log("Failure", e);
                    }
                });
            });
        }
    );


}
function editPagetoDB(username,currentSeries,currentIssue,pageNumber,img){

    var storageRef = firebase.storage().ref(username+"/"+currentSeries+"/"+currentIssue+"/"+pageNumber);
    var task = storageRef.putString(img,'data_url');
    task.on('state_changed',
        function progress(snapshot){


        },
        function error(err){},
        function complete() {
            storageRef = firebase.storage().ref();
            storageRef.child(username+"/"+currentSeries+"/"+currentIssue+"/"+pageNumber).getDownloadURL().then(function (url) {
                url = encodeURIComponent(url);
                return $.ajax({
                    type: "GET",
                    url: "/editPageDB?username="+username+"&"+"seriesTitle="+currentSeries+"&"+"issueTitle="+currentIssue+"&"+"pageNumber="+pageNumber+"&imgurl="+url,
                    cache: false,
                    success: function (response) {
                        // document.open("text/html","replace");
                        // document.write(response);
                        window.location.assign("/yourprofile");

                    },
                    error: function (e) {
                        console.log("Failure", e);
                    }
                });
            });
        }
    );


}
function uploadPagetoDBCustom(username,currentSeries,currentIssue,pageNumber){
    var reader  = new FileReader();
    var file    = document.querySelector('input[type=file]').files[0];
    console.log(file);

    var storageRef = firebase.storage().ref(username+"/"+currentSeries+"/"+currentIssue+"/"+pageNumber);
    var task = storageRef.put(file);
    task.on('state_changed',
        function progress(snapshot){


        },
        function error(err){},
        function complete() {
            storageRef = firebase.storage().ref();
            storageRef.child(username+"/"+currentSeries+"/"+currentIssue+"/"+pageNumber).getDownloadURL().then(function (url) {
                url = encodeURIComponent(url);
                return $.ajax({
                    type: "GET",
                    url: "/pageDB?username="+username+"&"+"seriesTitle="+currentSeries+"&"+"issueTitle="+currentIssue+"&"+"pageNumber="+pageNumber+"&imgurl="+url,
                    cache: false,
                    success: function (response) {
                        window.location.assign("/yourprofile");

                    },
                    error: function (e) {
                        console.log("Failure", e);
                    }
                });
            });
        }
    );


}