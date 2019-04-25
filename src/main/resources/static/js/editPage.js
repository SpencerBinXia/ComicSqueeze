function editPage(pageNum){
    return $.ajax({
        type: "GET",
        url: "/editPage?editNumber="+pageNum,
        cache: false,
        success: function (response) {
            document.open("text/html", "replace");
            document.write(response);
        },
        error: function (e) {
            console.log("Failure", e);
        }
    });
}