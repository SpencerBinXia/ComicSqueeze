



function doSearch(){
    var filterField = document.getElementById('searchBy')
    var filterOptionValue = filterField.options[filterField.selectedIndex].value
    var queryString= document.getElementById('searchContents').value

    console.log(filterOptionValue);
    //console.log(queryString);

    $.ajax({
        type: "GET",
        url: "/Search",
        data:{
            searchString: queryString,
            filter: filterOptionValue
        },
        cache: false,
        success: function (result) {
            console.log(result)
        },
        error: function(e){
            alert("Search failed!")
        }
    });
}