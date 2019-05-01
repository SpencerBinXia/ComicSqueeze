function voteForPage() {

}

$(document).ready(function(){
    $('#votePageBtn').click(function(e) {
        $(".horizontal_slick").slick({
            dots: true,
            arrows: true,
            vertical: false,
            infinite: false,
            // slidesToShow: 4,
            // slidesToScroll: 4,
            rows: 2,
            slidesPerRow: 4,
            verticalSwiping: false,
        });
    });
});