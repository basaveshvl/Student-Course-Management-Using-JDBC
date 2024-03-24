SQL:DATABASE

TEAM MEMBER: ARCHIT GANAPATI AVADHANI[1KS20CS011], BASAVESH V L[1KS20CS013], CHETHAN S[1KS20CS019]

#####################################################################################################################################################

CREATE DATABASE StudentCourse;
USE StudentCourse;
CREATE TABLE Student(
usn VARCHAR(10) PRIMARY KEY,
Sname VARCHAR(15),
Email VARCHAR(15),
username VARCHAR(10),
pass VARCHAR(10),
cour1 VARCHAR(5),
cour2 VARCHAR(5),
cour3 VARCHAR(5)
);
DESC Student;
CREATE TABLE Course(
c_id VARCHAR(5),
c_name VARCHAR(10),
c_price INT,
faculty VARCHAR(15)
);
DESC Course;
CREATE TABLE Admins(
a_name VARCHAR(15),
a_username VARCHAR(15),
a_pass VARCHAR(15),
a_email VARCHAR(20)
);
DESC Admins;
INSERT INTO Admins VALUES ('Archit','Admin1','archit123','A1@gmail.com');
INSERT INTO Admins VALUES ('Basavesh','Admin2','basavesh123','A2@gmail.com');
INSERT INTO Admins VALUES ('chethan','Admin3','chethan123','A3@gmail.com');
SELECT * FROM Admins;
INSERT INTO Course VALUES('C1','java',2000,'Akash');
INSERT INTO Course VALUES('C2','sql',1800,'Ananya');
INSERT INTO Course VALUES('C3','c++',2000,'Mahesh');
INSERT INTO Course VALUES('C4','python',1500,'Karthik');
SELECT * FROM Course;
