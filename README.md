# Poll Service
Service responsible for Polls creation and search

# Running
- Project can be executed by running it as docker-compose up
- Api can be accessed by the address http://localhost:8080/swagger-ui.html
- To run locally, with a local mongo instance: ./gradlew bootRun --args='--spring.profiles.active=dev'

# Technologies
- Spring boot is the base framework for the project
- MongoDB was used for persistence, because for the model described, 'polls', 
in a relational database I would have to make assumptions about the underlying
database model, and I could avoid it by describing the model as a document
- A swagger interface is used for easy access and documentation to the API 

# Missing Points
- Use openapi generator to define the endpoint and DTO definitions and decouple those from the DB model
- Add a migration lib for the project to upload data to mongo for tests and any possible initial setup
- Depending on the volume of data for polls, a full-text search tool could be used for fields like title
- An update endpoint for polls as a HTTP Patch
- User and password for mongo prod instance
