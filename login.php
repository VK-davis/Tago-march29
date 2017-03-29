<?php
	//session_start();
	require "conn.php";
	$usrname=$_POST["user_name"];
	$pass=$_POST["password"];
	$valid_pass=0;
	$query="select * from user_entry where username like '$usrname';";
	$result=$conn->query($query);
	$res=$result->fetch_assoc();
	if(password_verify($pass,$res["password"]))
		$valid_pass=1;
	$result=$conn->query($query);
	if($result->num_rows>0 && $valid_pass==1){
		while($res=$result->fetch_assoc()){
			$loggedinname=$res["username"];
			$profilename=$res["name"];
		}
		echo "Login success ".$loggedinname."--".$profilename;
	}
	else {
		/*$query="select * from user_entry where username like '$usrname';";
		$result=$conn->query($query);
		$res=$result->fetch_assoc();
		if($result->num_rows>0 && !(password_verify($pass,$res["password"]))) {
			while($res=$result->fetch_assoc()){
				if(!($res['password']==$pass)){
					echo "Incorrect Password!!!";	
				}
			}			
		}*/
		if($valid_pass==0)
				echo "Incorrect Password!!!";
		else
					echo "The username doesn't match any account.";
	 	//echo("Error description: " . mysqli_error($mysqli));
	}
$conn->close();
?>