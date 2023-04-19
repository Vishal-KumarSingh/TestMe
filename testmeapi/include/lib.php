<?php
function ResponseGenerator($status , $token, $toast , $data = array()) {
    $response = array(
        "token" => $token,
        "status"=> $status,
        "toast"=> $toast
    ); 
    foreach($data as $key => $value){
        $response[$key] = $value;
    }
    echo json_encode($response);
}


function required_fields($arrays , $token=""){
    global $post;
    foreach($arrays as $array){
        //echo $array;
          if(!isset($post[$array])){
                 
                 ResponseGenerator(false , $token , "Missing equired Fields");
                 die();
          }
    }
}


function TokenGenerator(){
    $character = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
   $token = '';
    for($i=0;$i<=6;$i++){
       $token .= $character[rand(0,strlen($character)-1)];
    }
    //echo $token;
    return $token;
}



function getUserFromToken($token) {
    global $conn;
    $sql = "select * from user where token = '". $token."'";
    $result = mysqli_query($conn , $sql);
    $user = mysqli_fetch_assoc($result);
    return $user;
}


function AdminAccess($token){
    global $post;
    if($token=="Admin@encrypted"){
          return;  
    }else{
          ResponseGenerator(false , $post["token"] , "Admin Access Required for This Action" , array());
          die();
    }
}

function Read($table , $conds=[]){
    global $conn;
    $sql="select * from ".$table." where 1 ";

    foreach($conds as $condition){
        $sql .= " and ";
        $sql.=$condition;
    }
    //echo $sql;
    $result = mysqli_query($conn,$sql);
    return mysqli_fetch_all($result,true );
}

function POSTpurifier(){
    global $post,$conn;
    // foreach($post as $input){
    //     mysqli_real_escape_string($conn  , $input);
    // }
}



function InsertItem($table , $data){
global $conn;
$table = mysqli_real_escape_string($conn , $table);
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

return $result;


}