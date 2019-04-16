function updateBio(){
    val = document.getElementById("bioField").value;
    console.log(val);
    $('#bioID').text(val);
    return $.ajax({
        type: "GET",
        url: "/updateBio?bio=" + val,
        cache: false,
        success: function (result) {
            $('#bioID').text(val);
        },
        error: function (e) {
            alert("Update bio failed!");
        }
    });
}

function createSeries(){
    titleVal = document.getElementById("titleField").value;
    descVal = document.getElementById("descField").value;
    tagVal = document.getElementById("tagField").value;
    $('#titleVal').text(titleVal);
    $('#descVal').text(descVal);
    $('#tagVal').text(tagVal);
    console.log(titleVal);
    console.log(descVal);
    console.log(tagVal);
    /*
    return $.ajax({
        type: "GET",
        url: "/updateBio?bio=" + val,
        cache: false,
        success: function (result) {
            $('#bioID').text(val);
        },
        error: function (e) {
            alert("Update bio failed!");
        }
    });
    */
}