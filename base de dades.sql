create table users (name varchar(20),password varchar(10),PRIMARY KEY(name));
create table routes (name varchar(15),description text,PRIMARY KEY(name));
create table points (name varchar(15),lat numeric(5,3),lon numeric(5,3),description text,image varchar(20),PRIMARY KEY(name));
create table valoration (def int,fk_route varchar(15) references routes(name),fk_user varchar(20) references users(name),PRIMARY KEY (fk_user,fk_route));
create table comments (def text,fk_route varchar(15) references routes(name),fk_user varchar(20) references users(name),PRIMARY KEY (fk_user,fk_route));


