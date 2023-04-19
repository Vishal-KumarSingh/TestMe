<?php
include "include/conn.php";

required_fields(array("username" , "password" ));
$username = mysqli_real_escape_string($conn , $post["username"]);
$password = mysqli_real_escape_string($conn , $post["password"]);
$sql = "select * from user where email = '".$username."' and password= '".$password."'";
$result = mysqli_query($conn , $sql);
if($token = mysqli_fetch_assoc($result)){
    
    ResponseGenerator(true , $token["token"] , "Login Success");
}else{
    ResponseGenerator(false , "" , "Login failed");
}