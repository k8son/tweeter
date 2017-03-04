##TWEETER
This is a basic Twitter-like API written in Java 8 and Spring Boot, utilising REST principles. The functionality offered is a cut down version of the real thing. In summary it allows users to post messages and follow other users. It also allows the user to view their own messages and timelines. A time line consists of messages posted by users that are being followed. All messages and timeline are displayed in reverse chronological order. 

### Build and execution 
The application can be ran by executing the following maven command into the command line:
```
mvn spring-boot:run
```

### Swagger Documentation
Once the application is running, Swagger documentation for the application can be accessed by entering the following url into a browser:
```
http://localhost:8080/swagger-ui.html
```

## API Usage
- Post a message:
```
POST /user/{userId}/tweet
```
Example body:
{
  "message" : "this is a tweet"
}
 
 

- Get all user messages:
```
GET /user/{userId}/tweet
```


- Get user timeline messages:
```
GET /user/{userId}/timeline
```

----------


- Follow a user:
```
POST /user/{userId}/follow/{followingUserId}
```
Where followingUserId is the user id of the user that is to be followed.



- Get all users being followed:
```
GET /user/{userId}/follow
```

