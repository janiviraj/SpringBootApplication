<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>

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
	<div class="container">
		<div class="form_container" align="center">

			<form:form action="${pageContext.request.contextPath}/login/validatedLogin" id="loginForm"
				method="post" modelAttribute="userdetails">
				<h3>Login</h3>
				<div class="form-group">
					Username:
					<form:input type="text" class="form-control" path="userid"
						placeholder="username" />
				</div>
				<div class="form-group">
					Password:
					<form:input type="password" class="form-control" path="password"
						placeholder="password" />
				</div>
				<div class="form-group">
<!-- 					<input type="submit" value="Sign in" -->
<!-- 						class="btn float-right login_btn"> -->
							<input type="button" value="Login" onclick="javascript:fnLogin();"
						class="btn float-right login_btn">
				</div>
				<div class="register">
					Don't have an account?<a href="${pageContext.request.contextPath}/login/signup">Sign Up</a>
				</div>

			</form:form>
		</div>
	</div>
	
	<script>
function fnLogin(){
	$.ajax({
		  url: "${pageContext.request.contextPath}/login/checkLogin",
		 data:$('#loginForm').serialize(),
		 method:"POST"
		})
		  .done(function( data ) {
			
		 	if(data.success=='true'){
			 		window.location.href='${pageContext.request.contextPath}/login/redirectLogin';
			 	}else{
				 	alert("Invalid Credentials");
				 	
				 	}
		  });
}
   
	</script>
</body>
</html>