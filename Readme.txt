Cafe Management System API
This project is an API for a Cafe Management System. It allows managers to manage employees' data, working hours, and provide assistance to employees. The API is built using Spring Boot framework with Java programming language, and the data is stored in MySQL.

Technologies Used
Eclipse
Postman
MySQL
Spring Boot
Java

API Endpoints
GET /pcad/welcome: returns a welcome message.
POST /pcad/Login: authenticates the user's login details using the mobile number and password, and returns a JWT token if the authentication is successful.
POST /pcad/addEmployee: adds an employee to the database. Only managers are authorized to access this endpoint.
POST /pcad/addEmployees: adds multiple employees to the database. Only managers are authorized to access this endpoint.
GET /pcad/employees: returns a list of all employees. Only managers are authorized to access this endpoint.
GET /pcad/employeeByName: returns a list of employees matching the given name. Only managers are authorized to access this endpoint.
GET /pcad/employeeByMobile/{mobile}: returns an employee's data based on their mobile number. Only managers are authorized to access this endpoint.
DELETE /pcad/delete/{id}: deletes an employee's data based on their ID. Only managers are authorized to access this endpoint.
GET /pcad/Profile: returns the employee's data based on their JWT token. Only employees are authorized to access this endpoint.
PUT /pcad/updateMobile: updates the employee's mobile number based on their JWT token. Only employees are authorized to access this endpoint.
PUT /pcad/updateEmail: updates the employee's email address based on their JWT token. Only employees are authorized to access this endpoint.
PUT /pcad/updateEntryExitCode: updates the employee's entry and exit code based on their JWT token. Only employees are authorized to access this endpoint.
PUT /pcad/EntryExit: registers the employee's entry and exit time using their unique entry and exit code. Both managers and employees are authorized to access this endpoint.
PUT /pcad/Exit/{id}: manually logs out the employee based on their ID and the given date and time. Only managers are authorized to access this endpoint.
How to Run
Clone the repository
Import the project into Eclipse
Run the application
Use Postman to make requests to the API endpoints

Contribution Guidelines
This project is open to contributions. If you find any issues or would like to suggest an improvement, feel free to create a pull request.