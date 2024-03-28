# Gardener

### Running locally

First start docker then run:
```shell
> docker run -e POSTGRES_PASSWORD=dev -p 5432:5432 -h 127.0.0.1 postgres
```

Start the backend:
```shell
ENV=dev sbt run
```