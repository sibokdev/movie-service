CREATE TABLE book (
 id BIGSERIAL PRIMARY KEY NOT NULL,
 eidr varchar(255) UNIQUE NOT NULL,
 title varchar(255) UNIQUE NOT NULL,
 director varchar(255) NOT NULL,
 releaseYear integer NOT NULL,
 publisher varchar(255) NOT NULL,
 sinopsis varchar(255) NOT NULL,
 imageURL varchar(255) NOT NULL,
 upVoteCount integer NOT NULL,
 downVoteCount integer NOT NULL,
 favoriteCount integer NOT NULL,
 created_date timestamp NOT NULL,
 last_modified_date timestamp NOT NULL,
 version integer NOT NULL
);