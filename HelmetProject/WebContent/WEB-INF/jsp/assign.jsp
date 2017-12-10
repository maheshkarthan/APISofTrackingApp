<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<style>
/* The Modal (background) */
.modal {
	display: none; /* Hidden by default */
	position: fixed; /* Stay in place */
	z-index: 1; /* Sit on top */
	padding-top: 100px; /* Location of the box */
	left: 0;
	top: 0;
	width: 100%; /* Full width */
	height: 100%; /* Full height */
	overflow: auto; /* Enable scroll if needed */
	background-color: rgb(0, 0, 0); /* Fallback color */
	background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
	position: relative;
	background-color: #fefefe;
	margin: auto;
	padding: 0;
	border: 1px solid #888;
	width: 80%;
	box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0
		rgba(0, 0, 0, 0.19);
	-webkit-animation-name: animatetop;
	-webkit-animation-duration: 0.4s;
	animation-name: animatetop;
	animation-duration: 0.4s
}

/* Add Animation */
@
-webkit-keyframes animatetop {
	from {top: -300px;
	opacity: 0
}

to {
	top: 0;
	opacity: 1
}

}
@
keyframes animatetop {
	from {top: -300px;
	opacity: 0
}

to {
	top: 0;
	opacity: 1
}

}

/* The Close Button */
.close {
	color: white;
	float: right;
	font-size: 28px;
	font-weight: bold;
}

.close:hover, .close:focus {
	color: #000;
	text-decoration: none;
	cursor: pointer;
}

.modal-header {
	padding: 0px 0px;
	background-color: #5cb85c;
	color: white;
}

.modal-body {
	padding: 0px 0px;
}

.modal-footer {
	padding: 0px 0px;
	background-color: #5cb85c;
	color: white;
}

</style>

<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Journey Requests List</title>

<!-- BOOTSTRAP STYLES-->
<link href="assets/css/bootstrap.css" rel="stylesheet" />
<!-- FONTAWESOME STYLES-->
<link href="assets/css/font-awesome.css" rel="stylesheet" />
<!--CUSTOM BASIC STYLES-->
<link href="assets/css/basic.css" rel="stylesheet" />
<!--CUSTOM MAIN STYLES-->
<link href="assets/css/custom.css" rel="stylesheet" />
<!-- GOOGLE FONTS-->
<link href='http://fonts.googleapis.com/css?family=Open+Sans'
	rel='stylesheet' type='text/css' />

<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

