
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
<title>Search Results Page</title>
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
	<div class="container-fluid">
		<!--<div style="display: block;width: 100%">
    <button class="btn btn-primary addFlight">Add Flight</button>
   </div> -->
		<div
			style="display: block; width: 100%; text-align: center; font-size: 16px; padding-right: 7%; font-weight: bold;">
			Flight Search Results for  From : <B>${searchFlight.location}</B> &nbsp; To : <B>${searchFlight.destination}</B>  Departure On  <B>${searchFlight.departure_time}</B></div>

	</div>
 

	<div class="addFlight_Table">
		<div class="table-responsive">
			
				<table class="table" id="admin">
					<thead>
						<tr>
							<th>From</th>
							<th>Dest</th>
							<th>Departure Time</th>
							<th>Arrival Time</th>
							<th>Total Seats</th>
							<th>Booked Seats</th>
							<th>Ticket Cost </th>
							<th>Book Seats</th>
						</tr>
					</thead>
					<tbody>

					<c:forEach var="flight" items="${flightList}">
						<form:form action="${pageContext.request.contextPath}/bookFlight"
							method="post" modelAttribute="flight">
				<input readonly type="hidden" name='flight_id'
										placeholder='flight_id' class="form-control" path="flight_id" value="${flight.flight_id }"/>
										
											<input readonly type="hidden" name='departure_date'
										placeholder='departure_date' class="form-control" path="departure_date" value="${searchFlight.departure_time}"/>
							<tr id='addr0' data-id="0">
	    
								<td data-name="location">
									<input readonly type="text" name='location'
										placeholder='from' class="form-control" path="location" value="${flight.location }"/>
											</td>
								<td data-name="destination">
									<input readonly type="text" name='destination'
										placeholder='from' class="form-control" path="destination" value="${flight.destination }"/>
											</td>
								<td data-name="departure_time">
									<input readonly type="text" name='departure_time'
										placeholder='from' class="form-control" path="departure_time" value="${flight.departure_time }"/>
											</td>
								<td data-name="arrival_time">
									<input readonly type="text" name='arrival_time'
										placeholder='from' class="form-control" path="arrival_time" value="${flight.arrival_time }"/>
											</td>
								<td data-name="seats">
									<input readonly type="text" name='seats'
										placeholder='from' class="form-control" path="seats" value="${flight.seats-flight.bookedSeats }"/>
											</td>
												<td data-name="bookedSeats">
									<input readonly type="text" name='bookedSeats'
										placeholder='from' class="form-control" path="bookedSeats" value="${flight.bookedSeats }"/>
											</td>
		<td data-name="price">
									<input readonly type="text" name='ticketCost'
										placeholder='from' class="form-control" path="seats" value="${flight.ticketCost }"/>
											</td>
								<td data-name="submit"><input type="submit"
									value="Book Seats"></td>
	
							</tr>
						
						</form:form>
						
					</c:forEach>
					</tbody>
				</table>
			

		</div>
	</div>
</body>
</html>