CREATE TABLE person(
	seq INT not null auto_increment PRIMARY KEY,
	name VARCHAR(30) not null,
	gender CHAR(6) not null,
	birth CHAR(10) not null,
	email VARCHAR(50) not null,
	regDate DATETIME not null DEFAULT NOW(),
	updateDate DATETIME not null DEFAULT NOW()
);