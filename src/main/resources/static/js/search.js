$(document).ready(function(){
    $(".searchButton").click(function (e){
        console.log("search function called");
        var filterField = document.getElementById('searchBy');
        var filterOptionValue = filterField.options[filterField.selectedIndex].value;
        var queryString= document.getElementById('searchContents').value;

        if (filterOptionValue == "" || filterOptionValue == null) {
            alert("Search field cannot be empty.");
            return false;
        }

        console.log("2");

        // document.getElementById('searchContents').value = "";

        console.log(filterOptionValue);
        var queryMethod = "";
        var methodMapping = "";
        if(filterOptionValue == "username"){
            queryMethod = "queryUsername";
            localStorage.setItem('showLow', 'false');
            localStorage.setItem('showSearch', 'false');
            localStorage.setItem('showUsers', 'true');
            localStorage.setItem('showHigh', 'false');
            localStorage.setItem('showRecent', 'false');
            localStorage.setItem('showPop', 'false');
            methodMapping = "/SearchUsername";
            //$('#seriesResultsList').hide();
            //$('#usersResultsList').show();
        }
        else if(filterOptionValue == "seriestitle"){
            queryMethod = "querySeriesTitle";
            localStorage.setItem('showLow', 'false');
            localStorage.setItem('showSearch', 'true');
            localStorage.setItem('showUsers', 'false');
            localStorage.setItem('showHigh', 'false');
            localStorage.setItem('showRecent', 'false');
            localStorage.setItem('showPop', 'false');
            methodMapping = "/SearchSeriesTitle";
            //$('#seriesResultsList').show();
            //$('#usersResultsList').hide();
        }
        else if(filterOptionValue == "tags"){
            queryMethod = "queryTags";
            localStorage.setItem('showLow', 'false');
            localStorage.setItem('showSearch', 'true');
            localStorage.setItem('showUsers', 'false');
            localStorage.setItem('showHigh', 'false');
            localStorage.setItem('showRecent', 'false');
            localStorage.setItem('showPop', 'false');
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

    console.log("3");
    if(filterVal == "rating-LOW"){
        console.log("rate-low");
        localStorage.setItem('showLow', 'true');
        localStorage.setItem('showSearch', 'false');
        localStorage.setItem('showUsers', 'false');
        localStorage.setItem('showHigh', 'false');
        localStorage.setItem('showRecent', 'false');
        localStorage.setItem('showPop', 'false');
    } else if(filterVal == "rating-HIGH"){
        console.log("rate-high");
        localStorage.setItem('showLow', 'false');
        localStorage.setItem('showSearch', 'false');
        localStorage.setItem('showUsers', 'false');
        localStorage.setItem('showHigh', 'true');
        localStorage.setItem('showRecent', 'false');
        localStorage.setItem('showPop', 'false');
    } else if(filterVal == "recent"){
        console.log("recent");
        localStorage.setItem('showLow', 'false');
        localStorage.setItem('showSearch', 'false');
        localStorage.setItem('showUsers', 'false');
        localStorage.setItem('showHigh', 'false');
        localStorage.setItem('showRecent', 'true');
        localStorage.setItem('showPop', 'false');
    } else if(filterVal == "popular"){
        console.log("popular");
        localStorage.setItem('showLow', 'false');
        localStorage.setItem('showSearch', 'false');
        localStorage.setItem('showUsers', 'false');
        localStorage.setItem('showHigh', 'false');
        localStorage.setItem('showRecent', 'false');
        localStorage.setItem('showPop', 'true');
    }
}

window.onload = function(){
    console.log("1");
    var showLow = localStorage.getItem('showLow');
    var showSearch = localStorage.getItem('showSearch');
    var showUsers = localStorage.getItem('showUsers');
    var showHigh = localStorage.getItem('showHigh');
    var showRecent = localStorage.getItem('showRecent');
    var showPop = localStorage.getItem('showPop');
    if(showLow == 'true'){
        console.log("showLow: " + showLow);
        document.getElementById("seriesResultsList").style.display = "none";
        document.getElementById("usersResultsList").style.display = "none";
        document.getElementById("sortHigh").style.display = "none";
        document.getElementById("sortRecent").style.display = "none";
        document.getElementById("sortPop").style.display = "none";
        document.getElementById("sortLow").style.display = "block";
        $(".vertical_slick_Low").slick({
            dots: true,
            arrows: false,
            vertical: true,
            infinite: false,
            rows: 3,
            slidesPerRow: 1,
            verticalSwiping: true,
        });
        localStorage.setItem('showLow', 'false');
    } else if(showSearch == 'true'){
        console.log("showSearch: " + showSearch);
        document.getElementById("usersResultsList").style.display = "none";
        document.getElementById("sortHigh").style.display = "none";
        document.getElementById("sortRecent").style.display = "none";
        document.getElementById("sortPop").style.display = "none";
        document.getElementById("sortLow").style.display = "none";
        document.getElementById("seriesResultsList").style.display = "block";
        $(".vertical_slick_search").slick({
            dots: true,
            arrows: false,
            vertical: true,
            infinite: false,
            rows: 3,
            slidesPerRow: 1,
            verticalSwiping: true,
        });
        localStorage.setItem('showSearch', 'false');
    } else if(showHigh == 'true'){
        console.log("showHigh: " + showHigh);
        document.getElementById("usersResultsList").style.display = "none";
        document.getElementById("sortRecent").style.display = "none";
        document.getElementById("sortPop").style.display = "none";
        document.getElementById("sortLow").style.display = "none";
        document.getElementById("seriesResultsList").style.display = "none";
        document.getElementById("sortHigh").style.display = "block";
        $(".vertical_slick_high").slick({
            dots: true,
            arrows: false,
            vertical: true,
            infinite: false,
            rows: 3,
            slidesPerRow: 1,
            verticalSwiping: true,
        });
        localStorage.setItem('showHigh', 'false');
    } else if(showRecent == 'true'){
        console.log("showRecent: " + showRecent);
        document.getElementById("usersResultsList").style.display = "none";
        document.getElementById("sortHigh").style.display = "none";
        document.getElementById("sortPop").style.display = "none";
        document.getElementById("sortLow").style.display = "none";
        document.getElementById("seriesResultsList").style.display = "none";
        document.getElementById("sortRecent").style.display = "block";
        $(".vertical_slick_recent").slick({
            dots: true,
            arrows: false,
            vertical: true,
            infinite: false,
            rows: 3,
            slidesPerRow: 1,
            verticalSwiping: true,
        });
        localStorage.setItem('showRecent', 'false');
    } else if(showUsers == 'true'){
        console.log("showUsers: " + showUsers);
        document.getElementById("sortHigh").style.display = "none";
        document.getElementById("sortPop").style.display = "none";
        document.getElementById("sortLow").style.display = "none";
        document.getElementById("seriesResultsList").style.display = "none";
        document.getElementById("sortRecent").style.display = "none";
        document.getElementById("usersResultsList").style.display = "block";
        $(".horizontal_slick-users").slick({
            dots: true,
            arrows: true,
            vertical: false,
            infinite: false,
            rows: 2,
            slidesPerRow: 4,
            verticalSwiping: false,
        });
        localStorage.setItem('showUsers', 'false');
    } else if(showPop == 'true'){
        console.log("showPop: " + showPop);
        document.getElementById("sortHigh").style.display = "none";
        document.getElementById("sortLow").style.display = "none";
        document.getElementById("seriesResultsList").style.display = "none";
        document.getElementById("sortRecent").style.display = "none";
        document.getElementById("usersResultsList").style.display = "none";
        document.getElementById("sortPop").style.display = "block";
        $(".horizontal_slick-pop").slick({
            dots: true,
            arrows: true,
            vertical: false,
            infinite: false,
            rows: 2,
            slidesPerRow: 4,
            verticalSwiping: false,
        });
        localStorage.setItem('showPop', 'false');
    } else {
        console.log("Nothing in local storage");
        document.getElementById("sortHigh").style.display = "none";
        document.getElementById("sortLow").style.display = "none";
        document.getElementById("seriesResultsList").style.display = "none";
        document.getElementById("sortRecent").style.display = "none";
        document.getElementById("usersResultsList").style.display = "none";
        document.getElementById("sortPop").style.display = "none";
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