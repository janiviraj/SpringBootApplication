<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login failed</title>
<style>
@import url('https://fonts.googleapis.com/css?family=Numans');

html, body {
	background-image: url("https://carolinehgroth.com.au/wp-content/uploads/2018/06/flight-landing-INBNDCHECK1117.jpg");
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
	width: 300px;
	height: 370px;
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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="style.css">
<title>Login Page</title>

</head>
<body>
<p>Login failed !!! You login has failed due to session issue. Kindly Loging to continue</p>
<div class="register">
					Logged Out?<a href="${pageContext.request.contextPath}/login/page">Sign In</a>
				</div>
</body>
</html>