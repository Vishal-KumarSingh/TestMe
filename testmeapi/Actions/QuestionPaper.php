<?php

$test_id = $post["test_id"];

$test_detail = Read("test" , ["id=".$test_id]);
$question = explode("/",$test_detail[0]["questions"]);
//print_r($question);
$data=array();
foreach($question as $q){
   array_push($data , Read("question" , ["id=".$q]));
}
ResponseGenerator(1 , $post["token"] , "" , ["data" => $data]);