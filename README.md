# Catalog Service

This application was developed by [Alfredo Suarez](https://github.com/sibokdev)
It's a movie catalog service to manage all crud operations.

## REST API

|       Endpoint	       |  Method  | Req. body | Status | Resp. body | Description    		   	                      |
|:---------------------:|:--------:|:---------:|:------:|:----------:|:-------------------------------------------|
|       `/movies`       |  `GET`   |           |  200   |  Movie[]   | Get all the movies in the catalog.         |
|       `/movies`       |  `POST`  |   Movie   |  201   |   Movie    | Add a new movie to the catalog.            |
|                       |          |           |  422   |            | A Movie with the same EIDR already exists. |
|   `/movies/{eidr}`    |  `GET`   |           |  200   |   Movie    | Get the Movie with the given EIDR.         |
|                       |          |           |  404   |            | No Movie with the given EIDR exists.       |
|   `/movies/{eidr}`    |  `PUT`   |   Movie   |  200   |   Movie    | Update the Movie with the given EIDR.      |
| `/movies/{eidr}/vote` |  `PUT`   |           |  200   |   Movie    | Votes a movie up,down or as favorite.      |
|                       |          |           |  200   |   Movie    | Create a Movie with the given EIDR.        |
|   `/movies/{eidr}`    | `DELETE` |           |  204   |            | Delete the Movie with the given EIDR.      |


## Local Development DB
More easy way to get the DB up and running is to create and start a docker container with next command:
```
docker run -d --name movies-postgres -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=moviesdb_catalog -p 5432:5432 postgres:14.4
```

If you cant run a docker container please install a local postgres DB with next credentials:
user: user
password: password
db: moviesdb_catalog

Runing tests
To execute test just build the project with gradle wrapper
```
./gradlew clean build
```

The project is configured to run code coverage report, you can find the generated report on 
```
/build/reports/jacoco/test/html/index.html
```
If you want to access the documentation, after run the app, please check the API docs on:
http://localhost:9001/swagger-ui/index.html