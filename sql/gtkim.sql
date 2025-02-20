-- DROP TABLE IF EXISTS TB_PERSON;
CREATE TABLE TB_PERSON (
	SEQ INT PRIMARY KEY AUTO_INCREMENT
	, NAME VARCHAR(32) NOT NULL
	, GENDER ENUM('MAN', 'WOMAN') NOT NULL
	, AGE INT
	, PHONE VARCHAR(32)
	, ADDRESS VARCHAR(256)
) ENGINE=InnoDB DEFAULT CHARACTER SET UTF8 COLLATE UTF8_GENERAL_CI;