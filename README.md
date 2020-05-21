# UserManagementApi

User management api, that provides several REST endpoints to manage user.
Used liquibase for database migration.
Using spring security. All endpoints are secured by basic authentication(username, password). So you have to include 'Authorization' 
header to every request.

Default users:
* username: user, password: password
* username: admin, password: admin

user has access only to GET /users endpoint

admin has access to all endpoints(including swagger)

API documentation can be accessed by admin via http://localhost:8080/swagger-ui.html

Run manually
------------------------------
1. Create postgresql db called 'testtask'
2. Build project `mvn clean install`
3. Run project 'java -jar target/userRequest-management-api-1.0-SNAPSHOT.jar'
4. Test it by calling GET http://localhost:8080/users (with any user)


Run app in docker(along with dockerized postgresql)
--------------------------
1. Build project `mvn clean install`
2. Run docker-compose `docker-compose up`
3. By default app is listening to port 8080

