# Online Coffee Store

Online coffee place startup business, where users can place drinks with
toppings orders and admins can create/update/delete drinks/toppings and have access to the most used
toppings.

Technologies
------------
- `Spring Boot 3.2.0`
- `Java 21`
- `MySQL` 
- `Docker`
- `Docker-Compose`
- `Lombok`
- `Swagger`
- `JUnit`
- `Test Containers`
- `Jacoco`


## Run the System
Follow the steps below to run the system locally.

```bash
git clone https://github.com/tugayesilyurt/coffee-store-api.git
cd coffe-store-backend
mvn clean install
docker-compose up -d
```

## Stop the System
Stopping all the running containers is simple with a single command:

* `docker-compose down`

### Spring Security Basic Auth ###

|    User     |   Password   |
| ----------- | ------------ |
|    admin    |   coffeee    |

### Jacoco test result

Jacoco test result : 

![Jacoco](https://github.com/tugayesilyurt/coffee-store-api/blob/main/assets/jacoco.png)


### Swagger

To view the generated Swagger UI documentation go to: [http://localhost:8090/swagger-ui/index.html#/](http://localhost:8090/swagger-ui/index.html#/)

![Swagger](https://github.com/tugayesilyurt/coffee-store-api/blob/main/assets/swagger.png)


### EndPoints ###

| Service      		| EndPoint                      | Method | Description                                      |
| --------------------- | ----------------------------- | :-----:| ------------------------------------------------ |
| coffee-store-backend  | /v1/drinks  			| POST   | Create Drink           	         	    |
| coffee-store-backend  | /v1/drinks/{id}   		| PUT    | Update Drink             	                    |
| coffee-store-backend  | /v1/drinks/{id}   		| DELETE | Delete Drink            	                    |
| coffee-store-backend  | /v1/toppings   		| POST   | Create Topping             	                    |
| coffee-store-backend  | /v1/toppings/{id}      	| PUT    | Update Topping 	           	            |
| coffee-store-backend  | /v1/toppings/{id}      	| DELETE | Delete Topping 	           	            |
| coffee-store-backend  | /v1/toppings/most-used      	| GET    | Get Most Used Toppings           	            |
| coffee-store-backend  | /v1/orders		      	| POST   | Create Order           	                    |


Things to improve
------------
- `JWT - authentication and authorization`
- `Stock control`
- `Spring AOP for logging`

