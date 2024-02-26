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