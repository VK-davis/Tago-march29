<?php
	error_reporting(0);
	require "conn.php";
	$name=$_POST["name"];
	$username=$_POST["username"];
	$password=$_POST["password"];
	$options = [
	'cost' => 12,
	];
	$password = password_hash($password, PASSWORD_BCRYPT, $options);
	$email=$_POST["email"];
	$flag=0;
	$query="SELECT * FROM user_entry WHERE username like '$username';";
	$result=$conn->query($query);
	$res=$result->fetch_assoc();
	if(!$res){
			$query2="SELECT * FROM user_entry WHERE email like '$email';";
			$result2=$conn->query($query2);
			$res2=$result2->fetch_assoc();
			if(!$res2){
				$query3="INSERT INTO user_entry VALUES(null,'$name','$username','$password','$email');";
				$result3=$conn->query($query3);
				if($result3===TRUE){
					echo "Welcome ".$name;
					mkdir("uploads/".$username);
					$flag=1;
				}
	
				else
					echo "error!!".$result."<br>".$conn->error;
			}
		
	}
	if($flag==0 && $username==$res["username"])
	{
		echo "Username already exists";
	}
	if($flag==0 && $email==$res2["email"])
	{
		echo "E-mail id already exists";	
	}


$conn->close();
?>