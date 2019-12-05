$(document).ready(function () {
    //Moving between layout-one and layout-two
    $('#change-ui').click(function () {
        if ($('#layout-one').is(":visible")) {
            $('#layout-one').fadeToggle(1000, function () {
                $('#layout-two').fadeToggle(1000);
            });
        } else {
            $('#layout-two').fadeToggle(1000, function () {
                $('#layout-one').fadeToggle(1000);
            });
        }
    });

    //Search for layout-one
    $('#myInput').keyup(function () {
        var input, filter, ul, li, a, i;
        input = document.getElementById("myInput");
        filter = input.value.toUpperCase();
        div = document.getElementById("myCountryMenu");
        a = div.getElementsByTagName("a");
        for (i = 0; i < a.length; i++) {
            txtValue = a[i].textContent || a[i].innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                a[i].style.display = "";
            } else {
                a[i].style.display = "none";
            }
        }
    });


    //Showing and hidding of the country list of layout-one
    $('#choose-country').click(function () {
        $('#myCountryMenu').toggleClass("show");
    });

    //Getting the results for country from layout-one
    $('.country-list').click(function () {
        countryVaue = $(this)[0].textContent;

        $.ajax({
            method: "GET",
            url: "https://restcountries.eu/rest/v2/name/" + countryVaue,
            success: function (country) {
                createCard(country[0]);
            },
            error: function () {
                alert("Wrong input :( Please try again !");
            }
        })
    });

    //Getting the result for a country by NAME
    $('#get-by-name').click(function () {
        criteria = $('#criteria').val();

        $.ajax({
            method: "GET",
            url: "https://restcountries.eu/rest/v2/name/" + criteria,
            success: function (country) {
                createCard(country[0]);
            },
            error: function () {
                alert("Wrong input :( Please try again !");
            }
        })
    });

    //Getting the results for countries by LANGUAGE
    $('#get-by-language').click(function () {
        criteria = $('#criteria').val();

        $.ajax({
            method: "GET",
            url: "https://restcountries.eu/rest/v2/lang/" + criteria,
            success: function (countries) {
                for (let i = 0; i < countries.length; i++) {
                    createCard(countries[i]);
                }
            },
            error: function () {
                alert("Wrong input :( Please try again !");
            }
        })
    });

    //Getting the results for countries by CURRENCY
    $('#get-by-currency').click(function () {
        criteria = $('#criteria').val();

        $.ajax({
            method: "GET",
            url: "https://restcountries.eu/rest/v2/currency/" + criteria,
            success: function (countries) {
                for (let i = 0; i < countries.length; i++) {
                    createCard(countries[i]);
                }
            },
            error: function () {
                alert("Wrong input :( Please try again !");
            }
        })
    });
});

function createCard(country) {
    var resultDiv = $('#api-results')[0] // without the [0] it return jQuery object, not HTML DOM object
    var cardDiv = document.createElement('div');
    cardDiv.className += "card";
    resultDiv.appendChild(cardDiv);
    var img = document.createElement('img');
    img.width = "200";
    img.height = "200";
    img.className += "card-img-top";
    img.src += country.flag;
    cardBody = document.createElement('div');
    cardBody.className += "card-body";
    cardDiv.appendChild(img);
    cardDiv.appendChild(cardBody);
    var name = document.createElement('h4');
    name.className += "card-title";
    name.append(country.name);
    cardDiv.appendChild(name);
    var desc = document.createElement('p');
    desc.className += "card-text";
    desc.append(country.name + " is a country based in  " + country.subregion +
        " with capital " + country.capital + ". The language they speak is " +
        country.languages[0].name + " and the official currency is " +
        country.currencies[0].name + ".");
    cardDiv.appendChild(desc);

    $('#myCountryMenu').toggleClass("show");
}