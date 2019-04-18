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
function uploadPagetoDB(username,currentSeries,currentIssue,pageNumber){
    
}
