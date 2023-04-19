<?php
session_start();
$conn  = mysqli_connect("127.0.0.1" , "root" , "" , "testme");
//$conn  = mysqli_connect("localhost" , " id20293696_root" , "qv#=UHv@Ua6g*Tk{" , "id20293696_room");
$post = json_decode(file_get_contents("php://input"),true);

include_once "lib.php";
POSTpurifier();