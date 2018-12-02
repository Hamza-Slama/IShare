 
<?php
require("DBInfo.inc");
//Call service to register
 // define quesry  //StartFrom
if ( $_GET['op']==1) { // my following
	 // case 1: search op=2, user_id =? , StartFrom=0
//http://localhost/IshareServer/SharesList.php?op=1&user_id=1&StartFrom=0

$query="select * from user_shares where user_id in (select following_user_id from following where user_id=". $_GET['user_id'] . ") or user_id=" . $_GET['user_id'] . " order by shares_date DESC". 
" LIMIT 20 OFFSET ". $_GET['StartFrom']  ;  // $usename=$_GET['username'];
}
elseif ( $_GET['op']==2) { // specific person post
 // case 2: search op=2, user_id =? , StartFrom=0
	//http://localhost/IshareServer/SharesList.php?op=2&user_id=1&StartFrom=0
$query="select * from user_shares where user_id=" . $_GET['user_id'] . " order by shares_date DESC" . 
" LIMIT 20 OFFSET ". $_GET['StartFrom'] ;  // $usename=$_GET['username'];
}
elseif ($_GET['op']==3) {
	// case 3: search op=3, query =? , StartFrom=0
//http://localhost/IshareServer/SharesList.php?op=3&query=new&StartFrom=0
$query ="select * from user_shares where shares_text like  '%". $_GET['query'] ."%' LIMIT 20 OFFSET ". $_GET['StartFrom'] ;
}elseif($_GET['op'] == 4 ){
//select picture_path,first_name,user_id  from login where user_id=1;
//http://localhost/IshareServer/SharesList.php?op=4&user_name=
$query ="select user_id,first_name,email,picture_path  from login where  first_name=". $_GET['user_name']  ;
 }

 
 
$result= mysqli_query($connect,$query);
if (!$result){
	die(' Error cannot run query');
}
$userShares=array();
while ($row= mysqli_fetch_assoc($result)) {
	
	$userShares[]= $row ;
 
}
if ($userShares) {
print("{'msg':'has Shares','info':'". json_encode($userShares) ."'}");
}else{
print("{'msg':'no Shares' }");
}
 mysqli_free_result($result);
mysqli_close($connect);
?>
