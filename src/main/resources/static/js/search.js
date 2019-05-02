



function doSearch(){
    var filterField = document.getElementById('searchBy');
    var filterOptionValue = filterField.options[filterField.selectedIndex].value;
    var queryString= document.getElementById('searchContents').value;

    if (filterOptionValue == "" || filterOptionValue == null) {
        alert("Search field cannot be empty.");
        return false;
    }

    document.getElementById('searchContents').value = "";

    console.log(filterOptionValue);
    var queryMethod = "";
    var methodMapping = "";
    if(filterOptionValue == "username"){
        queryMethod = "queryUsername";
        methodMapping = "/SearchUsername";
        $('#seriesResultsList').hide();
        $('#usersResultsList').show();
    }
    else if(filterOptionValue == "seriestitle"){
        queryMethod = "querySeriesTitle";
        methodMapping = "/SearchSeriesTitle";
        $('#seriesResultsList').show();
        $('#usersResultsList').hide();
    }
    else if(filterOptionValue == "tags"){
        queryMethod = "queryTags";
        methodMapping = "/SearchTags";
        $('#seriesResultsList').show();
        $('#usersResultsList').hide();
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
            console.log(result);
            //var result1 = JSON.stringify(result);
            //var result2 = encodeURI(result);
            //console.log(result2);
            //var result = encodeURI(result1);
            // console.log(result)
            //window.location.assign("/searchResultTwo?ArrayList="+result);
            window.location.assign("/search");
        },
        error: function(e){
            alert("Search failed!")
        }
    });
}