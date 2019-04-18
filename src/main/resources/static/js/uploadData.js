/*
    This function is for sending the Literally Canvas JSON to firebase to be stored and retrieved later
    when the user wants to make edits
 */
function uploadJSONtoFirebase(comicseries,comicissue,pagenumber,pagedata){
        // first we get the current user
       var user = firebase.auth().currentUser;
       // we store this new comic under their unique id
        // we either create a ne series, issue or page number object or it alread exists and we overide it
        // then we save the pagedata
       var result = firebase.database().ref(user.uid+'/'+comicseries+'/'+comicissue+'/'+pagenumber).set({
            pagedata: pagedata,
        });
       console.log(result);
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


                    },
                    error: function (e) {
                        console.log("Failure", e);
                    }
                });
            });
        }
    );


}
