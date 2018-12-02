drop database IShare;
create database IShare;
use IShare;
create table login (
user_id int  AUTO_INCREMENT PRIMARY KEY,
first_name varchar(50),
email varchar(50),
password varchar(50),
picture_path varchar(350)
);

describe login;
--tweets
create table shares(

 user_id int,
 shares_id int AUTO_INCREMENT PRIMARY KEY,
 shares_text varchar(100),
 shares_picture varchar(350),
 shares_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 FOREIGN KEY (user_id) REFERENCES login(user_id)
);

describe shares;


create table following (
user_id int,
following_user_id int,
FOREIGN KEY (user_id) REFERENCES login(user_id),
FOREIGN KEY (following_user_id) REFERENCES login(user_id)
);

describe following;


create view user_shares as

	select  shares.shares_id, shares.shares_text, shares.shares_picture,
	shares.shares_date, login.first_name,login.picture_path, shares.user_id
	from shares
	inner join login
	on shares.user_id= login.user_id ;

describe user_shares ;


 
