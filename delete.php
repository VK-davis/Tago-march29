<?php
	require "conn.php";
	$loggedinname=$_POST["name"];
	$file_path=$_POST["file_path"];
	$query="DELETE FROM folders where foldername like '$loggedinname';";
	$conn->query($query);
	unlink('uploads/'.$loggedinname."/".$file_path);
?>
