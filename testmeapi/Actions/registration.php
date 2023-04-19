<?php
include 'include/conn.php';
required_fields(array("username" , "password"));
$username = mysqli_real_escape_string($conn , $post["username"]);
$password = mysqli_real_escape_string($conn , $post["password"]);
$token = TokenGenerator();
$otp = rand(1000 , 9999);
$sql = "insert into user (email , password , token , verify) values ('".$username."','".$password."' , '".$token."' ,".$otp.")";

if($result = mysqli_query($conn , $sql)){
   
   ResponseGenerator(true , $token , "Registration Successful!  Please Verify Account" );
}else{
    ResponseGenerator(false , "" , "Registration failed , Try Again");
}


