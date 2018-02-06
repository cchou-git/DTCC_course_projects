<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="edu.dtcc.csc241.model.SystemDriver" %>
    
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="rstech_learning_management">
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
		
		<link id="CssLink" name="CssLink" rel="stylesheet" href="./css/main.css" /> 
		
  
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
		<h1 class="rs_tech_section_header" >Enter # of seconds to perform the simulation</h1>
		<fieldset>
		<legend>Simulation Details</legend>
		<div>
		<label>Simulate System till:
		<input id=stopTime name=StopTime type=text placeholder=200.0 required autofocus>
		</label>
		</div>
		<div>
		<button type=submit form=simulate name=sendData value=Submit>Start Simulation</button> 
		<span id="abc">this is a test</span>
		</div>
		
		<%
		if (simulationStopTime != null) {
		%>
			<div>
			<p>The last time to simulate was: 
		    <%out.println(simulationStopTime.toString()); %> </p>
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
		 
		 <script>



function loadDoc() { 
	var value = Math.random() * 5000;
	value = value + 3000;
		var url = "http://localhost:8080//sssq_jsp-0.0.1-SNAPSHOT//GetSampleCount?StopTime=";
		url = url + value;
    alert(url);
       
    var theUrl = url;
    //var response = httpGet(theUrl); 
	
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (xhttp.readyState == 4 && xhttp.status == 200) {
      document.getElementById("abc").innerHTML = xhttp.responseText;
    }
  };
  xhttp.open("GET", theUrl, true);
  xhttp.send();
}

function myFunction() {
	alert("In myFunction");
    setTimeout(function(){ loadDoc(); }, 3000);
}

</script>
		 
		 
		 <button onclick="myFunction()">Try it</button>
		 
		</form> 

<div class="special">
<h2>Heading</h2>
<p>This is a paragraph. Notice how the paragraph is contained in the div.</p>

</div>
<div id="map" style="width:300px;height:280px;">


 <script>

// This example creates a 2-pixel-wide red polyline showing the path of William
// Kingsford Smith's first trans-Pacific flight between Oakland, CA, and
// Brisbane, Australia.

function initMap() {
  var map = new google.maps.Map(document.getElementById('map'), {
    zoom: 3, 
    center: {lat: 51.508742, lng: --0.120850},
    mapTypeId: google.maps.MapTypeId.TERRAIN
  });

  var flightPlanCoordinates = [
    {lat: 50, lng: 0},
    {lat: 51.5, lng: -0.12},
    {lat: -18.142, lng: 178.431},
    {lat: -27.467, lng: 153.027}
  ];
  var flightPath = new google.maps.Polyline({
    path: flightPlanCoordinates,
    geodesic: true,
    strokeColor: '#FF0000',
    strokeOpacity: 1.0,
    strokeWeight: 2
  });

  flightPath.setMap(map);
}

    </script>
    <script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCg63JV_XUJC1yQcgzldsjRAb7XSBPDsgE&signed_in=true&callback=initMap"></script>
  </body>
 
</div>
</body>
</html>