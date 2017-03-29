<?php
	error_reporting(0);
	require "conn.php";
	//include_once 'class.verifyEmail.php';
	$name=$_POST["name"];
	$username=$_POST["username"];
	$password=$_POST["password"];
	$options = [
	'cost' => 12,
	];
	$password = password_hash($password, PASSWORD_BCRYPT, $options);
	
	$emailvalid=1;
	//$email=$_POST["email"];
	/*$vmail = new verifyEmail();
		$vmail->setStreamTimeoutWait(20);
		$vmail->Debug= TRUE;
		$vmail->Debugoutput= 'html';

		$vmail->setEmailFrom('tagoinfitechsystems@gmail.com');

		if ($vmail->check($email)) {
			//echo "Entered email already exist";
			$emailexist=1;
		} elseif (verifyEmail::validate($email)) {
			echo 'Entered email is not valid';
			$emailvalid=0;
			
		} else {
			echo "Entered email is not valid";
			$emailvalid=0;
		}
	
		*/
		$email = $_POST["email"];
if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
  $emailErr = "Enter a valid E-mail address";
  $emailvalid=0;

}
	if(!(isset($_GET['id']) && isset($_GET['code']) ) && $emailvalid==1){//--------------------------- new

	$flag2=0;
	$query="SELECT * FROM user_entry WHERE username like '$username';";
	$result=$conn->query($query);
	$res=$result->fetch_assoc();
	if(!$res){
			$query2="SELECT * FROM user_entry WHERE email like '$email';";
			$result2=$conn->query($query2);
			$res2=$result2->fetch_assoc();
			if(!$res2){
						//echo "A verification mail has been sent, please check your Email not".urldecode ($_POST['reg']);
						if(isset($_POST['reg']))
						{

							//$email=$_POST['email'];
							//$pass=$_POST['password'];
							$code=substr(md5(mt_rand()),0,15);
	
	
							//$insert=mysql_query("insert into verify values('','$email','$pass','$code')");
							$query="INSERT INTO verify VALUES(NULL,'$name','$username','$email','$password','$code');";
	
							if ($conn->query($query) === TRUE){
    								$db_id = $conn->insert_id;
    						}
							$message = "Your Activation Code is ".$code."\n";
    						$to=$email;
    						$subject="Activation Code For TAGO\n";
    						//$body='Your Activation Code is '.$code.' Please Click On This link';
    						//$body.='<a href="http://localhost/UploadProject/verification.php?id=".$db_id."&code=".$code>Verify.php?id='.$db_id.'&code='.$code.'</a>';//'.$db_id.'
							$body='<h3>Thankyou for registering to Tago.</h3></br> Please ';
							$body.='<html><a href="http://localhost/Tago/verifyandsignup.php?id='.$db_id.'&code='.$code.'">Verify</a> the account to finsh the registeration process</html>';  
    						//$body.='to activate your account.';
    						$headers = "From: TeamTago";
    						$headers.= "-------------------MIME-Version: 1.0\r\n";
							$headers.= "Content-Type: text/html; charset=ISO-8859-1\r\n";
    						mail($to,$subject,$body,$headers);
	
							echo "A verification mail has been sent. Verif your account and proceed to login";
						}			
			}
		
	}
	if($flag2==0 && $username==$res["username"])
	{
		echo "Username already exists";
	}
	if($flag2==0 && $email==$res2["email"])
	{
		echo "This E-mail id is already registered";	
	}
}//--------------------------eof first "if"



if(isset($_GET['id']) && isset($_GET['code']))
{
	
	$id=$_GET['id'];
	$code=$_GET['code'];
	//echo $id.'  '.$code;
	$query="select * from verify where id='$id' and code='$code';";
	$res=$conn->query($query);
			if($res->num_rows>0){
				while($r=$res->fetch_assoc())
				{	
					$email=$r['email'];
					$password=$r['password'];
					$name=$r['name'];
					$username=$r['username'];
				}
		$query="INSERT INTO verified_user values(NULL,'$name','$username','$email','$password');";
		$conn->query($query);

		$query="delete from verify where id='$id' and code='$code';";
		$conn->query($query);

		//echo "You shall proceed to login!";

		$flag1=1;//------------new

		$query3="INSERT INTO user_entry VALUES(null,'$name','$username','$password','$email');";
					$result3=$conn->query($query3);
					if($result3===TRUE){
						echo "Welcome ".$name;
						mkdir("uploads/".$username);
						$flag2=1;
					}
	
					else
						echo "error!!".$result."<br>".$conn->error;
	}
}






$conn->close();




?>