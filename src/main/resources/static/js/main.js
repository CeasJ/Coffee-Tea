(function ($) {
    "use strict";

    // Spinner
    var spinner = function () {
        setTimeout(function () {
            if ($('#spinner').length > 0) {
                $('#spinner').removeClass('show');
            }
        }, 1);
    };
    spinner();


    // Initiate the wowjs
    new WOW().init();


    // Sticky Navbar
    $(window).scroll(function () {
        if ($(this).scrollTop() > 300) {
            $('.sticky-top').addClass('shadow-sm').css('top', '0px');
        } else {
            $('.sticky-top').removeClass('shadow-sm').css('top', '-150px');
        }
    });


    // Back to top button
    $(window).scroll(function () {
        if ($(this).scrollTop() > 300) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').click(function () {
        $('html, body').animate({scrollTop: 0}, 1500, 'easeInOutExpo');
        return false;
    });


    // Modal Video
    var $videoSrc;
    $('.btn-play').click(function () {
        $videoSrc = $(this).data("src");
    });
    console.log($videoSrc);
    $('#videoModal').on('shown.bs.modal', function (e) {
        $("#video").attr('src', $videoSrc + "?autoplay=1&amp;modestbranding=1&amp;showinfo=0");
    })
    $('#videoModal').on('hide.bs.modal', function (e) {
        $("#video").attr('src', $videoSrc);
    })


    // Product carousel
    $(".product-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1000,
        margin: 25,
        loop: true,
        center: true,
        dots: false,
        nav: true,
        navText : [
            '<i class="bi bi-chevron-left"></i>',
            '<i class="bi bi-chevron-right"></i>'
        ],
        responsive: {
			0:{
                items:1
            },
            576:{
                items:1
            },
            768:{
                items:2
            },
            992:{
                items:3
            }
        }
    });


    // Testimonial carousel
    $(".testimonial-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1000,
        items: 1,
        loop: true,
        dots: true,
        nav: false,
    });
    
})(jQuery);


// Get sort by price combobox value according to the chosen option
var urlParams = new URLSearchParams(window.location.search);
var sortOption = urlParams.get("sortOption");
var isSearchPage = window.location.pathname === "/search"; // Check if this is the search page

if(isSearchPage){
    var selectElement = document.getElementById("sortByPrice");
    selectElement.value = "none";
}

if (sortOption !== null) {
    var selectElement = document.getElementById("sortByPrice");
    if (selectElement) {
        selectElement.value = sortOption;
    }
}



document.addEventListener("DOMContentLoaded", function () {
    var productGroupSelect = document.getElementById("productGroup");
    var sortByPriceSelect = document.getElementById("sortByPrice");
    var productNameSearch = document.getElementsByName("productName");
    // Handle change events for the category and sort option dropdowns
    productGroupSelect.addEventListener("change", updateURLAndReload);
    sortByPriceSelect.addEventListener("change", updateURLAndReload);

    // Handle search button click event
    var searchButton = document.getElementById("searchButton");

        searchButton.addEventListener("click", function () {
            updateURLAndReload();
        });

    // Get the current URL and parse the query parameters
    var urlParams = new URLSearchParams(window.location.search);
    var currentPage = urlParams.get("page");

    // Find all pagination links
    var paginationLinks = document.querySelectorAll(".pagination a");

    // Add a click event listener to each pagination link
    paginationLinks.forEach(function (link) {
        link.addEventListener("click", function (event) {
            event.preventDefault();

            // Get the target page from the pagination link
            var targetPage = link.getAttribute("data-page");

            // Update the 'page' parameter in the URL
            urlParams.set("page", targetPage);

            // Replace the current URL with the updated URL
            window.location.href = window.location.pathname + "?" + urlParams.toString();
        });
    });

    // Set the active class to the current page
    paginationLinks.forEach(function (link) {
        var linkPage = link.getAttribute("data-page");
        if (linkPage === currentPage) {
            link.classList.add("active");
        }
    });
});






// Category combobox listener event
var urlParams = new URLSearchParams(window.location.search);
var categoryId = urlParams.get("categoryId");
var productGroupSelect = document.getElementById("productGroup");
if(isSearchPage){
    productGroupSelect.value = "0";
}

if (categoryId) {
    productGroupSelect.value = categoryId;
}

productGroupSelect.addEventListener("change", function() {
    var selectedCategoryId = this.value;
    localStorage.setItem("selectedCategoryId", selectedCategoryId);
    updateURLAndReload();
});

// Sort by price function
function updateURLAndReload() {
    var selectedCategoryId = document.getElementById("productGroup").value;
    var selectedSortOption = document.getElementById("sortByPrice").value;
    var searchValue = localStorage.getItem("searchValue");

    var isSearchPage = window.location.pathname === "/search";


    localStorage.setItem("selectedCategoryId", selectedCategoryId);
    localStorage.setItem("sortOption", selectedSortOption);

    var newUrl = "/store?categoryId=" + selectedCategoryId;

    if (selectedSortOption !== 'none') {
        newUrl += "&sortOption=" + selectedSortOption;
    }

    if (searchValue) {
        newUrl += "&productName=" + searchValue;
    }

        window.location.href = newUrl;
 }

document.addEventListener("DOMContentLoaded", function () {
        // Get the current URL and parse the query parameters
        var urlParams = new URLSearchParams(window.location.search);
        var currentPage = urlParams.get("page");

        // Find all pagination links
        var paginationLinks = document.querySelectorAll(".pagination a");

        // Add a click event listener to each pagination link
        paginationLinks.forEach(function (link) {
            link.addEventListener("click", function (event) {
                event.preventDefault();

                // Get the target page from the pagination link
                var targetPage = link.getAttribute("data-page");

                // Update the 'page' parameter in the URL
                urlParams.set("page", targetPage);

                // Replace the current URL with the updated URL
                window.location.href = window.location.pathname + "?" + urlParams.toString();
            });
        });

        // Set the active class to the current page
        paginationLinks.forEach(function (link) {
            var linkPage = link.getAttribute("data-page");
            if (linkPage === currentPage) {
                link.classList.add("active");
            }
        });
    });
