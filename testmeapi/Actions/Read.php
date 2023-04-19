<?php

AdminAccess($post["token"]);
ResponseGenerator(1 , $post["token"] , "hello" , array( "data"=>Read($post["table"] , $post["condition"])));