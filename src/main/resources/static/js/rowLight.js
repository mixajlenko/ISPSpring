$(document).ready(function () {
    var toggle = true; // Toggle state

    $(".highlight").on({
        click: function () {
            if (toggle) {
                $(this).css("background-color", "#0c5460");
                $(this).css("color", "white");
                toggle = false;
            } else {
                $(this).css("background-color", "");
                $(this).css("color", "black");
                toggle = true;
            }
        }
    });
});