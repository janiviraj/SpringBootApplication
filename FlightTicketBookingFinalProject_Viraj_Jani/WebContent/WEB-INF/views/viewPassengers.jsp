
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
					<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>    

<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="admin.css">
<title>Add Flight Page</title>
<style>
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
.btn:hover {
	background: #568bb7;
	color: #ffffff;
}

.addFlight {
	float: left;
	background: green !important;
	border: 1px solid #ffffff;
}

.addFlight_Table {
	padding: 10px;
	background-color: rgba(0, 0, 0, 0.2) !important;
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

	<div class="container-fluid">
		
		<div
			style="display: block; width: 100%; text-align: center; font-size: 16px; padding-right: 7%; font-weight: bold;">
			All Passenger List</div>

	</div>
 

	<div class="addFlight_Table">
		<div class="table-responsive">
				<table class="table" id="admin">
					<thead>
						<tr>
							<th>Booking ID</th>
							<th>Passanger Name</th>
							<th>Contact No.</th>
							<th>Email</th>
							<th>From</th>
							<th>Dest</th>
							<th>Arrival Time</th>
							<th>Departure Time</th> 
							<th>Flight ID</th>
								<th>View Ticket</th>
									<th>Email Ticket </th>
									<th>Cancle Ticket </th>
						</tr>
					</thead>
					<tbody>
					
					<c:forEach var="flight" items="${flightList}">
					
						<tr id='addr0' data-id="0">
	<td >${flight.bid }</td>
							<td >${flight.pname }</td>
							<td >${flight.pphone }</td>
							<td >${flight.pemail }</td>
							<td >${flight.location }</td>
							<td >${flight.destination }</td>
							<td >${flight.departure_time }</td>
							<td >${flight.departure_time }</td>
							<td >${flight.flight_id }</td>
							<td ><a href='${pageContext.request.contextPath}/downloadETicket?bookingId=${flight.bid}&send=false' target="_new">View Ticket</a></td>
							<td ><a
								href='${pageContext.request.contextPath}/downloadETicket?bookingId=${flight.bid}'  target="_new">Email
									Ticket</a>
							<td><a
								href='${pageContext.request.contextPath}/cancleETicket?bookingId=${flight.bid}'  target="_new">Cancle
									Ticket</a>
							
							<%-- <a
								href='${pageContext.request.contextPath}/downloadETicket?bookingId=${flight.bid}&send=true'  target="_new">Email
									Ticket</a>
							 --%>
							</td>
						</tr>
						
					</c:forEach>
					</tbody>
				</table>
			

		</div>
	</div>
</body>
</html>