<script type="text/javascript">
		var path = "${pageContext.request.contextPath }";
	function getDetailsofRequests() {
		$("#tabledata").empty();
		$.ajax({
					type : "GET",
					url : path + "/listJourneyRequests.html",
					success : function(response) {
						var jo = $.parseJSON(response);
						if(jo.length != 0) {
						for (var i = 0; i < jo.length; i++) {
							$('#tabledata')
									.append(
											'<tr><td align="center">'
													+ jo[i].referanceId
													+ '</td><td align="center">'
													+ jo[i].userName
													+ '</td></td><td align="center">'
													+ jo[i].mobile
													+ '</td></td><td align="center">'
													+ jo[i].fromLocation
													+ '</td></td><td align="center">'
													+ jo[i].toLocation
													+ '</td></td><td align="center"><font color="#00e600">'
													+ jo[i].vehicle
													+ '</font></td></td><td align="center"><font color="#00e600">'
													+ jo[i].vehicleName
													+ '</font></td></td><td align="center">'
													+ jo[i].journeyDate
													+ '</td></td><td align="center">'
													+ jo[i].status
													+ '</td></td><td align="center"><a onclick="passVal('+jo[i].journeyId+',\''+jo[i].vehicle+'\')" href="#">Assign</a></td></tr>');
							//alert(jo[i].journeyId);
						}
						}
					},
					error : function(e) {
					}
				});
	}
	function passVal(journeyId, vehicle){
		$("#driverslist").empty();
		$('#myModal').css('display', 'block');
		console.log(journeyId+"   "+vehicle);
		$.ajax({
			type : "GET",
			url : path + "/getDriversByVehicle.html?vehicleType="+vehicle,
			success : function(response) {
				var jo = $.parseJSON(response);
				for (var i = 0; i < jo.length; i++) {
					$('#driverslist')
							.append(
									'<tr><td align="center">'
											+ jo[i].driverName
											+ '</td></td><td align="center">'
											+ jo[i].mobile
											+ '</td></td><td align="center">'
											+ jo[i].currentLocation
											+ '</td></td><td align="center"><font color="#00e600">'
											+ jo[i].vehicleType
											+ '</font></td></td><td align="center"><font color="#00e600">'
											+ jo[i].vehicleName
											+ '</font></td></td><td align="center"><a onclick="driverSelected('+jo[i].profileId+','+journeyId+')" href="#">Assign</a></td></tr>');
					//alert(jo[i].journeyId);
				}
			},
			error : function(e) {
			}
		});
	}
	
	function driverSelected(profileId, journeyId) {
		console.log("profileId:: "+profileId+"  journeyId:: "+journeyId);
		$.ajax({
			type : "GET",
			url : path + "/journeyAssignedtoDriver.html?profileId="+profileId+"&journeyId="+journeyId,
			success : function(response) {
				var status = $.parseJSON(response);
				//alert(jo);
				if (status) {
					alert("Journey Assigned"+response);
					$('#myModal').css('display', 'none');
					getDetailsofRequests();
				} else {
					alert("Journey not Assigned"+response);
					$('#myModal').css('display', 'none');
					getDetailsofRequests();
				}
			},
			error : function(e) {
			}
		});
	}
	
	function getListOfVehicles() {
		$("#vehicleslist").empty();
		$.ajax({
					type : "GET",
					url : path + "/getAllVehicleDetails.html",
					success : function(response) {
						var jo = $.parseJSON(response);
						//alert(jo);
						if(jo != 'No Vehicles Available') {
							$("#noVehicles").hide();
						for (var i = 0; i < jo.length; i++) {
							$('#vehicleslist')
									.append(
											'<tr><td align="center"><img id="retrivedImage" src="'+jo[i].imageUrl+'" height="100" width="100"/>'
													/* + jo[i].imageUrl */
													+ '</td><td align="center">'
													+ jo[i].vehicleName
													+ '</td><td align="center">'
													+ jo[i].vehicleType
													+ '</td><td align="center">'
													+ jo[i].status
													+ '</td><td align="center"><a onclick="getVehicleDetailsById('+jo[i].id+')" href="#">Edit</a></td></tr>');
							//alert(jo[i].journeyId);
						}
						} else {
							$("#noVehicles").show();
						}
					},
					error : function(e) {
					}
				});
	}
	
	function getVehicleDetailsById(vehicleId) {
		addNewVehicleDetails();
		$.ajax({
			type : "GET",
			url : path + "/getvehicleDetails.html?vehicleId="+vehicleId,
			success : function(response) {
				var jo = $.parseJSON(response);
				if(jo.length != 0) {
					//alert(jo[0].vehicleName);
					$('#vehichleName').val(jo[0].vehicleName);
					$('#vehichleType').val(jo[0].vehicleType);
					$("input[name=status][value=" + jo[0].status + "]").prop('checked', true);
					$('#vehichleId').val(jo[0].vehicleId);
					//$('#image').val(jo[0].image);
				}
			},
			error : function(e) {
			}
		});
	}
	
	function addNewVehicleDetails() {
		$('#addEditVehicleModal').css('display', 'block');
		$('#vehichleName').val("");
		$('#vehichleType').val("");
		$('#vehichleId').val("");
	}

	$(document).ready(function() {
		$("#image").change(function(){
		    readImageURL(this);
		});
		
		 $("#myBtn").click(function() {
			$('#vehicleModal').css('display', 'block');
		}); 
		 
		$(".close").click(function() {
			$('#myModal').css('display', 'none');
			$('#vehicleModal').css('display', 'none');
			$('#addEditVehicleModal').css('display', 'none');
		});
	});
	
	function readImageURL(input) {
	    if (input.files && input.files[0]) {
	        var reader = new FileReader();
	        reader.onload = function (e) {
	            $('#uploadedImageSample').attr('src', e.target.result);
	        }
	        reader.readAsDataURL(input.files[0]);
	    }
	}
	
	function validateVehicleDetails() {
		var isVehicleNameAvailable = false;
		var vehicleName = $("#vehicleName").val();
		var vehicleType = $("#vehicleType").val();
		var image = $("#image").val();
		var input = document.getElementById('image');
		var file = input.files[0];
		var vehicleId = $("#vehicleId").val();
		if (vehicleName.trim() == "" || vehicleName.trim() == null) {
			$("#vehicleName").css("border", "1px solid red");
			alert("vehicleName is empty");
			return false
		} else {
			//alert(file.size);
			if (vehicleId == 0) {		
				$.ajax({
					type : "GET",
					async : false,
					cache : false,
					url : path + "/validateVehicleName.html?vehicleName="+vehicleName.trim(),
					success : function(response) {
						isVehicleNameAvailable = $.parseJSON(response);
						//returnFormValidations(isAvailable);
					}
				});
			} else {
				$.ajax({
					type : "GET",
					async : false,
					cache : false,
					url : path + "/validateEditVehicleName.html?vehicleName="+vehicleName.trim()+"&vehicleId="+vehicleId,
					success : function(response) {
						isVehicleNameAvailable = $.parseJSON(response);
					}
				});
				//setTimeout(returnFormValidations(isAvailable), 5000);
			}
			
		}
		
		if (vehicleType.trim() == "--SELECT--" || vehicleType.trim() == null) {
			$("#vehicleType").css("border", "1px solid red");
			alert("vehicleType is empty");
			return false;
		}
		
		if (image == "" || image == null) {
			$("#image").css("border", "1px solid red");
			alert("Select an image");
			return false;
		}
//alert("ttt"+returnFormValidations(isVehicleNameAvailable)+"yyyy"+Boolean(returnFormValidations(isVehicleNameAvailable)));
		return Boolean(returnFormValidations(isVehicleNameAvailable));
	}

	function returnFormValidations(isVehicleNameAvailable) {
		if (isVehicleNameAvailable == 'true') {
			$("#vehicleName").css("border", "1px solid black");
			$("#vehicleType").css("border", "1px solid black");
			$("#image").css("border", "1px solid black");
			$("#nameAvailabilityStatus").text("");
			return true;
		} else {
			$("#nameAvailabilityStatus").text("Vehicle Name Registered.");
			$("#nameAvailabilityStatus").css("color", "red");
			$("#vehicleName").css("border", "1px solid red");
			return false;
		}
	}
