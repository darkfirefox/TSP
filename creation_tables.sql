create table book_catalog( 
ID_book int NOT NULL AUTO_INCREMENT, 
name VARCHAR(50) NOT NULL, 
author VARCHAR(50) NOT NULL, 
genre VARCHAR(50) NOT NULL, 
year int(4) NOT NULL, 
cost int NOT NULL CHECK (cost>=0), 
description VARCHAR(1000), 
PRIMARY KEY (ID_book) 
); 

create table users( 
ID_user int NOT NULL AUTO_INCREMENT, 
username VARCHAR(16) NOT NULL, 
password VARCHAR(16) NOT NULL, 
role VARCHAR(1) CHECK(role in ('a','u')), 
PRIMARY KEY (ID_user) 
); 
create table basket( 
ID_user int NOT NULL, 
ID_book int NOT NULL, 
FOREIGN KEY (ID_book) REFERENCES book_catalog(ID_book), 
FOREIGN KEY (ID_user) REFERENCES users(ID_user) 
); 
create table orders( 
idOrder int NOT NULL AUTO_INCREMENT,
ID_user int NOT NULL, 
ID_book int NOT NULL, 
dataSale DATE NOT NULL, 
PRIMARY KEY (idOrder),
FOREIGN KEY (ID_book) REFERENCES book_catalog(ID_book), 
FOREIGN KEY (ID_user) REFERENCES users(ID_user) 
);