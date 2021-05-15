# power-jobs

### job processing application

## Description

- API allows to create a task, read the status and the results of the tasks
- When task is created user recieves the unique ID of the task
- The user can check the status and the results of the task using recieved ID
- Provided docker-compose file is providing with ease-startup od application
- Tasks are put into queue, the status of task may be:
    - _prepared_ - task is put into processing queue
    - _running_ - application is computing the result of the task at the moment
    - _finished_ - application processed the task and result is accessible
- built with Java SE 14 and Spring-Boot


## Starting application

#### Requirements

- Maven
- Java Development Kit 14
- Docker (optional for development)

There are two ways to start the application

1. Development mode using internal H2Database

```shell
mvn spring-boot:run
```

2. In production ready environment

```shell
docker-compose up
```

By default application is starting at port 8080.

## API documentation

User is able to check the API documentation and interact with it under the URL when application is started.

```
http://{host}:{port}/api-docs/ui
```
| URL                             | HTTP method | Request body example        | Response                                                                                                             | Description              |
|---------------------------------|-------------|-----------------------------|----------------------------------------------------------------------------------------------------------------------|--------------------------|
| http://{host}:{port}/tasks      | POST        | {"base": 2, "exponent": 10} | {"id": 1}                                                                                                            | Create new task          |
| http://{host}:{port}/tasks/{id} | GET         |                             | {"id": 1, "base": 2, "exponent": 10, status: "Prepared" }                                                            | Get task by id           |
| http://{host}:{port}/tasks      | GET         |                             | [{"id": 1, "base": 2, "exponent": 10, status: "Prepared" },{"id": 1, "base": 2, "exponent": 10, status: "Prepared" } | Get all registered tasks |


Full [postman collection](powerjobs.postman_collection.json) for this application

## Personal assessment

Great things I did:
- created concurrent implementation for task executor service using ThreadExecutor, Queue etc.
- extended task statuses of PREPARED
- used ResourceBundles for Task statuses in case of localization
- integrated openapi library with swagger and documented API
- integrated flyway for database migrations in the future
- used Spring's convertors
- used Spring's properties to make application more customizable (ex. number of processing threads)

Things I would to do if I had more time:
- code refactoring (in my opinion the code in package domain is mixed too much with logic and I should think about better organization of async package)
- write more tests, the coverage is low
- keep good history of building the application (commit often)

## Desclimer

To simulate long working tasks I've put some additional code into the task code.

[PendingTask.java](src/main/java/com/github/siwonpawel/powerjobs/domain/PendingTask.java#L28-L51)
```java
    /* some code */

    @Override
    public void run() {
        int exponent = task.getExponent().intValue();

        /*
            for loop for counting progress, normally I would use Math.pow() or BigInteger.pow()
            artificalWait() is made to make task seem to be 'heavly processing'
         */
        for(int i = 1; i <= exponent; i++) {
            artificalWait();
            this.progress = i * 100 / exponent;
            if(i == 1) {
                task.setResult(task.getBase());
                continue;
            }
            task.setResult(task.getResult().multiply(task.getBase()));
        }
    }

    private void artificalWait() {
        for(int i = 0; i < 100_000_000; i++) {

        }
    }
    
    /* some code */
```