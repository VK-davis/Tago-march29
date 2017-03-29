<?php 
	//require "conn.php";
/*	// Table Scheme for Verify Table
$sql="CREATE TABLE verify (
 id int(11) NOT NULL AUTO_INCREMENT,
 email text NOT NULL,
 password text NOT NULL,
 code text NOT NULL,
 PRIMARY KEY (id)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1";
$conn->query($sql);
// Table Scheme for verified_user table
$sql2="CREATE TABLE verified_user (
 id int(11) NOT NULL AUTO_INCREMENT,
 email text NOT NULL,
 password text NOT NULL,
 PRIMARY KEY (id)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1";
$conn->query($sql2);*/


	$name=$_POST["name"];
	$username=$_POST["username"];
	$password=$_POST["password"];
	$email=$_POST["email"];

	$flag1=0;//-----------------------new ; line 22-27
echo "please check your Email, no dont".$_POST['submit'];
if(isset($_POST['submit']))
{

	//$email=$_POST['email'];
	//$pass=$_POST['password'];
	$code=substr(md5(mt_rand()),0,15);
	
	
	//$insert=mysql_query("insert into verify values('','$email','$pass','$code')");
	$query="INSERT INTO verify VALUES(NULL,'$email','$password','$code');";
	
	if ($conn->query($query) === TRUE){
    	$db_id = $conn->insert_id;
    }
	$message = "Your Activation Code is ".$code."\n";
    $to=$email;
    $subject="Activation Code For TAGO\n";
    //$body='Your Activation Code is '.$code.' Please Click On This link';
    //$body.='<a href="http://localhost/UploadProject/verification.php?id=".$db_id."&code=".$code>Verify.php?id='.$db_id.'&code='.$code.'</a>';//'.$db_id.'
$body='<h3>Thankyou for registering to Tago.</h3></br> Please ';
$body.='<html><a href="http://localhost/UploadProject/verification.php?id='.$db_id.'&code='.$code.'">Verify</a> the account to finsh the registeration process</html>';  
    //$body.='to activate your account.';
    $headers = "From: TeamTago";
    $headers.= "-------------------MIME-Version: 1.0\r\n";
	$headers.= "Content-Type: text/html; charset=ISO-8859-1\r\n";
    mail($to,$subject,$body,$headers);
	
	echo "A verification mail has been sent, please check your Email";
}

if(isset($_GET['id']) && isset($_GET['code']))
{
	
	$id=$_GET['id'];
	$code=$_GET['code'];
	//echo $id.'  '.$code;
	$query="select email,password from verify where id='$id' and code='$code';";
	$res=$conn->query($query);
			if($res->num_rows>0){
				while($r=$res->fetch_assoc())
				{	echo "hi2";
					$email=$r['email'];
					$password=$r['password'];
				}
		$query="INSERT INTO verified_user values(NULL,'$email','$password');";
		$conn->query($query);

		$query="delete from verify where id='$id' and code='$code';";
		$conn->query($query);

		echo "You shall proceed to login!";

		$flag1=1;//------------new
	}
}

?>