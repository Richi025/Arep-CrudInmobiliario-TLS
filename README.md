# Property Management System with Spring Boot and MySQL and 



This project is a simple property management system implemented using Spring Boot, JPA (Java Persistence API), and MySQL. The system allows users to perform CRUD operations (Create, Read, Update, Delete) on property entities. The properties are stored in a MySQL database, and the application exposes a REST API for interaction.

![alt text](images/portada.gif)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

You need to install the following tools and configure their dependencies:

1. **Java** (version 8 or above)
    
    ```bash
    java -version
    ```

    Should return something like:
    
    ```bash
    java version "1.8.0"
    Java(TM) SE Runtime Environment (build 1.8.0-b132)
    Java HotSpot(TM) 64-Bit Server VM (build 25.0-b70, mixed mode)
    ```

2. **Maven**
    - Download Maven from [here](http://maven.apache.org/download.cgi)
    - Follow the installation instructions [here](https://maven.apache.org/install.html)

    Verify the installation:

    ```bash
    mvn -version
    ```

    Should return something like:

    ```bash
    Apache Maven 3.6.3
    ```

3. **Docker**
    - Install Docker by following the instructions [here](https://docs.docker.com/get-docker/)
    - Verify the installation:
    
    ```bash
    docker --version
    ```

    Should return something like:

    ```bash
    Docker version 20.10.7, build f0df350
    ```

## Installing

1. Clone the repository and navigate into the project directory:
    
    ```bash
    git clone https://github.com/Richi025/Arep-CrudInmobiliario-TLS.git 
    cd PropertyManagement
    ```

2. Build the project:
    
    ```bash
    mvn clean package
    ```

    Should display output similar to:

    ```bash
    [INFO] BUILD SUCCESS
    ```
1. Start the MySQL service using Docker Compose:

    ```bash
    docker-compose up -d
    ```

    This command will start the MySQL container, as defined in the `docker-compose.yml` file.

2. Update your `application.properties` with the following configuration to connect to the MySQL container:

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3000/mydatabase
    spring.datasource.username=user
    spring.datasource.password=secret
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
    ```
3. Run the application:
    
    ```bash
    java -jar target/PropertyApplication-0.0.1-SNAPSHOT.jar
    ```

4. Access the API at: `http://localhost:8080/index.html`

## Test Resutls

To run the tests use:

  ```bash
  mvn test
  ```

![alt text](images/imageTest.png)

### Explanation:

#### PropertyServiceTest:

- Mocks interactions with `PropertyRepository` and verifies that the service methods are being called and return the expected results.
- All the main methods are tested: create, retrieve, update, and delete.

#### PropertyControllerTest:

- Uses `MockMvc` to simulate HTTP requests to the controller endpoints.
- Verifies the HTTP responses, status codes (such as `200 OK`), and the returned JSON.
- The tested methods include create, retrieve all properties, retrieve by ID, update, and delete.


## Deployment in AWS

To run the program on AWS, we need to have two instances, in my case, they are the following.

![images/imageInstances.png](images/imageInstances.png)

In the instance called serverSQL, MySQL and the APACHE web server will be installed, and on the other machine, SPRING BOOT will be deployed. A Let's Encrypt certificate will be generated on each machine to implement the HTTPS protocol.

Two DNS domains are also needed for each EC2 instance in order to generate Let's Encrypt certificates. For this, the following website allows us to generate free domains: https://www.duckdns.org/.

![alt text](images/imageDNS.png)

* **ServerSQL** ---> labserverapache.duckdns.org
* **SeverWebDocker** ---> labserverapacheback.duckdns.org

### **instance ServerSQL**

#### A. Install SQL

1. To install SQL on the EC2 instance, you can refer to the README of the following repository.

    ```bash
    https://github.com/Richi025/Arep-CrudInmobiliario.git
    ```

    If you follow the steps in the repository, you will have SQL installed on the instance.

#### B. Install APACHE 

1. Connect to the EC2 instance.

    First, you must connect to the EC2 instance using SSH. Use the following command:

    ```bash
    ssh -i "ruta/a/tu/clave.pem" ec2-user@tu-direccion-ip

    ssh -i "serverSQL.pem" ec2-user@ec2-44-204-45-23.compute-1.amazonaws.com
    ```

2. Update the packages.
    ```bash
    sudo yum update -y
    ```

3. Install Apache.

    ```bash
    sudo yum install httpd -y
    ```

4. Start and enable Apache.

    Once installed, the Apache service must be started and configured to start automatically when the system boots.
    

    ```bash
    sudo systemctl start httpd
    sudo systemctl enable httpd
    ```


5. Check the status of Apache:


    ```bash
    sudo systemctl status httpd
    ```

#### C. Let's Encrypt certificate

1. Install Dependencies:

    To use Certbot, first, install Python and pip (a package manager for Python):

    ```bash
    sudo yum install python3-pip -y
    sudo pip3 install certbot

    ```
    You also need to install the Apache integration package:

     ```bash
    sudo yum install python-certbot-apache

    ```
2.  Configure Apache

    Edit the configuration file for your virtual host for your domain:

    ```bash
    sudo nano /etc/httpd/conf.d/labserverapache.duckdns.org.conf
    ```

    Make sure you have something like the following in your configuration file:

    ```bash
    <VirtualHost *:80>
        ServerName labserverapache.duckdns.org
        DocumentRoot /var/www/html

        RewriteEngine on
        RewriteCond %{SERVER_NAME} =labserverapache.duckdns.org
        RewriteRule ^ https://%{SERVER_NAME}%{REQUEST_URI} [L,R=301]
    </VirtualHost>
    ```


3. Run Certbot

    Once the Apache configuration file is ready, you can run Certbot to obtain the SSL certificate:

    ```bash
    sudo certbot --apache -v -d labserverapache.duckdns.org
    ```

4. Verification

    Now we check the functionality of the certificate by entering the preferred browser and making a query to the DNS labserverapache.duckdns.org using the HTTPS protocol.

    ![!\[alt text\](image.png)](images/imageSSL.png)


### **instance SeverWebDocker**

#### A. Let's Encrypt certificate

1. Connect to the EC2 instance.

    First, you must connect to the EC2 instance using SSH. Use the following command:

    ```bash
    ssh -i "ruta/a/tu/clave.pem" ec2-user@tu-direccion-ip

    ssh -i "servidorWebDocker.pem" ec2-user@ec2-54-227-44-70.compute-1.amazonaws.com
    ```
2. Install Certbot

    You can do so using the following commands:

    ```bash
    sudo yum update -y
    sudo yum install -y certbot python2-certbot-apache

    ```



3. Obtain a Let's Encrypt Certificate

    ```bash
    sudo certbot certonly --standalone -d your_domain.com

    sudo certbot certonly --standalone -d labserverapacheback.duckdns.org

    ```

    This command will generate the certificate files, usually located in /etc/letsencrypt/live/your_domain.com/:

    + fullchain.pem: This is the certificate chain.
    + privkey.pem: This is the private key

4. Convert to PKCS12 Format

    ```bash
    sudo openssl pkcs12 -export -out /path/to/your/keystore.p12 \
    -in /etc/letsencrypt/live/your_domain.com/fullchain.pem \
    -inkey /etc/letsencrypt/live/your_domain.com/privkey.pem \
    -name "mydomain" -password pass:your_password

    ```

5. Configure Your Spring Boot Application

6. Run the Spring Boot Application

## Diagram Class

![images/imageClass.png](images/imageClass.png)



## Class Diagram Explanation

This diagram represents the structure of the Property Management System and shows the relationships between the main classes in the backend.

### Classes:

1. **Property**:
   - Represents the main entity in the system, a "Property" with attributes such as `id`, `address`, `price`, `size`, and `description`.
   - Methods:
     - `getId()`: Returns the ID of the property.
     - `getAddress()`: Returns the address of the property.
     - `getPrice()`: Returns the price of the property.
     - `getSize()`: Returns the size of the property.
     - `getDescription()`: Returns the description of the property.

2. **PropertyController**:
   - Acts as the REST controller that handles HTTP requests for managing properties.
   - Dependencies:
     - Injects `PropertyService` to delegate business logic.
   - Methods:
     - `createProperty(Property property)`: Creates a new property.
     - `getAllProperties()`: Retrieves all properties.
     - `getPropertyById(Long id)`: Retrieves a specific property by ID.
     - `updateProperty(Long id, Property propertyDetails)`: Updates a property by ID.
     - `deleteProperty(Long id)`: Deletes a property by ID.

3. **PropertyService**:
   - Implements the business logic for the system.
   - Dependencies:
     - Injects `PropertyRepository` to interact with the database.
   - Methods:
     - `createProperty(Property property)`: Creates a new property in the database.
     - `getAllProperties()`: Returns a list of all properties from the database.
     - `getPropertyById(Long id)`: Retrieves a property by its ID.
     - `updateProperty(Long id, Property propertyDetails)`: Updates an existing property with new details.
     - `deleteProperty(Long id)`: Deletes a property from the database.

4. **PropertyRepository**:
   - This is the interface responsible for data persistence and extends `JpaRepository`.
   - Methods:
     - `findByAddress(String address)`: Custom method to search for properties by address.



## Architecture

![images/imgArq.png](images/imgArq.png)

### Diagram Explanation



#### Client Browser:
The browser is the frontend that interacts with the application through static files like `index.html`, `style.css`, and `script.js`. These files send HTTP requests to the backend.

#### ECS Backend Server:
The backend server is deployed on Amazon ECS. It exposes a REST API running on port 8080 and contains the following components:
- **PropertyController**: Handles incoming HTTP requests.
- **PropertyService**: Implements business logic.
- **PropertyRepository**: Manages data persistence operations.
- **Property**: Represents a property entity in the system.

#### ECS MySQL Database:
The MySQL database is deployed on ECS and listens on port 3306. It stores property data.


## Built With

- [Maven](https://maven.apache.org/) - Dependency Management
- [Spring Boot](https://spring.io/projects/spring-boot) - Framework for building microservices
- [Docker](https://www.docker.com/) - Containerization
- [MySQL](https://www.mysql.com/) - Relational Database

## Versioned

We use [Git](https://github.com/) for version control. For available versions, see the tags in this repository.

## Author

* **Jose Ricardo Vasquez Vega** - [Richi025](https://github.com/Richi025)

## Date

October 03, 2024