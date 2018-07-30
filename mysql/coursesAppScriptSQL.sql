create database usersjdbc DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;


CREATE TABLE logowanko (
login varchar(50) primary key,
pass  varchar(50) not null,
permission varchar(1)

);

INSERT INTO logowanko values ('user', 'user', '0');
INSERT INTO logowanko values ('admin', 'admin', '1');

CREATE TABLE ankieta (
id INT PRIMARY KEY auto_increment,
name VARCHAR(50) NOT NULL,
last VARCHAR(50) NOT NULL,
java bool, 
python bool,
other VARCHAR(100),
english VARCHAR(50),
kurs VARCHAR(50),
data datetime
);

INSERT INTO ankieta (name, last, java, python, other, english, kurs, data) 
VALUES ('teddy', 'baxter', 1, 1, 'test', 'test', 'test', now());
