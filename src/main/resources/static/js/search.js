$(document).ready(function(){
    localStorage.setItem('showSearch', 'true');
    $(".searchButton").click(function (e){
        console.log("search function called");
        var filterField = document.getElementById('searchBy');
        var filterOptionValue = filterField.options[filterField.selectedIndex].value;
        var queryString= document.getElementById('searchContents').value;

        if (filterOptionValue == "" || filterOptionValue == null) {
            alert("Search field cannot be empty.");
            return false;
        }


        localStorage.setItem('showLow', 'false');
        document.getElementById('searchContents').value = "";

        console.log(filterOptionValue);
        var queryMethod = "";
        var methodMapping = "";
        if(filterOptionValue == "username"){
            queryMethod = "queryUsername";
            methodMapping = "/SearchUsername";
            //$('#seriesResultsList').hide();
            //$('#usersResultsList').show();
        }
        else if(filterOptionValue == "seriestitle"){
            queryMethod = "querySeriesTitle";
            methodMapping = "/SearchSeriesTitle";
            //$('#seriesResultsList').show();
            //$('#usersResultsList').hide();
        }
        else if(filterOptionValue == "tags"){
            queryMethod = "queryTags";
            methodMapping = "/SearchTags";
            //$('#seriesResultsList').show();
            //$('#usersResultsList').hide();
        }
        else if(filterOptionValue == "keywords"){
            methodMapping = "/SearchKeywords"
        }
        $.ajax({
            type: "GET",
            url: methodMapping,
            data:{
                searchString: queryString
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
                //window.location.href="/search";
                window.location.assign("/search");
            },
            error: function(e){
                alert("Search failed!");
            }
        });
    });
});


function applyFiltering() {
    var sortFilter = document.getElementById('sortBy');
    var filterVal = sortFilter.options[sortFilter.selectedIndex].value;

    //localStorage.setItem('showLow', 'true');
    localStorage.setItem('showSearch', 'false');
    if(filterVal == "rating-LOW"){
        console.log("gottorate")
        localStorage.setItem("showLow", 'true');
            //document.getElementById("sortLow").style.display = "block";
    }
}

window.onload = function(){
    //localStorage.setItem('showLow', 'false');
    var showLow = localStorage.getItem('showLow');
    var showSearch = localStorage.getItem('showSearch');
    if(showLow == 'true'){
        document.getElementById("sortLow").style.display = "block";
        document.getElementById("seriesResultsList").style.display = "none";
    }
    if(showSearch == 'true'){
        document.getElementById("seriesResultsList").style.display = "block";
        document.getElementById("sortLow").style.display = "none";
    }
}
function applyFilter(){
    //console.log("Hello")
    var sortFilter = document.getElementById('sortBy');
    var filterVal = sortFilter.options[sortFilter.selectedIndex].value;

    var seriesResultList = document.getElementById("seriesResultsList");
    var memberResult = document.getElementById("usersResultsList");
    console.log(seriesResultList);
    //var seriesList = document.querySelector("#seriesResultsList > div");
    //document.querySelector("#seriesResultsList > div > a:nth-child(1)")
    //#seriesResultsList > div > a:nth-child(1)

    console.log(memberResult);
    var resultQuery = document.getElementById("giveMeQuery").valueOf();
    console.log(resultQuery)

    var filterBy = "";
    if(filterVal == "Recent"){
        filterBy = "sortByRecent";
    }
    else if(filterVal == "Popular"){
        filterBy = "sortByPopular";
    }
    else if(filterVal == "Rating-HIGH"){
        filterBy = "sortByHighRating";
    }
    else if(filterVal == "Rating-LOW"){
        filterBy = "sortByLowRating";
    }

    $.ajax({
        type : "GET",
        url : filterBy,
        cache : false,
        data : {
            theSearchResults : resultQuery,
            theFilter : filterVal
        },
        success: function(result){

        },
        error: function(e){

        }
    });

}

/*
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
*/