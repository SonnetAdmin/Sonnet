// Onload Event
$(function () {
    const modal = document.getElementById("pageModal");
    const modalFrame = document.getElementById("modalFrame");
    const modalTitle = document.getElementById("modalTitle");

    $(".display").click(function () {
        var id = $(this).attr("id");
        switch (id) {
            case "about_modal":
                modalFrame.src = "About.html";
                modalTitle.textContent = "Sonnet - About";
                modal.showModal();
                break;

            case "code_examples":
                modalFrame.src = "CodeExamples.html";
                modalTitle.textContent = "Sonnet - Code Examples";
                modal.showModal();
                break;

            case "benefits":
                modalFrame.src = "Benefits.html";
                modalTitle.textContent = "Sonnet - Benefits";
                modal.showModal();
                break;

            case "installation":
                modalFrame.src = "Installation.html";
                modalTitle.textContent = "Sonnet - Installation";
                modal.showModal();
                break;

            case "contact_us_modal":
                modalFrame.src = "ContactUs.html"
                modalTitle.textContent = "Sonnet - ContactUs";
                modal.showModal();
                break;
        }
    });
    $(".close-modal-btn").click(function () {
        modal.close();
    });

    $("#color_change").click(function () {
        $(this).toggleClass("button");
        $(this).toggleClass("blue");
    });

    $("#red").click(function () {
        $("tr:even").addClass("red")
    });

});
