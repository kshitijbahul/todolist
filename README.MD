To run the existing project without building it 
    `docker-compose up` will make the application available on  `localhost:8120`
    
To run build the project and run it use the following follwing 
    `./gradlew bootRun` it runs the application available on port `localhost:8007`

To build the jar run the following command 
    `./gradlew bootJar`

The microservice exposes the following end points for TODOs

Accounts    

    Get All Todos: curl -X GET http://localhost:8129/todo?page=0&size=10  -H 'Content-Type: application/json'
    Create Todo : curl -X POST http://localhost:8129/todo  -H 'Content-Type: text/plain' -d 'Todo Text to enter'
    Get a Todo : curl -X GET http://localhost:8129/todo/{todoid}  -H 'Content-Type: application/json'
    Update a Todo : curl -X PUT  http://localhost:8129/todo/{todoid} -H 'Content-Type: application/json' -d '{"id": 7,"description": "Buld Gen Todo 0","status": "DONE"}'
    Delete a todo : curl -X DELETE http://localhost:8129/todo/{todoid} 

Bonus:To Create Todo quickly use
 
    curl  -X POST http://localhost:8129/todo/create/{noOftodos} -H 'Content-Type: application/json'

Application Uses 

    Springboot
    Lombak
    Mockito
    Plain HTML
    Plain Javascript 
    CSS

   