</script>
</head>
<body onload="getDetailsofRequests()">


	 

	<!-- The Modal for Drivers List -->
	<div id="myModal" class="modal">
		<!-- Modal content -->
		<div class="modal-content panel panel-default">
			<div class="modal-header">
				<span class="close">&times;</span>
				<h4>List of Drivers Available</h4>
			</div>
			<div class="panel-body">
				<div class="table-responsive overlay movedown" style="overflow:scroll; height:400px;">
									<table class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th align="center">Driver Name</th>
												<th align="center">Driver Mobile</th>
												<th align="center">Current Location</th>
												<th align="center">Vehicle</th>
												<th align="center">Vehicle Name</th>
												<th align="center">Select Driver</th>
											</tr>
										</thead>
										<tbody id="driverslist">
											<!-- Dynamic Data -->
										</tbody>
									</table>
								</div>
			</div>
			<!-- <div class="modal-footer">
				<h4>Modal Footer</h4>
			</div> -->
		</div>
	</div>

<!--Model for Vehicles List  -->
<div id="vehicleModal" class="modal">
		<!-- Modal content -->
		<div class="modal-content panel panel-default">
			<div class="modal-header">
				<span class="close">&times;</span>
				<h4> Vehicles List</h4>
			</div>
			<div class="panel-body">
				<div class="table-responsive overlay movedown" style="overflow:scroll; height:400px;">
									<table class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th align="center">Vehicle Image</th>
												<th align="center">Vehicle Name</th>
												<th align="center">Vehicle Type</th>
												<th align="center">Status</th>
												<th align="center">View | Edit</th>
											</tr>
										</thead>
										<tbody id="vehicleslist">
											<!-- Dynamic Data -->
										</tbody>
									</table>
									<div id="noVehicles" align="center"><p id="vehicleAvailabilityStatus"> <font color="red" size="5"> No Vehicles Available </font></p></div>
				</div>
			</div>
			 <div class="modal-footer">
			 <a href="#" onclick="addNewVehicleDetails()" style="color: white"><i class="fa fa-plus fa-1.9x"> ADD </i></a>
			</div> 
		</div>
	</div>
	
	<!--Model for Adding/Editing Vehicles  -->
