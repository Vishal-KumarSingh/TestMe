<?php

$post["data"]["marks"]=[80];
$post["data"]["user_id"]=[getUserFromToken($post["token"])["id"]];


InsertItem("results" , $post["data"]);
$marks=80;
ResponseGenerator(1,$post["token"],"Congrates your marks is ".$marks,array());