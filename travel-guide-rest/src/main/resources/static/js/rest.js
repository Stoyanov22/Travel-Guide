$(document).ready(function () {

	var editId = 0;

	$.ajax({
		url: "/rest/getTrips",
		method: "GET",
		success: function (data) {
			data.forEach(function (trip) {
				createTripCard(trip.id, trip.name, trip.country.name);
			});

		},
		error: function (err) {
			alert(err);
		}
	});

	$('#createTripBtn').click(function () {
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
					alert("Adding Trip failed");
				} else {
					createTripCard(data, name, countryName);
					$('#tripName').val("");
				}
			},
			error: function (err) {
				alert(err);
			}
		});
	});

	$('.container').on('click', '.editTrip', function () {
		editId = $(this).attr('id');
		$.ajax({
			method: "GET",
			url: "/rest/editTrip",
			data: {
				id: editId
			},
			async: false,
			success: function (trip) {
				document.getElementById("editTripName").value = trip.name;
				document.getElementById("editCountryId").value = trip.country.id;
			},
			error: function () {
				alert("Wrong input :( Please try again !");
			}
		})
		$('#edit-modal').modal('show');

	});

	$('#editTripBtn').click(function (e) {
		name = document.getElementById("editTripName").value;
		countryId = document.getElementById("editCountryId").value;
		countryName = $("#editCountryId option:selected").html();

		$.ajax({
			url: "/rest/editTrip",
			method: "POST",
			data: {
				id: editId,
				name: name,
				countryId: countryId
			},
			success: function (data) {
				$('#edit-modal').modal('hide');

				if (data == 0) {
					alert("Search failed");
				} else {
					removeTripCard(editId);
					createTripCard(editId, name, countryName);
				}
			},
			error: function (err) {
				alert(err);
			}
		});
	});

	$('.container').on('click', '.deleteTrip', function () {
		var deleteId = $(this).attr('id');
		$.ajax({
			url: "/rest/deleteTrip",
			method: "POST",
			data: {
				id: deleteId
			},
			success: function (data) {
				if (data == 0) {
					alert("Deleting Trip failed");
				} else {
					removeTripCard(deleteId);
				}
			},
			error: function (err) {
				alert(err);
			}
		});
	});

	$('#search-input-btn').click(function (e) {
		searchInput = document.getElementById("search-input").value;
		$.ajax({
			url: "/rest/search",
			method: "POST",
			data: {
				input: searchInput
			},
			success: function (data) {
				if (data == null) {
					alert("Deleting Trip failed");
				} else {
					$('#restTrips').empty();
					data.forEach(function (trip) {
						createTripCard(trip.id, trip.name, trip.country.name);
					});
				}
			},
			error: function (err) {
				alert(err);
			}
		});
	});
})

function createTripCard(tripId, tripName, countryName) {
	var trips = $('#restTrips')[0] // without the [0] it return jQuery object, not HTML DOM object
	var cardDiv = document.createElement('div');
	cardDiv.id += tripId;
	cardDiv.className += "card";
	trips.appendChild(cardDiv);
	var img = document.createElement('img');
	$.ajax({
		method: "GET",
		url: "https://restcountries.eu/rest/v2/name/" + countryName,
		async: false,
		success: function (c) {
			img.src += c[0].flag;

		},
		error: function () {
			alert("Wrong input :( Please try again !");
		}
	})
	img.width = "200";
	img.height = "200";
	img.className += "card-img-top";
	cardDiv.appendChild(img);
	var name = document.createElement('h3');
	name.className += "card-title";
	name.append(tripName);
	cardDiv.appendChild(name);
	var edit = document.createElement('a');
	edit.href += "#";
	edit.id += tripId;
	edit.className += "editTrip ";
	edit.className += "btn ";
	edit.className += "btn-success"
	edit.append("Edit");
	cardDiv.appendChild(edit);
	var del = document.createElement('a');
	del.href += "#";
	del.id += tripId;
	del.className += "deleteTrip ";
	del.className += "btn ";
	del.className += "btn-danger"
	del.append("Delete");
	cardDiv.appendChild(del);
}

function removeTripCard(tripId) {
	$('#' + tripId).fadeOut();
}