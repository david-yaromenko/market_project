create table Product(
id serial not null primary key,
name varchar(60) not null,
category varchar(60) not null,
price float not null,
image_preview_url varchar(255)
);

create table Image(
id serial not null primary key,
image_url varchar(255) not null,
product_id int not null,

foreign key (product_id)
references Product(id)
);