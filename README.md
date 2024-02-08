Online Bookstore System
About

The backend system offers customers the ability to browse through available books, request books for borrowing, and
manage the borrowing and return dates. Additionally, it  enables administrators to maintain the inventory by adding or
deleting books as needed.

Admin Have Following Access for this online store site:
- Add New Books.
- Update book details.
- Manage the inventory(eg. setbook availablity)
- Remove Books.


Customers are able to:
- browse books by categories
- View book details
- Request to borrow a book
- Return a borrowed book


Technologies used:-
1. Back-End Development:
- Java [JDK 19]
- SQL (H2 InMemory database embedded)
- Spring boot [ 3.2.2]

3. Database:
- H2
- tables can be viewed here: http://<HOST>:8080/h2-console

4. Testing the application
- Execute the attached jar file, the application should start on port:8080
- Using your preferred HTTP client, make calls to the exposed APIs/endpoints that have been documented with Swagger
    Note: SWAGGER UI is strictly for API documentation (including sample payloads) and NOT for testing
        http://<HOST>:8080/swagger-ui/index.html#/
- Every HTTP request should include a header 'Authorization' with value 'admin' for all admin functionality

5. Assumptions:
 - User is already authenticated by a 3PP OAUTH server, as this was not in scope for this task
 - The value 'admin' for header parameter would typically mimic the actual bearer token in production

6. Recommendation Service
 - The recommendation service 










