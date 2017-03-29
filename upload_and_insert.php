<?php
	require "conn.php";
	$loggedinname=$_POST["name"];
	$response=array();
	$file_path="uploads/".$loggedinname."/";
	$file_path=$file_path.basename($_FILES['uploaded_file']['name']);
	if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'],$file_path)){
		 $response["success"]="success...".$file_path;
		 //echo json_encode($response);
		 $query="select * from folders where foldername like '$loggedinname';";
		 $result=$conn->query($query);
		 if(!($result->num_rows>0)){
			$query="INSERT INTO folders VALUES(null, '$loggedinname','$file_path');";
			$conn->query($query);
		 }
	}
	else
		echo "error";
?>