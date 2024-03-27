# Todolist

## Table Of Content
* [Introduction](#Introduction)
* [SetUp](#SetUp)
* [Features](#Features)
* [EndPoint](#end-points)


# SetUp
* install git
* Install mySql for proper database functions
* Clone the project by following the cloning process
* Ensure your project is on the right server.port
* install jdk version 21
* Click on this link (https://github.com/Oluwatobiloba-ojo/TodoListApplication.git)
* Open your Post man Application,paste the accurate url on the given url space
* Ensure all dependencies in the the above project are well injected in your pom.xml

# Introduction
A todolist allows you to prioritize the tasks that are more important. This means you don't waste time on tasks that don't require your immediate attention. It also helps in showing your the due date of task which is schedule on the todolist, This application is built on java, maven spring-boot, and mongodb for storage

## **Features**

* User registration

* User Login

* Create Task

* View A Day TodoList

* View All TodoList

* Delete A Task

* Delete All Task

* Update A Task Message

* Update Task DueDate

* Delete Account

* View A Particular Task



# End-Points

## Register-Request

* _**Register request is where the user can be able to register or create an account, it collect username and password, which is saved in database and return message**_

Request:

* Url : `http://localhost:9005/api/user/register`

* Method: Post

* Header :
  * Content-type: application\json

* Body
```
JSON:  {"username" : "matthew","password":"Password45@"}
```
* Fields : 
  * `username` (required, String): The username of the user 
  * ``password`` (required, String): The password of the user

# Response 1

_This is a successful response_

* Status code: `202 Accepted`

Body :
```
JSON: {"data": {"message": "Account created"}"successful": true }
```

# Response 2

* unsuccessful request due to creating a user which already exist in the data base

* Status code: `400 Bad request`

* Body:
```
JSON: {"data": {"message": "Client already exist"},"successful": false }
```

# Response 3:

* unsuccessful request due to creating with the wrong password format

* Status code: `400 Bad request`

* Body : 
```
JSON :

{
    "data": {
    "message": "Weak Password"
    },
    "successful": false
}
```

## Login Request

* This end point allows verification of the user password to ensure is valid and so that he can have access to other feautures of the todolist.

## Request

* Url: `http:\\localhost:9005\api\user\login`
* Method: POST
* Header:
  * Content-type : application\json 
* Body:
```
JSON:
  {
        "username":"delighted",
        "password":"password"
  }
```
* Fields:
  * username (required, String) The username of the user
  * password (required, String) The password of the user

# Response 1

* successful request to logIn

* Status code : `202 Accepted`

* Body:
```
JSON:
    {
      "data": {
      "message": "You don log in !!!!!!!!!!!"},
      "successful": true 
    }
```

# Response 2

* unsuccessful request due to incorrect email or password

* Status code : `400 bad request`

* Body:
```
JSON :
  {
    "data": {
    "message": "Invalid details"
    },
    "successful": false
  }
```

## Create-Task-Request End-Point

* This end-point is used to create a task on the user to do list this request collect a this from the user, username, message or description, dueDateTime and returns that task has been created.
* A code sample using JSON on postman is shown below

# Request

* Url: `http://localhost:9005/api/user/createTask`
* Method: POST 
* Header: 
  * Content type: application\json 
* Body:
```
JSON:
{
  "username": "delighted",
  "message": "i am becoming  a man",
  "dueDateTime":{
                "date":{
                       "year": 2024,
                       "month": 10,
                        "day": 20
                },
  "hour": 9,
  "minute": 30
  }
}
```
* Fields
  * `username` (required, String): `the username of the user`
  * `message/description` (required, String): `the message of the task want to create`
  * `dueDateTime` (required, int) `the due date for the task to be executed`

# Response 1

*successful request* 

* Status code: 201 Created

* Body:
```
JSON
{
      "data": {
              "message": "We don create Task ooh"
      },
              "successful": true
}
```

# Response 2

_unsuccessful request due to invalid details either from invalid username_

* Status code: `400 Bad request`

* Body
```
JSON
{
    "data": {
          "message": "Client does not exist"
    },
          "successful": false
}
```


## View-A-Day-Task

*This request allows user to view their task created in a Day of their choice this request will be collecting username, date wished to see all task. it is a get request*

*A code sample using json on postman*

# Request
* Url: `http:\\localhost:9005/api/user/viewADayTask`
* Method: GET
* Header: 
  * Content-type: application\json 
* Body
```
JSON
{
      "username": "delighted,"
      "date": {
              "year": 2023,
              "month": 12,
              "day": 19
      }
}
```
* Fields:
  * username (required, String) the username of the user
  * date(required, int) the date the user wants to see or find

# Response 1
*successful request*
* Status code: 200 Ok
* Body:
```
JSON:
{
      "data": [
          {
            "id": "6603da7eea638b45d4c7b268",
            "message": "i am becoming  a man",
            "localDate": "2024-03-27",
            "dueDateTime": "2024-10-20T09:30:00",
            "todoId": "6603da52ea638b45d4c7b267"
          },
          {
            "id": "6603db45ea638b45d4c7b269",
            "message": "i am becoming  a man and i went to school",
            "localDate": "2024-03-27",
            "dueDateTime": "2024-10-20T09:30:00",
             "todoId": "6603da52ea638b45d4c7b267"
          }
              ],
      "successful": true
}
```

# Response 2
*unsuccessful request due to that the user does not exist*

* Status code: `404 Not Found`
* Body:
```
JSON:
{
    "data": "Client does not exist",
     "successful": false
}

```
# Response 3
*unsuccessful request due to that the their is no task created that day*
* Status code: `404 Not Found`
* Body
```
JSON:
{
    "data": "No task created",
    "successful": false
}
```

## View-A-Task

*This view-a-task end point allows the user to view a single task in the list of task of the users. it is a get request*

*A code sample in Post man using JSON is shown below.*

# Request

* URL: `https:\\localhost:9005/api/user/viewATask`
* Method: GET
* Header: 
  * Content-type: application\json
* Body
```
JSON
{
      "username": "matthew,"
      "dateCreated": {
                      "year": 2023,
                      "month": 12,
                      "day": 23
      },
      "message": "I am becoming a man"
}
```
* Fields
  * username (required, String) this is the username of the user
  * dateCreated(required, Date) this is the date which it was created
  * message (required, String) this is the description of the task of the user

# Response 1
*successful request*

* Status code: 200 OK
* Body:
```
JSON
{
    "data": {
              "message": {
                          "id": "658723a72c695f40947d58dc",
                          "message": "i am becoming  a man",
                          "localDate": "2023-12-23",
                          "dueDateTime": "2024-10-20T09:30:00",
                          "todoId": "6584e2942c695f40947d58db"
              }
    },
    "successful": true
}
```
# Response 2
_unsuccessful due to that the username given does not exist_

* Status code: `400 Bad Request`
* Body
```
JSON
{
    "data": {
              "message": "Client does not exist"
    },
    "successful": false
}
```
# Response 3
_unsuccessful due to that there is no task created in that day or if the message does not exist in that date_

* Status code: `400 Bad Request`
* Body
```
JSON
{
      "data": {
                "message": "Invalid details to find"
      },
      "successful": false
}
```


## Delete-list-of-task

_This end-point ensures that user can delete all task belonging to the user, this is a delete mapping_

# Request
* Url: http:\\localhost:9005/api/user/deleteAll/ope
* Method: Delete
* Parameters:
  * username : String

# Response 1
_successful request_

* Status code: `200 OK`
* Body :
```
JSON
{
    "data": {
              "message": "Deleted"
    },
    "successful": true
}
```
# Response 2
_unsuccessful due to that the user does not have a task at all_

* Status code: `404 Not Found`
* Body :
```
JSON
{
      "data": {
                "message": "No task found"
      },
      "successful": false
}
```



## Delete-A-Task

_This end-point ensures that the user has the act to delete a task which belong to that user_

# Request
* Url : `http:\\localhost:9005/api/user/delete`
* Method: Delete
* Body:
```
JSON:
{
      "username":"ope",
      "dateCreated": {
                        "year": 2023,
                        "month": 12,
                        "day":19
      },
      "message":"i am going to shop"
}
```
* Fields
  * username (required String) this is the username of the user
  * dateCreated(required Date) this is the date we create the task
  * message (required String) this is the description of the task

# Response 1
_successful request_

* Status code: `200 OK`
* Body:
```

JSON:
{
    "data": {
              "message": "Deleted Shop market done !!!!"
    },
    "successful": true
}
```
# Response 2
_unsuccessful request due to invalid details in the request either message and date created_

* Status Code : `404 Not found`
* Body
```
JSON
{
  "data": {
            "message": "No task found"
  },
  "successful": false
}
```

## Update-A-Task-Due-Date

_This is an end-point that is used for updating the due date of a task_

# Request
* Url : `http:\\localhost:9005/api/user/updateDueDate`
* Method: PUT
* Body
```
JSON
{
    "username": "delighted",
    "description": "I am going to a man house",
    "dateCreated":{
                    "year":2023,
                    "month":12,
                    "day":19
    },
    "dueDate": {
                "date":{
                        "year":2024,
                        "month":9,
                        "day":10
                },
                "hour":11,
                "minute":50
    }
}
```
* Fields:
  * username (required String) this username of the user
  * description (required String) This is the description of the task
  * dateCreated(required Date) This is the date the task was created
  * dueDate(required DateTime) This is the dueDate of the task

# Response 1
_successful request_

* Status code: `200 OK`
* Body
```
JSON
{
    "data": {
              "message": "We don update ooh!!!!!"
    },
    "successful": true
}
```
# Response 2
_unsuccessful request cause their is an invalid user_

* Status code : `400 Bad Request`
* Body
```
JSON
{
    "data": {
              "message": "Invalid user"
    },
    "successful": false
}
```
# Response 3
_unsuccessful request due to that invalid details_

* Status code: `400 Bad Request`
* Body: 
```
JSON
{
    "data": {
              "message": "Task does not exist"
    },
    "successful": false
}
```


## Delete-Account
_this end point is allowing the user to delete his account with only the username of the user_

# Request
* Url: `https:\\localhost:9005/api/user/deleteAccount/ope`

* Method : Delete

* Parameter:
  * username: String

# Response 1
_successful request_

* Status code: `200 OK`

* Body :
```
JSON:
{
    "data": "Deleted…",
    "successful": true
}
```
# Response 2
_unsuccessful requests due to invalid username of the user_

* Status Code: `400 Bad Request`

* Body
```
JSON
{
  "data": "Client does not exist",
  "successful": false
}
```


## Update-message-of-A-task
_This end-point allows the user to be able to update the message\description of the task_

# Request
* Url: `https:\\localhost:9005/api/user/updateTask`

* Method: PUT

* Body
```
JSON
{
    "username":"ope,"
    "dateCreated": {
                    "year": 2023,
                    "month": 12,
                    "day": 23
    },
    "newMessage": "I am becoming a man,"
    "oldMessage": "Shopping mall"
}
```
* Field
  * username (required String) the username of the user
  * dateCreated(required Date) the date at which the task was created
  * newMessage(required String) the new update message which the user want to update
  * oldMessage(required String) the old message which the user want to change, exist before

# Response 1
* Status Code: `200 OK`
* Body
```
JSON
{
    "data": {
                "message": "We don update am ooh"
    },
    "successful": true
}
```
# Response 2

_unsuccessful details due to when the new Message has been created already and the date created is the same_

* Status Code: `404 Not Found`

* Body
```
JSON
{
    "data": {
                "message": "Task has been created already"
    },
    "successful": false
}
```
# Response 3
_unsuccessful details due to when there is invalid details_

* Status Code `404 Not Found`

* Body
```
JSON
{
    "data": {
                "message": "Task does not exist"
    },
    "successful": false
}
```

