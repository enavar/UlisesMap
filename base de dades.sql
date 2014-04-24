create table users (pk serial,name varchar(20),password varchar(10),PRIMARY KEY(pk));
create table valoration (def int,foreig key route,foreig key users);
create table comments (def text,foreig key route,foreig key users);
create table points (pk serial,name varchar(15),lat double(5,3),lon double(5,3),description text,image varchar(20),PRIMARY KEY(pk));
create table route1 (registre de punts,description text,name varchar(20));