<div id="addEditVehicleModal" align="center" class="modal">
		<!-- Modal content -->
		<div class="panel-body col-md-6">
			<div class="modal-header">
				<span class="close">&times;</span>
				<h4> Add Vehicle</h4>
			</div>
			<div class="panel panel-success">
				<div class="table-responsive overlay movedown">
				<form action="uploadVehicleDetails.html" name="addingVehicleForm" id="addingVehicleForm" onsubmit="return validateVehicleDetails()" method="POST" enctype="multipart/form-data">
									<table class="table">
										<thead>
											<tr>
												<th align="center" colspan="2"><font color="purple" size="4">Vehicle Registration</font></th>
											</tr>
										</thead>
										<tbody id="addEditVehicleDetails">
											<tr><td align="right"><label>Vehicle Name</label></td> <td><input type="text" id="vehicleName" name="vehicleName"><p id="nameAvailabilityStatus"> </p></td></tr>
											<tr><td align="right"><label>Vehicle Type/Category</label></td> <td><select id="vehicleType" name="vehicleType">
											<option>--SELECT--</option>
											<option>CAB</option>
											<option>BUS</option>
											<option>ROBOT</option>
											<option>OTHER</option>
											</select></td></tr>
											<tr><td align="right"><label>Status</label></td> <td><input type="radio" id="status" name="status" value="Available" checked="checked"> Available  &nbsp; <input type="radio" id="status" name="status" value="NotAvailable"> Not Available</td></tr>
											<tr><td align="right"><label>Vehicle Image</label></td> <td><input type="file" id="image" name="image" /><img id="uploadedImageSample" src="images/defaultImageToUpload.png" height="100" width="100"/></td></tr>
											<tr><td colspan="2" align="center"><input type="hidden" id="vehicleId" name="vehicleId" value="0"><input type="submit" ></td></tr>
										</tbody>
									</table>
				</form>
				</div>
			</div>
			 <!-- <div class="modal-footer">
			 <a href="#" onclick="addNewVehicleDetails()" style="color: white"><i class="fa fa-plus fa-1.9x"> ADD </i></a>
			</div>  -->
		</div>
	</div>

	<div id="wrapper">
		<nav class="navbar navbar-default navbar-cls-top " role="navigation"
			style="margin-bottom: 0">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".sidebar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index.html">Cab Project</a>
			</div>

		</nav>
		<!-- /. NAV TOP  -->
		<!-- /. NAV SIDE  -->
		<div id="page-wrapper">
			<div id="page-inner">
				<div class="row">
					<div class="col-md-12">
						<h3 class="page-head-line">Assign Journey to Driver</h3>
					</div> 
				</div>
				<!-- /. ROW  -->

				<div class="row">
					<div class="col-md-12">
						<!--   Kitchen Sink -->
						<div class="panel panel-default">
							<div class="panel-heading">
								<b>Journey Requests List</b><button id="myBtn" style="float: right;" onclick="getListOfVehicles()">Upload Vehicles</button>
							</div>
							<div class="panel-body">
								<div class="table-responsive">
									<table class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th align="center">#Reference Id</th>
												<th align="center">User Name</th>
												<th align="center">User Mobile</th>
												<th align="center">From Location</th>
												<th align="center">To Location</th>
												<th align="center">Vehicle</th>
												<th align="center">Vehicle Name</th>
												<th align="center">Journey Date</th>
												<th align="center">Status</th>
												<th align="center">Assign Driver</th>
											</tr>
										</thead>
										<tbody id="tabledata">
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
	<!-- JQUERY SCRIPTS -->
	<script src="assets/js/jquery-1.10.2.js"></script>
	<!-- BOOTSTRAP SCRIPTS -->
	<script src="assets/js/bootstrap.js"></script>
	<!-- METISMENU SCRIPTS -->
	<script src="assets/js/jquery.metisMenu.js"></script>

</body>
</html>
