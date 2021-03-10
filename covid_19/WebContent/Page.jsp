<%@ page language="java"  contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css" />
<title>Covid-19 vaccination statistics</title>
<script type="text/javascript" src="js.js">    </script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
</head>
<body>

<div class='top'>	
	<h1 >Στατιστικά εμβολιασμού για τον COVID-19</h1>
</div>
<div class='mid'>
  <div class="column">
  	<h3>Συνολικά μέχρι σήμερα,<br/> έχουν εμβολιαστεί <span id="x">  X </span><br/>   άνθρωποι.</h3> 
  </div>
  <div class="column">
  <h3>Έχουν γίνει, <br/><span id="y">  Y </span> εμβολιασμοί <br/>της 1ης δόσης.</h3>	
  </div>
  <div class="column">
    	<h3> Έχουν γίνει, <br/><span id="z">  Z </span> εμβολιασμοί <br/>της 2ης δόσης.</h3>
  </div>
</div>
<div class='charts' >
    <div  id="UI">
        <div id="Areas_div"><select id="Areas" onchange="area_updated()" ></select></div>
        <div id="Date_from" >Από:</div>
        <div id="Date_to" >Έως:</div>
        <div id="Change_dates"  class="button" onclick="date_updated()">Αλλαγή ημ/νιας</div>
    </div>
		
	<div id="debug">
	</div>	
		
    <div id="left_line" >
        
    </div>
    <div id="right_line">
        
    </div> 
        
    
    <div id="left_pie">
            
    </div>
    <div id="right_pie">
            
    </div>
           
     
    
</div>
<div class='footer'>
	<small>Τα δεδομένα αντλήθηκαν απο το site:<a href="https://data.gov.gr/">https://data.gov.gr</a></small>
	<br/>
	<small>Για τους γράφους χρησιμοποιήθηκε το api:<a href="https://developers.google.com/chart">https://developers.google.com/chart</a></small>
	<br/>
	<div id="logo">Created by Tolias Dimitris</div>
</div>

<script>

	//2)make_date_from , make_date_to
	make_date_from("Date_from");
	make_date_to("Date_to");
	//computes and sets xyz values 
	compxyz();
	//1)save jsp resp.get atr json value to sessionStorage-->storemissingdatasets()
	<%String json=(String)session.getAttribute("json");	%>
	//call store_missing_datasets2 cause the json is JSON OBJECT
	store_missing_datasets2(<%=json%>);
	
	//3)update(x,y,z) --> update top values
	//4) date_updated() --> gia na ginei update to ui kai ta charts
	date_updated();

</script>
</body>
</html>