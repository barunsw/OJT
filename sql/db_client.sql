CREATE TABLE TB_DB_CONNECT (
	SEQ_NUM		INTEGER AUTO_INCREMENT PRIMARY KEY 
	, DB_TYPE	ENUM('MARIA', 'POSTGRES') 	NOT NULL
	, IP		VARCHAR(50)					NOT NULL
	, PORT		VARCHAR(10)					NOT NULL
	, USER_NAME	VARCHAR(100)				NOT NULL
	, PASSWORD	VARCHAR(100)				NOT NULL
	, DB_NAME	VARCHAR(100)				NOT NULL
	, CREATED	DATETIME 	DEFAULT NOW()
	, MODIFIED	DATETIME
) ENGINE=INNODB DEFAULT CHARACTER SET UTF8 COLLATE UTF8_GENERAL_CI;