function deletePage() {
    if (confirm('Are you sure you want to delete this page?')) {
        // Delete the page
        console.log("page deleted.")
    } else {
        // Do nothing
    }

}

// CREATE SERIES MODAL REWORK
function editIssueClicked() {
    var modal = document.getElementById('edit-issue');
    modal.style.display = "block";
}
function closeEditIssue() {
    var modal = document.getElementById('edit-issue');
    document.getElementById('editIssueForm').reset();
    modal.style.display = "none";
}
window.onclick =function (event) {
    var modal = document.getElementById('edit-issue');
    if (event.target == modal) {
        document.getElementById('editIssueForm').reset();
        modal.style.display = "none";
    }
}
function editIssue() {
    var descVal = $('#issueDescField').val();
    console.log("Desc:" + descVal);
    $.ajax({
        type: "GET",
        url: "/editIssue",
        data:{
            description: descVal
        },
        cache: false,
        success: function (result) {
            location.reload();
        },
        error: function (e) {
            alert("Update issue failed!");
        }
    });
}

function deleteIssue() {
    console.log("In delete issue js");
    console.log(curIssueTitle);
    console.log(curSeriesTitle);
    console.log(curSeriesUsername);
    if (confirm('Are you sure you want to delete this issue?')) {
        // Delete the page
        $.ajax({
            type: "GET",
            url: "/deleteIssue",
            cache: false,
            data:{
                issueOfSeries: curIssueTitle,
                seriesOfIssue: curSeriesTitle,
                seriesCreator: curSeriesUsername
            },
            success: function (result) {
                //reload page
                console.log("issue successfully deleted");
                window.location.assign("/series/" + curSeriesUsername + "/" + curSeriesTitle);
            },
            error: function (e) {
                alert("Delete issue failed!");
            }
        });
    } else {
        // Do nothing
    }

}

$(document).ready(function(){
    $(".owl-carousel").owlCarousel({
        loop: false,
        nav: true
    });
    $(".horizontal_slick").slick({
        dots: true,
        arrows: true,
        vertical: false,
        infinite: false,
        slidesToShow: 4,
        slidesToScroll: 4,
        // rows: 1,
        // slidesPerRow: 4,
        verticalSwiping: false,
    });


    $('.publish-checkbox').checkboxpicker({
        /*
        html: true,
        offLabel: '<span class="glyphicon glyphicon-remove">',
        onLabel: '<span class="glyphicon glyphicon-ok">'
        */
    });

    for (var i = 0;i < userPageNumber.length;i++)
    {
        console.log(userPageNumber[i]);
        console.log(userPageArrayNumber[i]);
        console.log(userPagePublished[i]);
    }

    $('.publish-checkbox').on('change', function() {
        console.log("clicky");
        console.log(this.value);
        if ($(this).prop('checked'))
        {
            console.log("checked");
            userPagePublished[this.value-1] = true;
        }
        else
        {
            userPagePublished[this.value-1] = false;
        }
    });
});

function publish()
{
    for (var i =0;i < userPagePublished.length;i++)
    {
            console.log(userPageNumber[i]);
            console.log(userPageArrayNumber[i]);
            console.log(userPagePublished[i]);
    }
    $.ajax({
        type : "POST",
        url : "/publish",
        data : {
            publishedArray: userPagePublished //notice that "myArray" matches the value for @RequestParam
                       //on the Java side
        },
        success : function(response) {
            location.reload();
        },
        error : function(e) {
            alert('Error: ' + e);
        }
    })
}

$(document).ready(function() {
    newUrl = window.location.href;
    console.log(newUrl);
    document.getElementById('comments').innerHTML='';
    parser=document.getElementById('comments');
    parser.innerHTML='<div style="float: left; padding-left:5px; min-height:500px" class="fb-comments" data-href="'+newUrl+'" data-num-posts="20" data-width="380"></div>';
    FB.XFBML.parse(parser);
});