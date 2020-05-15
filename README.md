# BowlingScores

How to start the BowlingScores application with docker
---
- Requires the following installed:
  - docker
  - docker-compose

1. Go to config.yml file and set the database.url to `jdbc:postgresql://database:5432/postgres`
1. `docker-compose build`
1. `docker-compose up`


How to start the BowlingScores application locally
---
- Requires the following installed:
  - Java 8
  - Maven
  - docker

1. Run `docker run --name bowling-scores-db -e POSTGRES_PASSWORD=pw -p 5432:5432 -d postgres` to create and start the postgresql database
1. Go to config.yml file and set the database.url to `jdbc:postgresql://localhost:5432/postgres`
1. Run `mvn clean install` to build the application
1. Run `mvn liquibase:update` to apply the database changes
1. Start application with `java -jar target/bowling-scores-1.0-SNAPSHOT.jar server config.yml`
1. To check that the application is running enter url `http://localhost:8080`

Health Check
---

To see the applications health enter url `http://localhost:8081/healthcheck`

Endpoints
---
#### POST /scores/{playerId}
Creates records for the `playerId`
##### curl command
`curl --location --request POST 'localhost:8080/scores/1234'`
#### output
```
{
    "playerId": 1234,
    "runningTotal": 0,
    "lastUpdatedFrame": 0,
    "frames": [
        {
            "frameNumber": 1,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        },
        {
            "frameNumber": 2,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        },
        {
            "frameNumber": 3,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        },
        {
            "frameNumber": 4,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        },
        {
            "frameNumber": 5,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        },
        {
            "frameNumber": 6,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        },
        {
            "frameNumber": 7,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        },
        {
            "frameNumber": 8,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        },
        {
            "frameNumber": 9,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        },
        {
            "frameNumber": 10,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        }
    ]
}
```
#### GET /scores/{playerId}
Gets the records for the `playerId`
##### curl command
`curl --location --request GET 'localhost:8080/scores/1234'`
#### output
```
{
    "playerId": 1234,
    "runningTotal": 0,
    "lastUpdatedFrame": 0,
    "frames": [
        {
            "frameNumber": 1,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        },
        {
            "frameNumber": 2,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        },
        {
            "frameNumber": 3,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        },
        {
            "frameNumber": 4,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        },
        {
            "frameNumber": 5,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        },
        {
            "frameNumber": 6,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        },
        {
            "frameNumber": 7,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        },
        {
            "frameNumber": 8,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        },
        {
            "frameNumber": 9,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        },
        {
            "frameNumber": 10,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        }
    ]
}
```
#### PUT /scores/{playerId}
Query params:
- `frameNumber` a number 1 - 10 to specify which frame to update
- `rollNumber` a number 1 - 3 to specify which roll to update
- `pinsDown` a number 1 - 10 to specify the number of pins knocked down

Updates the score for the `playerId` and returns the updated player score. This endpoint can only be called in a sequence. For example, this endpoint cannot be called to update frame 1, then frame 5, or frame 5, then frame 1.
##### curl command
`curl --location --request PUT 'localhost:8080/scores/1234?frameNumber=1&rollNumber=1&pinsDown=5'`
##### output
```
{
    "playerId": 1234,
    "runningTotal": 5,
    "lastUpdatedFrame": 1,
    "frames": [
        {
            "frameNumber": 1,
            "rollOne": 5,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 5,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        },
        {
            "frameNumber": 2,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        },
        {
            "frameNumber": 3,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        },
        {
            "frameNumber": 4,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        },
        {
            "frameNumber": 5,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        },
        {
            "frameNumber": 6,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        },
        {
            "frameNumber": 7,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        },
        {
            "frameNumber": 8,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        },
        {
            "frameNumber": 9,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        },
        {
            "frameNumber": 10,
            "rollOne": 0,
            "rollTwo": 0,
            "rollThree": 0,
            "frameScore": 0,
            "frameType": "OPEN",
            "subsequentRollAddsAvail": 0
        }
    ]
}
```
