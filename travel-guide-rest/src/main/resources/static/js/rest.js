$(document).ready(function () {

	var editId = 0;

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
					createTripCard(data, name, countryName);
					$('#tripName').val("");
				}
			},
			fail: function (err) {
				alert(err);
			}
		});
	});

	$('.container').on('click', '.editTrip', function (){
		editId = $(this).attr('id');
		$('#editTrip').show();    
	});

	$('#editTripBtn').click(function (e) {
		name = document.getElementById("editTripName").value;
		countryId = document.getElementById("editCountryId").value;
		countryName = $("#editCountryId option:selected").html();

		$.ajax({
			url: "/rest/editTrip",
			method: "POST",
			data: {
				id : editId,
				name: name,
				countryId: countryId
			},
			success: function (data) {
				if (data == 0) {
					alert("Updating Trip Failed");
				} else {
					removeTripCard(editId);
					createTripCard(editId, name, countryName);
					$('#editTrip').hide();    
				}
			},
			fail: function (err) {
				alert(err);
			}
		});
	});

	$('.container').on('click', '.deleteTrip', function (){
		var deleteId = $(this).attr('id');
		$.ajax({
			url: "/rest/deleteTrip",
			method: "POST",
			data: {
				id : deleteId
			},
			success: function (data) {
				if (data == 0) {
					alert("Deleting Trip Failed");
				} else {
					removeTripCard(deleteId);
				}
			},
			fail: function (err) {
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
    var name = document.createElement('h3');
    name.className += "card-title";
    name.append(tripName);
    cardDiv.appendChild(name);
    var country = document.createElement('p');
    country.append(countryName);
	cardDiv.appendChild(country);
	var edit = document.createElement('a');
	edit.href += "#";
	edit.id += tripId;
	edit.className += "editTrip";
	edit.append("Edit");
	cardDiv.appendChild(edit);
	var del = document.createElement('a');
	del.href += "#";
	del.id += tripId;
	del.className += "deleteTrip";
	del.append("Delete");
	cardDiv.appendChild(del);
}

function removeTripCard(tripId){
	$('#' + tripId).fadeOut();
}