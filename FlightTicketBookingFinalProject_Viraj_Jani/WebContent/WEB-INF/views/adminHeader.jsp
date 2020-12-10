
<%@page import="com.neu.flightbooking.model.UserDetails"%>


<%
if(session.getAttribute("userdetails")==null){
	request.getRequestDispatcher(request.getContextPath()+"/login/").forward(request, response);
}
UserDetails us=(UserDetails)session.getAttribute("userdetails");
boolean isAdmin=false;
if(us.getRole().equalsIgnoreCase("admin")){
	isAdmin=true;
}
%>

<%if(isAdmin){ %>
<div class="container">
	<h3 class="logo">myFly</h3>
	<h3>
		Hi,  ${userdetails.userid} [Admin]<a
			href="${pageContext.request.contextPath}/login/logout"><span
			class="logout">Logout</span></a>
	</h3>
</div>
<a href="${pageContext.request.contextPath}/addFlight">Add Airplane</a>
&nbsp;|&nbsp;





<a href="${pageContext.request.contextPath}/updateFlights">View and
	Edit Flight</a>
&nbsp;|&nbsp;
<a href="${pageContext.request.contextPath}/viewPassengers">View
	Bookings </a>
	&nbsp;|&nbsp;


	<%} else{%>
	<div class="container">
	<h3 class="logo">myFly</h3>
	<h3>
		Hi,  ${userdetails.userid} [User]<a
			href="${pageContext.request.contextPath}/login/logout"><span
			class="logout">Logout</span></a>
	</h3>
</div>
&nbsp;|&nbsp;


&nbsp;|&nbsp;
<a href="${pageContext.request.contextPath}/viewPassengers">My Bookings
	</a>
&nbsp;|&nbsp;

<%} %>
