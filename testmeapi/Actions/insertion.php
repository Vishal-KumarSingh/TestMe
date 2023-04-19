<?php

required_fields(array("token" , "table" , "data"));


//restricting Access
AdminAccess($post["token"]);


//creating sql header format
$data = $post["data"];
$table = mysqli_real_escape_string($conn , $post["table"]);
$sql = "insert into ".$table." (";
$tuples = "";
$column  = array();
foreach($data as $key=>$value){
$sql.=$key.",";
array_push($column , $key);
}
$sql[strlen($sql)-1]=' ';


//adding tuple entry values
$i=0;
foreach($data[$column[0]] as $entry){
    $tuples .= "(";
    foreach($column as $val){
        $tuples .= "'".$data[$val][$i]."',";
    }
    $tuples[strlen($tuples)-1]=")";
    $tuples .= ",";
    $i=$i+1;
}
$tuples[strlen($tuples)-1]=" ";


//appending both values
$query = $sql .") values ".$tuples;

//echo $query;
$result = mysqli_query($conn , $query);
if($result){
     ResponseGenerator(true , $post["token"] , "Item Added Successfully");
 }else{
     ResponseGenerator(false , $post["token"] , "Something Went Wrong");
 }