<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="edu.dtcc.csc241.model.SystemDriver" %>
    
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type=text/css>
		body {
		margin: 2em 5em;  font-family:Georgia, Times New Roman, Times, serif; }
		h1, legend {
			font-family:Arial, Helvetica, sans-serif; } 
		label, input, select {
		display:block; }
		input, select { 
		margin-bottom: 1em; }
		input[type=checkbox] {
		display:inline; }
		</style>
</head>
<body>

<%
Double simulationStopTime = null;
SystemDriver aSystemDriver = new SystemDriver();

String stopTime = request.getParameter("StopTime");
if (stopTime != null) {
	simulationStopTime = Double.parseDouble(stopTime);
	aSystemDriver = new SystemDriver();
	aSystemDriver.setAverageServiceTime(60.0);
	aSystemDriver.setAverageTimeBetweenArrival(50.0);
	aSystemDriver.setupSystemRunTimeTo(simulationStopTime);
	aSystemDriver.start();
}

%>
 
		<form method=post  id=simulate>
		<h1>Enter # of seconds to perform the simulation</h1>
		<fieldset>
		<legend>Simulation Details</legend>
		<div>
		<label>Simulate System till:
		<input id=stopTime name=StopTime type=text placeholder=200.0 required autofocus>
		</label>
		</div>
		<div>
		<button type=submit form=simulate name=sendData value=Submit>Start Simulation</button> 
		</div>
		
		<%
		if (simulationStopTime != null) {
		%>
			<div>
			<p>The last time to simulate was: 
<%out.println(simulationStopTime.toString()); %> 
</p>
			<p>The average waiting time was: 
		 
			<% out.println(aSystemDriver.getCustomerStatitician().getMean().toString()); %> 
			
			</p>
			<p>The server busy percentage was: 
			<% Double val = ((aSystemDriver.getServerStatitician().getTotal()) / simulationStopTime) * 100.0;  
			out.println(val); %>  
			</fieldset>
			
			</p>
			</div>
		<%
		}
		%>
		 
		</form> 

</body>
</html>