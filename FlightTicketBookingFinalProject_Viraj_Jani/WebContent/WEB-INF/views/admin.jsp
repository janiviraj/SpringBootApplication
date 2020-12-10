<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="admin.css">
<title>Admin Home</title>
<style>
@import url('https://fonts.googleapis.com/css?family=Numans');

@import url('https://fonts.googleapis.com/css?family=Numans');

html, body {
	background-image: url('https://carolinehgroth.com.au/wp-content/uploads/2018/06/flight-landing-INBNDCHECK1117.jpg');
	background-size: cover;
	background-repeat: no-repeat;
	height: 100%;
	font-family: 'Numans', sans-serif;
}

h3 {
	float: right;
	font-weight: 300 !important;
	display: inline;
}

.logo {
	float: left;
	color: orange;
	display: inline;
	font-style: italic;
}

.container {
	margin: 0px;
	padding-left: 10%;
	padding-right: 10%;
	width: 100%;
	color: #ffffff;
	background-color: rgba(0, 0, 0, 0.5) !important;
}

.form_container {
	background-color: rgba(0, 0, 0, 0.5) !important;
	padding: 5%;
	margin-top: auto;
	margin-bottom: auto;
	display: flex;
	justify-content: center;
	align-items: center;
	box-shadow: 0px 0px 7px #777;
}

.container-fluid {
	padding-top: 7%;
	display: flex;
	justify-content: flex-start;
	align-items: center;
}

.btn {
	display: inline-block;
	margin-bottom: 15px;
	font-weight: 400;
	text-align: center;
	background: #ffffff;
}

.btn:hover {
	background: #568bb7;
	color: #ffffff;
}

.logout {
	font-size: 14px;
	color: #ffffff;
	font-style: italic;
}

.logout:hover {
	font-size: 14px;
	color: red;
	font-style: italic;
}

.addFlight {
	float: left;
	background: green !important;
	border: 1px solid #ffffff;
}

.addFlight_Table {
	padding: 10px;
	background-color: rgba(0, 0, 0, 0.7) !important;
}

#admin {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	border-collapse: collapse;
	width: 100%;
	margin-bottom: 0px;
}

#admin td, #admin th {
	border: 1px solid #ddd;
	padding: 7px;
}

#admin tr:nth-child(even) {
	background-color: #f2f2f2;
}

#admin tr:hover {
	background-color: #ddd;
}

#admin th {
	text-align: left;
	background-color: #4CAF50;
	color: white;
	vertical-align: middle;
	text-align: center;
}

#admin td {
	vertical-align: middle;
	text-align: center;
}
</style>


</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div></div>
	<div class="container-fluid">
		<div class="form_container">
			<div class="btn-group-vertical">
				<a href="${pageContext.request.contextPath}/addFlight"><button
						type="button" class="btn">Add Airplane</button></a> <a
					href="${pageContext.request.contextPath}/updateFlights"><button
						type="button" class="btn ">View and Edit Flight</button></a> <a
					href="${pageContext.request.contextPath}/viewPassengers"><button
						type="button" class="btn ">View Passenger List</button></a>
			</div>
		</div>
	</div>
</body>
</html>