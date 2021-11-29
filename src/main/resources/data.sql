create table authors (
id int AUTO_INCREMENT PRIMARY KEY,
author varchar(200) NOT NULL
);


create table genres (
id int AUTO_INCREMENT PRIMARY KEY,
genre varchar(200) NOT NULL
);


create table books (
id int AUTO_INCREMENT PRIMARY KEY,
title varchar(200) NOT NULL,
authorId varchar(200),
genreId varchar(200),
FOREIGN KEY (authorId) REFERENCES authors(id),
FOREIGN KEY (genreId) REFERENCES genres(id)
);

