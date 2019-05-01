$(document).ready(function(){
    $(".vertical_slick").slick({
        dots: true,
        arrows: false,
        vertical: true,
        infinite: false,
        // slidesToShow: 8,
        // slidesToScroll: 8,
        rows: 4,
        slidesPerRow: 1,
        verticalSwiping: true,
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
});