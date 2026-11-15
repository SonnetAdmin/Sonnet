// Onload Event
$(function () {

    $("#color_change").click(function () {
        $(this).toggleClass("button");
        $(this).toggleClass("blue");
    });

    $("#red").click(function () {
        $("tr:even").addClass("red")
    });

});
