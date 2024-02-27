# Catalog Service

This application was developed by [Alfredo Suarez](https://github.com/sibokdev)
It's a movie catalog service to manage all crud operations.

## REST API

|       Endpoint	       |  Method  | Req. body | Status | Resp. body | Description    		   	                                        |
|:---------------------:|:--------:|:---------:|:------:|:----------:|:-------------------------------------------------------------|
|          `/`          |  `GET`   |           |  200   |   String   | Returns a message to check server upd and running            |
|       `/movies`       |  `GET`   |           |  200   |  Movie[]   | Get all the movies in the catalog, sorted by favorite count. |
|       `/movies`       |  `POST`  |   Movie   |  201   |   Movie    | Add a new movie to the catalog.                              |
|                       |          |           |  422   |            | A Movie with the same EIDR already exists.                   |
|   `/movies/{eidr}`    |  `GET`   |           |  200   |   Movie    | Get the Movie with the given EIDR.                           |
|                       |          |           |  404   |            | No Movie with the given EIDR exists.                         |
|   `/movies/{eidr}`    |  `PUT`   |   Movie   |  200   |   Movie    | Update the Movie with the given EIDR.                        |
|    `/movies/years`    |  `GET`   |           |  200   |  Movie[]   | Return list of movies grouped by release year.               |
| `/movies/years/{releaseYear}` |  `GET`   |           |  200   |  Movie[]   | get list of movies by release year.                          |
| `/movies/{eidr}/vote` |  `PUT`   |           |  200   |   Movie    | Votes a movie up,down or as favorite.                        |
|                       |          |           |  200   |   Movie    | Create a Movie with the given EIDR.                          |
|   `/movies/{eidr}`    | `DELETE` |           |  204   |            | Delete the Movie with the given EIDR.                        |

On root folder you will find the postman exported collection MoviesChallengeCollection.postman_collection.json

## Local Development DB
More easy way to get the DB up and running is to create and start a docker container with next command:
```
docker run -d --name movies-postgres -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=moviesdb_catalog -p 5432:5432 postgres:14.4
```

If you cant run a docker container please install a local postgres DB with next credentials:
user: user
password: password
db: moviesdb_catalog

## Running tests
To execute test just build the project with gradle wrapper, if first time fail please give a second try.
```
./gradlew clean build
```

The project is configured to run code coverage report, you can find the generated report on 
```
/build/reports/jacoco/test/html/index.html
```
If you want to access the documentation, after run the app, please check the API docs on:
http://localhost:9001/swagger-ui/index.html

## Dockerizing the application 
At the root folder there is a Dockerfile to create the docker image of the microservice, once you are sure that your project test passed
build the jar
```
./gradlew bootjar
```
Next you need to build the image, please execute at the root folder:
```
docker build -t movie-service .
```
## GitHub Action
Added a github action under /.github/workflows/commit-stage.yml

The action will generate the docker image and upload it to github packages registry, link the package to the source code.
Also will execute tests and analysis with grype to create a vulnerability report on the application libraries and also for the container.
All of this will happen on the commit stage to main branch.

You can see the result of the github actions on the repository:
https://github.com/sibokdev/movie-service

For the vulnerability report please visit:
https://github.com/sibokdev/movie-service/security/code-scanning