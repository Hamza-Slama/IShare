<?php
require("DBInfo.inc");
//Call service to new register
//http://localhost/IshareServer/TweetAdd.php?user_id=3&tweet_text=fffffffffffffffffffff0&tweet_picture=home/u5.png
$query ="insert into tweets(user_id,tweet_text,tweet_picture) values (" . $_GET['user_id']. ",'" . $_GET['tweet_text']. "','" . $_GET['tweet_picture']. "');" ;
$result= mysqli_query($connect,$query);
if (!$result){
	$output ="{ 'msg':'fail'}" ;
}else{
	$output ="{ 'msg':'tweet is added'}" ;
}
print($output);
mysqli_close($connect);
?> 
