<?php


?>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="css-loginpage.css" media="screen" />
	<title> Login Form </title>
	<h2> Please fill the Login form to upload contents.</h2>
</head>
	<body>
		<form action="login.php" method="post" enctype="multipart/form-data">
		<div>
			Username:<input type="text" name="username"></br>
			Password:<input type="password" name="password"></br>
			<input type="submit" value="submit" id="smt"></br></br>
			<a href="signup.php"> Signup Here</a>


		</div>
	</form>
</body>
</html>