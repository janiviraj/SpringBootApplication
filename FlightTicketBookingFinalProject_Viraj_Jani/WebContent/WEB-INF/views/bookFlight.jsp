<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
<link rel="stylesheet" type="text/css" href="customer.css">
<title>Book Flight</title>
<style>
@import url('https://fonts.googleapis.com/css?family=Numans');

html, body {
	background-image: url('https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fstatic.onecms.io%2Fwp-content%2Fuploads%2Fsites%2F28%2F2017%2F11%2Fflight-landing-INBNDCHECK1117.jpg');
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

.title {
	float: left;
	margin-bottom: 7%;
	padding-left: 3%;
}

.container {
	margin: 0px;
	padding-left: 10%;
	padding-right: 10%;
	width: 100%;
	color: #ffffff;
	background-color: rgba(0, 0, 0, 0.5) !important;
}

.container-fluid {
	padding-top: 2%;
	display: flex;
	justify-content: center;
}

.inquiry {
	background-color: rgba(0, 0, 0, 0.5) !important;
	color: #ffffff;
	padding: 5%;
	padding-top: 2%;
	padding-bottom: 2%;
	margin-top: auto;
	margin-bottom: auto;
	display: flex;
	justify-content: center;
	align-items: center;
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

.submit {
	padding: 10px;
	text-align: center;
}

.form-group {
	text-align: left;
}
</style>

</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>

	<div class="container-fluid">
		<div class="inquiry">
			<!-- sendmail -->
			<form:form action="${pageContext.request.contextPath}/finalBook"
				method="post" modelAttribute="bookFlight" name='ticketForm'
				id="bookFlight">
				<h3 class="title text-center">Book A Flight</h3>
				<div class="col-md-12" style="text-align: center;">

					<h4>
						<b>${flight.location}</b> to <b>${flight.destination}</b>
					</h4>
					<h5>${bookFlight.departure_time}</h5>
					<input readonly type="hidden" name='flight_id'
						placeholder='flight_id' class="form-control" path="flight_id"
						value="${flight.flight_id }" />
					<div class="form-group">
						<form:input type="hidden" name='location' placeholder='location'
							class="form-control" path="location" value="${flight.location }" />
					</div>

					<div class="form-group">
						<form:input type="hidden" name='destination'
							placeholder='destination' class="form-control" path="destination"
							value="${flight.destination }" />
					</div>

					<div class="form-group">
						<form:input type="hidden" name='departure_time'
							placeholder='departure_time' class="form-control"
							path="departure_time" value="${bookFlight.departure_time }" />
					</div>

					<div class="form-group">
						Passenger Name: <form:input type="text" name="pname"
							class="form-control" required="required"
							placeholder="Passenger Name" path="pname" />
					</div>
					<div class="form-group">
						Age:
						<form:input type="number" name="page" class="form-control"
							min="18" max="100" title="Age should be in between 18-100"
							required="required" placeholder="Age" path="page" />
					</div>
					<div class="form-group">
						Mobile Number:
						<form:input type="number" name="pphone" class="form-control"
							required="required" placeholder="Mobile Number" path="pphone" />
					</div>
					<div class="form-group">
						Email:
						<form:input type="email" name="pemail" class="form-control"
							required="required" placeholder="Email" path="pemail" />
					</div>

					<!-- 					<input type="submit" value="Book Seat" name="submit" -->
					<!-- 						class="btn btn-success"> -->

					<input type="button" value="Make Payment" name="MakePaymebnt"
						onclick="fnLoad();" class="btn btn-success">

				</div>

			</form:form>
		</div>

		<!-- Modal -->
		<div class="modal fade" id="myModal" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Payment Screen</h4>
					</div>
					<div class="modal-body" id='paymentScreen'>
						<div class="row">

							<div class="col-xs-12 col-md-8">


								<!-- CREDIT CARD FORM STARTS HERE -->
								<div class="panel panel-default credit-card-box">
									<div class="panel-heading display-table">
										<div class="row display-tr">
											<h3 class="panel-title display-td">Payment Details</h3>
											<div class="display-td">
												<img class="img-responsive pull-right"
													src="http://i76.imgup.net/accepted_c22e0.png">
											</div>
										</div>
									</div>
									<div class="panel-body">
										<form role="form" id="payment-form"
											action="${pageContext.request.contextPath}/finalBook"
											method="post" modelAttribute="bookFlight" name='ticketForm'
											id="bookFlight" onsubmit="return fnProceed();">
											<div class="row">
												<div class="col-xs-12">
													<div class="form-group">
														<label for="cardNumber">CARD NUMBER</label>
														<div class="input-group">
															<input type="text" class="form-control" name="cardNumber"
																placeholder="Valid Card Number" autocomplete="cc-number"
																pattern="[0-9]{16}" title="Card No Should be 16 digits"
																required autofocus /> <span class="input-group-addon"><i
																class="fa fa-credit-card"></i></span>
														</div>
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-xs-7 col-md-7">
													<div class="form-group">
														<label for="cardExpiry"><span class="hidden-xs">EXPIRATION</span><span
															class="visible-xs-inline">EXP</span> DATE</label> <input type="tel" class="form-control" name="cardExpiry"
															placeholder="MM / YY" autocomplete="cc-exp" pattern="([0-9]{2}[/]?){2}" required />
													
													
													</div>
												</div>
												<div class="col-xs-5 col-md-5 pull-right">
													<div class="form-group">
														
														<label for="cardCVC">CV CODE</label> <input type="number"
															class="form-control" name="cardCVC" max="999" pattern=".{3,}"
															placeholder="CVC" title="Please enter 3 digits"
															autocomplete="cc-number" required ="required"/>
													</div>
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-xs-12">
													<div class="form-group">
														<label for="couponCode">COUPON CODE</label> <input
															type="number" class="form-control" name="couponCode" />
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-xs-12">
													<button class="btn btn-success btn-lg btn-block"
														type="submit">Book Ticket</button>
												</div>
											</div>
											<div class="row" style="display: none;">
												<div class="col-xs-12">
													<p class="payment-errors"></p>
												</div>
											</div>
										</form>
									</div>
								</div>
								<!-- CREDIT CARD FORM ENDS HERE -->


							</div>



						</div>
					</div>

				</div>

			</div>
		</div>


	</div>







	<script>
		function fnLoad() {
			if (document.getElementById('bookFlight').checkValidity()) {
				$
						.ajax(
								{
									url : "${pageContext.request.contextPath}/checkBookedHistory",
									data : $('#bookFlight').serialize(),
									method : "POST"
								})
						.done(
								function(data) {

									if (data.success == 'true') {
										alert('You have already booked a ticket for the same passenger. ');
									} else {
										$('#myModal').modal('show');

									}
								});
			} else {
				alert('Kindly fill up the form. ');
			}

		}

		function fnProceed() {
			alert('Payment Received Successfully. Proceeding....');
			$('#myModal').modal('hide');
			document.getElementById('bookFlight').submit();

			return false;
		}
	</script>
</body>
</html>