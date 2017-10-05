# Run this project

1, Clone the project and convert it to a Maven project

2, Run the main method in AcltestApplication class

3, The app will be running on http://localhost:8080

# How it works

This project demonstrates the basic usage of Spring ACL.

### Users in the embedded database:

1, username: "james", password: "password"

2, username: "jane", password: "password"

3, username: "john", password: "password"

4, username: "mike", password: "password"

### Objects in the embedded database:

=======================Distributor 5============================

                                                       
 
================Farmer 6==============Farmer 7==================

             
        
================Farm 15================Farm 16==================

james has access to Distributor 5 and therefore all the child objects of Distributor 5

jane has access to Farmer 6 and therefore all the child objects Farmer 6

mike has access to Farmer 7 and therefore all the child objects Farmer 7

john does not have access to anything

### To Test

Authorize as james and make a get request to http://localhost:8080/farmers 
and you should see both farmers.

Feel free to play around with the other endpoints

http://localhost:8080/farms

http://localhost:8080/distributors


# How to set up Spring ACL in Spring Boot

#### Create database schema
Vendor specific schemas can be found on https://docs.spring.io/spring-security/site/docs/current/reference/html/appendix-schema.html

#### Configure Spring Security
See SpringSecurityConfig class

#### Configure Spring ACL
See ACLConfig class

# Further imformation
For more information on acl database tables go to http://krams915.blogspot.co.uk/2011/01/spring-security-3-full-acl-tutorial.html

