create table users (pk int serial,name varchar(20),password varchar(10),PRIMARY KEY(pk));
alter table users ADD CONSTRAINT c_unique UNIQUE (name);
create table routes (name varchar(15),description text,PRIMARY KEY(name));
create table points (pk serial,name varchar(15),lat numeric(5,3),lon numeric(5,3),description text,image varchar(20),PRIMARY KEY(pk));
create table valoration (def int,fk_route varchar(15) references routes(name),fk_user int references users(pk));
create table comments (def text,fk_route varchar(15) references routes(name),fk_user int references users(pk));


