# inventory
This is a simple inventory management system built using Spring Boot. It allows users to manage their inventory by adding, updating, and deleting items.
## Features
- Add new items to the inventory
- Update existing items in the inventory
- Delete items from the inventory
- View all items in the inventory
## Technologies Used
- Spring Boot
- Spring Data JPA
- Postgres Database
- Java 21 or higher
## Getting Started
To get started with this project, follow the instructions below:
1. Clone the repository:
```git clone https://github.com/Nutefe/inventory.git ```
2. Navigate to the project directory:
```cd inventory ```
3. Run docker compose to start and init database:
```docker compose up -d ```
4. Build and run the application:
```./mvn spring-boot:run ```
5. Access the application at `http://localhost:8787`.
6. Use the API endpoints to manage your inventory. You can use tools like Postman or cURL to interact with the API.
## API Endpoints
`` You can paginate the results by adding `page`, `size`, `sort=name,asc` query parameters to the GET requests. For example, to get the first page of dealers with a page size of 10, you can use the following endpoint:
```http://localhost:8787/dealers?page=0&size=10 ```
## CURL examples
1. Dealer Management:
- Add a new dealer:
```curl -u user:password -H "X-Tenant-Id: t1" -H "Content-Type: application/json"   -d '{"name":"Dealer A","email":"a@d.com","subscriptionType":"PREMIUM"}'   http://localhost:8787/dealers ```
- Get all dealers:
```curl -u user:password -H "X-Tenant-Id: t1" http://localhost:8787/dealers ```
- Update a dealer:
```curl -u user:password -H "X-Tenant-Id: t1" -H "Content-Type: application/json"   -d '{"name":"Dealer A Updated","email":"a@.com","subscriptionType":"BASIC"}'   http://localhost:8787/dealers/${dealerId} ```
- Delete a dealer:
```curl -u user:password -H "X-Tenant-Id: t1" http://localhost:8787/dealers/${dealerId} ```
2. Vehicle Management:
- Add a new vehicle:
```curl -u user:password -H "X-Tenant-Id: t1" -H "Content-Type: application/json"   -d '{"model":"Toyota","price":15000,"status":"AVAILABLE","dealerId":${dealerId}}'   http://localhost:8787/vehicles ```
- Get all vehicles:
```curl -u user:password -H "X-Tenant-Id: t1" http://localhost:8787/vehicles ```
- Search vehicles by model:
```curl -u user:password -H "X-Tenant-Id: t1" http://localhost:8787/vehicles/search?model=Toyota ```
- Update a vehicle:
```curl -u user:password -H "X-Tenant-Id: t1" -H "Content-Type: application/json"   -d '{"model":"Toyota","price":16000,"status":"AVAILABLE","dealerId":${dealerId}}'   http://localhost:8787/vehicles/${vehicleId} ```
- Delete a vehicle:
```curl -u user:password -H "X-Tenant-Id: t1" http://localhost:8787/vehicles/${vehicleId} ```
3. Admin 
- Get count By Subscription:
```curl -u admin:password -H "X-Tenant-Id: t1" http://localhost:8787/admin/dealers/countBySubscription ```

