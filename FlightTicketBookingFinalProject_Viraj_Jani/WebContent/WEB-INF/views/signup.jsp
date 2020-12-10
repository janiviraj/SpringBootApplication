<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title>Sign Up Page</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="style.css">
<style>
@import url('https://fonts.googleapis.com/css?family=Numans');

html, body {
	background-image: url('https://carolinehgroth.com.au/wp-content/uploads/2018/06/flight-landing-INBNDCHECK1117.jpg');
	background-size: cover;
	background-repeat: no-repeat;
	height: 100%;
	font-family: 'Numans', sans-serif;
}

.container {
	height: 100%;
	align-content: center;
	display: flex;
	justify-content: center;
	align-items: center;
}

.form-group {
	color: #ffffff;
}

.form_container {
	background-color: rgba(0, 0, 0, 0.5) !important;
	padding: 7%;
	padding-top: 2%;
	padding-bottom: 2%;
	margin-top: auto;
	margin-bottom: auto;
	display: flex;
	justify-content: center;
	align-items: center;
	box-shadow: 0px 0px 7px #777;
}

h3 {
	color: #ffffff;
}

input:focus {
	outline: 0 0 0 0 !important;
	box-shadow: 0 0 0 0 !important;
}

.login_btn {
	color: black;
	background-color: #ffc312;
	width: 100px;
}

.login_btn:hover {
	color: black;
	background-color: white;
}

.register {
	color: white;
}

.register a {
	margin-left: 4px;
}
</style>
</head>
<body>
	<div class="container">
		<div class="form_container">

			<form:form action="${pageContext.request.contextPath}/login/sign-up"
				method="post" modelAttribute="userdetails" id="loginForm">
				<h3>Sign Up</h3>
				<div class="form-group">
					Firstname:
					<form:input type="text" path="firstname" class="form-control" required="required"
						placeholder="Enter first name" />
				</div>
				<div class="form-group">
					Lastname:
					<form:input type="text" path="lastname" class="form-control" required="required"
						placeholder="Enter last name" />
				</div>
				<div class="form-group">
					Gender:
<%-- 					<form:input type="text" path="gender" class="form-control" required="required" /> --%>
					<select path="gender" name="gender" class="form-control" >
					<option value="male">Male</option>
					<option value="female">Female</option>
					<option value="NA">Other</option>
					
					</select>
				</div>
				<div class="form-group">
					User Id:
					<form:input type="text" path="userid" onblur="fnLogin()" id="userid" class="form-control" required="required" />
				</div>

				<div class="form-group">
					Password:
					<form:input type="password" path="password" class="form-control" required="required" />
				</div>

				<div class="form-group">
					<input type="submit" value="Sign Up"
						class="btn float-right login_btn">
				</div>
				<div class="register">
					Already Register?<a href="${pageContext.request.contextPath}/login/page">Sign In</a>
				</div>
			</form:form>

		</div>
	</div>

<script>
function fnLogin(){
	if(document.getElementById('userid').value.length>0){
	$.ajax({
		  url: "${pageContext.request.contextPath}/login/checkUserExists",
		 data:$('#loginForm').serialize(),
		 method:"POST"
		})
		  .done(function( data ) {
			
		 	if(data.success=='true'){
		 		document.getElementById('userid').value='';
			 	alert('UserId already exists. Please choose other user id or proceed to signin page.');
			 	document.getElementById('userid').focus();
			 	}
		  });
	}
}
</script>


</body>
</html>