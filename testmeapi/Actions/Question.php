<?php

$question_id = $post["question_id"];
ResponseGenerator(1 , $post["token"] , "" , array( "data"=>Read("question" ,["id=".$question_id])));