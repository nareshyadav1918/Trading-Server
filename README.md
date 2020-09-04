
# Getting Started

### Prerequisites
	Java 8 or above
	Lombok plugin

### Install and Build ( using maven )
	1. Go to the root folder
	2. run "mvn clean"
	3. run "mvn install"
	4. run "java -jar target/system-0.0.1-SNAPSHOT"

### Calculation and logic

```

    Rate 	Qty. 	Amt. 		Action
    -----------------------------------
    10.00 	1000 	10000.00 	Buy
    
    15.00 	1000 	15000.00 	Buy
    
    12.00 	-500 	-6000.00 	Sell
    
    11.00 	500 	5500.00 	Buy
    
    
    Current Stock Rate: 14.00
    
    Total amount buy : 30500.00
    Total amount sell = 6000
    Current buy amount holding = 24500
    
    total stock buy = 2500
    total stock sell = 500
    Current stock holding = 2000
    
    Average price
    ----------------------------------------------------
    =(Current buy amount holding)/(Current stock holding)
    =24500/2000
    = 12.25 Per Unit
    
    Profit/Loss
    -----------------------------------------------------
    =(Current stock holding)*((Current Stock Rate) - (Average price))
    = (2000) * (14.00 - 12.25)
    = 3500.00 (i.e. Profit)

```

### Notes

<p>

1. In the api part I added to custom ApiResponseWrapper just to show as an example, could have used the normal HTTP status codes  and normal response which is easier. But tried to demonstrate incase we wanted to send some  more customized response from the server. 

2. We can also add Swagger for documentation, Actuator for monitoring, Hazelcast for in memory caching, HATEOS for hypermedia-driven REST service as enhancements. Thought of implementing them but felt like it needs time.
</p>


### Reference Documentation
For further reference, please consider the following sections:


* [Flyway Migration](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#howto-execute-flyway-database-migrations-on-startup)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#using-boot-devtools)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)


