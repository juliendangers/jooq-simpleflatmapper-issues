# Instructions

Laucnh a Postgres DB:

```
docker run -p 5432:5432 -e POSTGRES_PASSWORD=azerty -e POSTGRES_USER=service-questionnaire  -e POSTGRES_DB=test -d postgres
```

Define 3 env variable:

```
DB_USERNAME=service-questionnaire
DB_URL=jdbc:postgresql://localhost:5432/test
DB_PASSWORD=azerty
```

Build project with env variables:

``` ./gradlew build```

Run project :)

# Tests

3 endpoints are available to reproduce the issue with List<Tuple>:

The first and second ones are the one failing :
http://localhost:8080/questionnaires/0a2e068d-96fd-4eb9-b3ea-c4143df36a38?type=1
http://localhost:8080/questionnaires/0a2e068d-96fd-4eb9-b3ea-c4143df36a38?type=2

The thirs one works fine:
http://localhost:8080/questionnaires/0a2e068d-96fd-4eb9-b3ea-c4143df36a38?type=3
