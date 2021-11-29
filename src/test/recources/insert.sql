INSERT INTO authors(author) VALUES ('author1');

INSERT INTO genres(genre) VALUES ('genre1');

INSERT INTO books(title, authorId, genreId) VALUES (
'title1',
select id from authors where author = 'author1',
select id from genres where genre = 'genre1');



INSERT INTO authors(author) VALUES ('author2');

INSERT INTO genres(genre) VALUES ('genre2');

INSERT INTO books(title, authorId, genreId) VALUES (
'title2',
select id from authors where author = 'author2',
select id from genres where genre = 'genre2');



INSERT INTO authors(author) VALUES ('author3');

INSERT INTO genres(genre) VALUES ('genre3');

INSERT INTO books(title, authorId, genreId) VALUES (
'title3',
select id from authors where author = 'author3',
select id from genres where genre = 'genre3'
 );