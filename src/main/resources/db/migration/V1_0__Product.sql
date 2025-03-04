create table Product(
id serial not null primary key,
name varchar(60) not null,
category varchar(60),
price float not null,
image_path varchar(255)
);

create table Image(
id serial not null primary key,
name varchar(60) not null,
content_type varchar(60) not null,
original_file_name varchar(60) not null,
sizes numeric,
bytes oid,
product_id int not null,

foreign key (product_id)
references Product(id)
);