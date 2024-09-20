# College Directory Application
A backend application providing a set of REST API endpoints to perform CRUD Operations.
<h1>Spring Boot, PostgreSQL, Spring Security, JWT, JPA, Rest API </h1>
<h5>REST API endpoints </h5>
<hr>
The app defines following CRUD APIs.

**Authentication - ANYONE**

POST `/auth/register`  
Description : Register a new user <br>
Request Body :<br>

    { 
     "username": "string", // Username for the new user 
     "password": "string", // Password for the new user 
     "role": "string", // Role of the new user (e.g., ADMINISTRATOR, STUDENT, FACULTY_MEMBER) 
     "name": "string" // Full name of the new user 
     }


<hr>

POST  `/auth/login` <br>
Description : Login <br>
Request Body :<br>

    { 
     "username": "string", // Username  
     "password": "string", // Password  
     "role": "string", // Role of the user (e.g., ADMINISTRATOR, STUDENT, FACULTY_MEMBER) 
     }


<hr>

**Authentication - ADMINISTRATOR**

POST  `/admin/create-department` <br>
Description : Create a new department <br>
Request Body :<br>
 

    {  
     "name": "string",        // Department Name
     "description": "string"  // Description of the department
    }


<hr>

POST  `/admin/update-department/{departmentId}` <br>
Description : Update a department <br>
Request Body :<br>

    { 
    "name": "string", // New department name 
    "description": "string" // New department description
    }


<hr>

POST  `/admin/update-department/{departmentId}` <br>
Description : Update a department <br>
Request Body :<br>

    { 
    "name": "string", // New department name 
    "description": "string" // New department description
    }


<hr>

GET `/admin/view-departments`

Description : View all existing departments <br>

<hr>

DELETE `/admin/delete-department/{departmentId}`

Description : Delete a department by it's id <br>

<hr>

**Authentication - STUDENT**

   POST `/student/create-profile`

Description : Create a new profile for the logged-in student

Request Body:

    {
       "email": "string",           // Email of the student
       "phone": "string",           // Phone number of the student
       "year": "string",            // Academic year of the student
       "department": {
          "id": departmentId        // ID of the department the student belongs to
       }
    }

   <hr>
   
PUT `/student/update` 

Description : Update the logged-in student’s profile

Request Body:

      {
       "email": "string",           // Updated email
       "phone": "string",           // Updated phone number
       "year": "string",            // Updated academic year
       "department": {
          "id": departmentId        // ID of the department the student belongs to
       }
    }

   <hr>

   GET `/student/view-profile`

**Description**: View the profile of the logged-in student

   <hr>

  GET `/student/view-faculty`

Description : View faculties associated with the logged-in student’s department

<hr>

**Authentication - FACULTY_MEMBER**


   POST `/faculty/create-profile`

Description : Create a new profile for the logged-in faculty

Request Body:

    {
       "email": "string",           // Email of the faculty
       "phone": "string",           // Phone number of the faculty
       "officeHours": "string",     // Office Hours of the faculty
       "department": {
          "id": departmentId        // ID of the department the faculty belongs to
       }
    }

   <hr>
   
PUT `/faculty/update`  

Description : Update the logged-in faculty’s profile

Request Body:

      {
       "email": "string",           // Updated email
       "phone": "string",           // Updated phone number
       "officeHours": "string",     // Updated office hours
       "department": {
          "id": departmentId        // ID of the department the faculty belongs to
       }
    }

   
   <hr>
   
   GET `/faculty/view-profile`

**Description**: View the profile of the logged-in faculty

   <hr>
   
  GET `/faculty/view-students`

Description : View students associated with the logged-in faculty’s department

<hr>



