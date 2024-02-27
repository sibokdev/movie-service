CREATE TABLE movie (
 id BIGSERIAL PRIMARY KEY NOT NULL,
 eidr varchar(255) UNIQUE NOT NULL,
 title varchar(255) NOT NULL,
 director varchar(255) NOT NULL,
 release_year integer NOT NULL,
 publisher varchar(255) NOT NULL,
 sinopsis varchar(1000) NOT NULL,
 image_u_rl varchar(255),
 up_vote_count integer DEFAULT 0,
 down_vote_count integer DEFAULT 0,
 favorite_count integer DEFAULT 0,
 created_date timestamp NOT NULL,
 last_modified_date timestamp NOT NULL,
 version integer NOT NULL
);