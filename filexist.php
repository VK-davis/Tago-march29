<?php/*
	$loggedinname=$_POST["name"]; 
	$q      =       (count(glob("$dir/*")) === 0) ? 'Empty' : 'Not empty';

    if ($q="Empty")
    {

        echo "the folder is empty"; 

    }*/
 //here we retrieve download path for the file from db and delete 
 require "conn.php";
 $loggedinname=$_POST["name"];
 $query="select * from folders where foldername like '$loggedinname';";
 $result=$conn->query($query);
 if($result->num_rows>0){
		while($res=$result->fetch_assoc())
			$folderpath=$res["path"];
		echo "file exists";
	}
	else 
		echo "file not exists";

?>