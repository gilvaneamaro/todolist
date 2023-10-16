# todolist
API Rest that allows create an user and tasks, list and update tasks created. The application uses an in-memory database (H2) and performs user validations in task listing and updating.

# HTTP Requests
Try out the API using an application to test requests, such as [Postman](https://www.postman.com/).

## Creating user
```
// POST: https://todolist-ph72.onrender.com/users/
{
    "name":"Gilvane Amaro",
    "username":"gilvaneamaro2",
    "password":"1234567"
}
```
## Creating tasks
```
// POST: https://todolist-ph72.onrender.com/users/
{
    "description":"tarefa teste",
    "title":"titulo teste",
    "priority":"alta",
    "startAt":"2023-10-19T11:17:10",
    "endAt":"2023-10-25T12:30:00"
}
```
## Loading tasks
```
// GET: https://todolist-ph72.onrender.com/tasks/
```
## Updating tasks
```
// PUT: https://todolist-ph72.onrender.com/tasks/{taskID}
{
    "title":"new title",
    "description":"new description"
}

```
