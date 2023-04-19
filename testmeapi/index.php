<?php
include "include/conn.php";


if(file_exists("Actions/".$post["action"].".php")){
    include "Actions/".$post["action"].".php";
}else{
    ResponseGenerator(false, $post["token"]," Unknown Action ", array());
}
