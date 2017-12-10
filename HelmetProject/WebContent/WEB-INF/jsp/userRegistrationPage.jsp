<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     	<%@ page errorPage="error.jsp" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Driver Registration</title>



    <!-- BOOTSTRAP STYLES-->
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
    <!-- FONTAWESOME STYLES-->
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
       <!--CUSTOM BASIC STYLES-->
    <link href="assets/css/basic.css" rel="stylesheet" />
    <!--CUSTOM MAIN STYLES-->
    <link href="assets/css/custom.css" rel="stylesheet" />
    <!-- GOOGLE FONTS-->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
    
    <link rel="stylesheet" href="jquery-ui.min.css">
    <link rel="icon" type="image/png" href="images/favicon.png">
<script src="external/jquery/jquery.js"></script>
<script src="jquery-ui.min.js"></script>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
    
    <script type="text/javascript">
    function myAjaxCall() {
    	var path = "${pageContext.request.contextPath }";
    	//alert(path);
    	// get the form values
    	        var mobiileNo = $('#mobile').val();
    	        
    	        $.ajax({
    	        type: "GET",
    	        url: path+"/studentverify.html",
    	        data: "mobile=" + mobiileNo,
    	        success: function(response){
    	        // we have the response
    	        //alert(response);
    	        $('#availability').text(response);
    	        },
    	        error: function(e){
    	        //alert('Error: ' + e);
    	        }
    	        });
    	        }

    function isNumberKey(evt){
        var charCode = (evt.which) ? evt.which : evt.keyCode
        return !(charCode > 31 && (charCode < 48 || charCode > 57));
    }
    
    function getValidation(){
    	
    	var usersName = document.getElementById("userName").value;
    	if (usersName == null || usersName == "") {
    		alert(":: Invalid User Name ::");
    		return false;
    	}
    	
    	var mobileavailability = $('#availability').text();
    	var moileno = document.getElementById("mobile").value;
    	if (moileno == null || moileno == "" || moileno.length < 10 || mobileavailability != "") {
    		alert(":: Invalid Mobile No ::");
    		return false;
    	}
    	
    	var pwd = document.getElementById("password").value;
    	var cpwd = document.getElementById("Cpassword").value;;
    	if (pwd == null || pwd == "" || pwd.length < 5 || pwd != cpwd) {
    		alert(":: Password Mismatch ::");
    		return false;
    	}
    }
    
    </script>

</head>
<body>

<nav class="navbar navbar-default navbar-cls-top " role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                    <i class="fa fa-bars"></i>
                </button>
                <a class="navbar-brand" href="./"><img src="images/logo.png" alt="Site Logo"></a>
            </div>
           
		 <nav id="main-menu" class="collapse navbar-collapse pull-right">
		 <div class="sidebar-collapse">
        <ul class="nav navbar-nav">
          <li><a href="adminActionPage.html">Home</a></li>
        </ul></div>
      </nav>
           
        </nav>
  <!-- Header Section -->

<%-- 
<div align="right" class="header-right">
<b style="letter-spacing: 4px"; ><font color="black">Welcome : </font></b><strong style="color: red;">${sessionScope.UserName }</strong>
			 <a href="${pageContext.request.contextPath }/logout.html" class="btn btn-primary" title="New Task">
			 <strong>Logout</strong></a>
</div><br><br> --%>



<div class="row" align="center">
		<div class="col-md-4" align="right">

			<div class="panel panel-success" align="center">
				<div class="panel-heading">User Details</div>
				<div class="panel-body" style="padding: 0px;">
 <form method="POST" name="form1" onsubmit="return getValidation()" action="userdetails.html">

<table class="table">

				<tr>
			        <td align="right"><label>Name:</label></td>
			        <td><input type="text" name="userName" id="userName" value=""/></td>
			    </tr>
			  <!--   <tr>
			        <td align="right"><label>Mobile No:</label></td>
			        <td><input type="text" name="mobile" id="mobile" maxlength="10" onkeypress="return isNumberKey(event);" value=""/><font color="red"><p id="availability"></p></font></td>  onfocusout="myAjaxCall()"
			    </tr> -->
			    <tr>
			        <td align="right"><label>Email:</label></td>
			        <td><input type="text" name="email" id="email" value=""/></td>
			    </tr>
			    <tr>
			        <td align="right"><label>Current Loction:</label></td>
			        <td><input type="text" name="location" id="location" value=""/></td>
			    </tr>
			    <tr>
			        <td align="right"><label>City:</label></td>
			        <td><input type="text" name="city" id="city" value=""/></td>
			    </tr>
			    <tr>
			        <td align="right"><label>Date of Birth:</label></td>
			        <td><input type="text" name="dob" id="dob" value=""/></td>
			    </tr>
			    <tr>
			        <td align="right"><label>ID Proof:</label></td>
			        <td><input type="text" name="idproof" id="idproof" value=""/></td>
			    </tr>
			    <tr>
			        <td align="right"><label>ID Proof No:</label></td>
			        <td><input type="text" name="idproofNo" id="idproofNo" value=""/></td>
			    </tr>
			    
			    <tr>
			      <td colspan="2" align="center" class="panel-footer"><input type="submit" class="btn btn-success" value="Save" /></td>
		      </tr>
			</table> 

</form>
<font color="#00FF00"><span>${regflag }</span></font>
</div></div></div></div>




					
<!-- <div align="right" id="footer-sec">© IAMSEQST 2016 - All Reserves. Developed by <a href="https://www.facebook.com/MaheshKarthan" target="_blank"><font color="brown">Mahesh Kartan</font></a> | Designed by <a href="http://vicishealth.com/" target="_blank"><font color="brown">Vicis Health services private limited.</font></a>
   </div> -->

</body>
</html>