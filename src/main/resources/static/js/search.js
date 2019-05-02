



function doSearch(){
    var filterField = document.getElementById('searchBy')
    var filterOptionValue = filterField.options[filterField.selectedIndex].value
    var queryString= document.getElementById('searchContents').value

    console.log(filterOptionValue);
    var queryMethod = ""
    var methodMapping = ""
    if(filterOptionValue == "username"){
        queryMethod = "queryUsername"
        methodMapping = "/SearchUsername"

    }
    else if(filterOptionValue == "seriestitle"){
        queryMethod = "querySeriesTitle"
        methodMapping = "/SearchSeriesTitle"
    }
    else if(filterOptionValue == "tags"){
        queryMethod = "queryTags"
        methodMapping = "/SearchTags"
    }
    $.ajax({
        type: "GET",
        url: methodMapping,
        data:{
            searchString: queryString,
            //filter: filterOptionValue
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