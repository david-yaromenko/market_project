create table Users(
id serial not null primary key,
email varchar(60) not null,
password varchar(60) not null,
role varchar(60) not null
);
