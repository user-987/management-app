# management-app (Employee Management Application)

Web API of a simple user management application used to add, delete, modify and search for employees.
Service reply is returned in the JSON format

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Required:

```
Java 8
Maven
Lombok
```

### Installing

Install Lombok plugin in the IDE of your choice and build project using Maven.
Then run application and open the following link in the browser:
 
```http://localhost:8080/```
 
to get the API use cases and general information.

### Tests

Testing locally from command line using ```curl```:

* to add a new employee
```
curl -v -X POST localhost:8080/employees/add -H 'Content-Type:application/json' -d '{"name": "John","surname": "Smith", "grade": 1, "salary": 1}'
```

* to delete an employee
```
curl -v -X DELETE localhost:8080/employees/delete/1
```

* to update an employee
```
curl -v -X PUT localhost:8080/employees/update/1 -H 'Content-Type:application/json' -d '{"name": "John","surname": "Smith", "grade": 2, "salary": 2}'
```

Testing locally the search options using browser:

* to get all employees
```
http://localhost:8080/employees
```

* to get an employee by id
```
http://localhost:8080/employees/1
```

* to search by any combination of parameters
```
http://localhost:8080/employees/search?name=John
```
```
http://localhost:8080/employees/search?name=John&grade=1
```
