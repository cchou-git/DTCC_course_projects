<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     
    
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
		<style type="text/css">
 
</style>

 <style>
 
 
 .outer {
  width: 100%;
  text-align: center;
  background-image: url('images/flight_panel.PNG');
    background-size: 100% 100%;
    background-repeat: no-repeat;
}

.inner {
  display: inline-block;
}
 
</style>

		
		
		<link id="CssLink" name="CssLink" rel="stylesheet" href="./css/main.css" /> 
		
  
<body> 
 
		<form method=post  id=simulate>
		<h1>My Airplane Simulation</h1>
		<fieldset>
		<legend>Simulation Details</legend> 
		
		<div class="outer">
  <div class="inner" id="map" style="width:500px;height:280px;">
		</div> 
		<p id="abc">status</p>
		<br>
		<p id="cde">this is a test</p>
		
		 <input type="button" onclick="myFunction()" value="Init System" opacity="0.6"> 
		 <input type="button" onclick="setHeading(45)" value="Set Heading to 45 degrees">  
		 <input type="button" onclick="setSpeed(500)"  value="Set Speed to 500 miles per hour"> 
		 
		
		</form> 

 

<script>
function showVal(newVal){
	  document.getElementById('blah').innerHTML='Heading = ' + newVal;
	  setHeading(newVal);	  
	}

function showSpeed(newVal){
	  document.getElementById('blah2').innerHTML='Speed = ' + newVal;
	  setSpeed(newVal);	  
	}
</script>


  


<script>

// This example creates a 2-pixel-wide red polyline showing the path of William
// Kingsford Smith's first trans-Pacific flight between Oakland, CA, and
// Brisbane, Australia.
//Double lastLongitude = 40.7127;
//	Double lastLatitude = 74.0059;
var flightPlanCoordinates = new Array();

flightPlanCoordinates.push({lat: 40.7127, lng: -74.0059 })  

var map;

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
    zoom: 10, 
    center: {lat: 40.7127, lng: -74.0059},
    mapTypeId: google.maps.MapTypeId.TERRAIN
  });

  
  
}

    </script>
    <script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCg63JV_XUJC1yQcgzldsjRAb7XSBPDsgE&signed_in=true&callback=initMap"></script>
<script>    

var counter;

function alertContents(httpRequest) {
    if (httpRequest.readyState == 4 && httpRequest.status == 200) {

			var data = httpRequest.responseText; 
        	document.getElementById('abc').innerHTML = data;
        	var elems = data.split('|');
			var newData = {lat: parseFloat(elems[1])  , lng: parseFloat(elems[2]) }; 
			flightPlanCoordinates.push(newData);
			
        	var flightPath = new google.maps.Polyline({
        	    path: flightPlanCoordinates,
        	    geodesic: true,
        	    strokeColor: '#FF0000',
        	    strokeOpacity: 1.0,
        	    strokeWeight: 2
        	  });

        	  flightPath.setMap(map);
        	
        	
        } else {
        	document.getElementById('cde').innerHTML = 'status = ' + httpRequest.status + ' reponse = ' + httpRequest.responseText + ' count = ' + counter
        	counter=counter+1;
        } 
}

function httpGet(theUrl) {
	counter = 0;
    var httpRequest = new XMLHttpRequest();
    httpRequest.onreadystatechange = function () { alertContents(httpRequest); };
    httpRequest.open("GET", theUrl, true);
    httpRequest.send(null); 
}

var initCommandString = 'http://localhost:8080/sssq_jsp/GetSampleCount?sysinit=';
var incrementHeadingCommandString =  'http://localhost:8080/sssq_jsp/GetSampleCount?setHeading=';
var setSpeedCommandString =  'http://localhost:8080/sssq_jsp/GetSampleCount?setSpeed=';
var getStatusCommandString =  'http://localhost:8080/sssq_jsp/GetSampleCount';

function setHeading(val) {
	 
	httpGet(incrementHeadingCommandString + val)  
}

function setSpeed(val) {
	 
	httpGet(setSpeedCommandString + val)  
}


function getStatus() {
	httpGet(getStatusCommandString);	
}

var timerStart = false;

function myFunction() {
	alert("In myFunction"); 
	 
	httpGet(initCommandString)  
	if (timerStart == false) {
		timerStart = true;
		setInterval(function(){ getStatus(); }, 3000);
	}
}

</script>

     <span>
<h2 style="color:white;text-align:left"   id='blah' >Heading  </h2><input type="range" name="heading" min="-180" max="180" step="1" 
   oninput="showVal(this.value)" onchange="showVal(this.value)">

 

<h2   id='blah2' style="color:white;text-align:left" >Speed

		</h2><input type="range" name="heading" min="300" max="900" step="30" 
   oninput="showSpeed(this.value)" onchange="showSpeed(this.value)">  
   
   </span>
    
    
  </body>

</body>
</html>