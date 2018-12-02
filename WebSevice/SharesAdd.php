<?php
require("DBInfo.inc");
//Call service to new register
//http://localhost/IshareServer/SharesAdd.php?user_id=3&shares_text=fffffffffffffffffffff0&shares_picture=home/u5.png
$query ="insert into shares(user_id,shares_text,shares_picture) values (" . $_GET['user_id']. ",'" . $_GET['shares_text']. "','" . $_GET['shares_picture']. "');" ;
$result= mysqli_query($connect,$query);
if (!$result){
	$output ="{ 'msg':'fail'}" ;
}else{
	$output ="{ 'msg':'Share is added'}" ;
}
print($output);
mysqli_close($connect);
?> 
