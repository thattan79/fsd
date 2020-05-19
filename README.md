` Project Management Application `
----------------

Project Manager Application is a single page application to keep track of upcoming projects and their respective tasks and their status and priorities

An Overview
-----------------
The application allows the user to manage projects and their respective tasks. It allws the user to set their priorities to each project and task.
One manager to each project and task-owner to each task.
Each start task will have parent task(optional), start date and end date.

The Features are,
1. Add/Edit/View/End project
1. Add/Edit/View/End user
1. Add/Edit/View/End task
2. One task can be a parent of another
3. Search fields are available to filter the records

Technologies Used
------------------
1. Spring Boot WAR project with MySql DB for real time and H2 embedded for testing.
2. Node with angular 6 is used for front end
3. Docker Build Image and Deploy into Docker HUB

To run the project
---------------------

Frontend - Use 'ng serve' and the application will be available in 'http://localhost:4200' (with default port)
Backend - Run the spring boot application.
