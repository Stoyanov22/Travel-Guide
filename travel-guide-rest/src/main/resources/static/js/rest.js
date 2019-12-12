$(document).ready(function () {
	$.ajax({
		url: "/rest/getTrips",
		method: "GET",
		success: function (data) {
			console.log(data);
			alert("woho");
			data.forEach(function (trip) {
				console.log(trip.id);
				console.log(trip.name);
				console.log(trip.country);
				console.log(trip.country.name);
			});

		},
		fail: function (err) {
			alert(err);
		}
	});

	$('#createTripBtn').click(function (e) {
		name = document.getElementById("tripName").value;
		countryId = document.getElementById("countryId").value;
		countryName = $("#countryId option:selected").html();

		$.ajax({
			url: "/rest/createTrip",
			method: "POST",
			data: {
				name: name,
				countryId: countryId
			},
			success: function (data) {
				if (data == 0) {
					alert("Adding Trip Failed");
				} else {
					createTripCard(name, countryName);
					$('#tripName').val("");
				}
			},
			fail: function (err) {
				alert(err);
			}
		});

	});
})

function createTripCard(tripName, countryName) {
    var trips = $('#restTrips')[0] // without the [0] it return jQuery object, not HTML DOM object
    var cardDiv = document.createElement('div');
	cardDiv.className += "card";
    trips.appendChild(cardDiv);
    var name = document.createElement('h3');
    name.className += "card-title";
    name.append(tripName);
    cardDiv.appendChild(name);
    var country = document.createElement('p');
    country.append(countryName);
	cardDiv.appendChild(country);
	var edit = document.createElement('p');
	edit.id += "editTripBtn";
	edit.append("Edit");
	cardDiv.appendChild(edit);
	var del = document.createElement('p');
	del.id += "deleteTripBtn";
	del.append("Delete");
	cardDiv.appendChild(del);
}