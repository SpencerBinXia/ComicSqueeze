function removeNotif(e){

    console.log(e.name);
    return $.ajax({
        type: "GET",
        url: e.name,
        cache: false,
        success: function (response) {


        },
        error: function (e) {

        }
    });
}