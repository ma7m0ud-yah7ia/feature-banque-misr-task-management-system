# banque-misr-task-management-system
- The application designed on microservices' architecture.
- The application consists of 5 modules:
    1- banquemisr-challenge05-cloud-config-server
    2- banquemisr-challenge05-discovery-server
    3- banquemisr-challenge05-api-gateway
    4- banquemisr-challenge05-email-service
    5- banquemisr-challenge05-task-management-service 
- All the last modules have one parent "banquemisr-challenge05-task-management-system" 
- I use a Keycloak as OAuth2 security system.

- # Steps to run the application successfully

1- clone the "banquemisr.challenge05-app-config" repository -- git clone https://github.com/ma7m0ud-yah7ia/banquemisr-challenge05-app-config.git
2- customize the application.yml file of "banquemisr-challenge05-cloud-config-server" app [change the port if you need, and local-repository folder path]

 - Install, run mongodb, and add the db configuration on banque-misr-task-management-service-{profile}.properties file that located in "banquemisr.challenge05-app-config".
 - Install, and run postgreSQL database.
 - install, and run the Keycloak from https://www.keycloak.org/downloads as .ZIP file.
 - # steps to set up and run Keycloak:
     - configure the Keycloak to run on PostgreSQL db [path-to-folder/keycloak-25.0.6/conf], edit the properties:
[
# The database vendor.
db=postgres
# The username of the database user.
db-username={postgres --or any other db you want}
# The password of the database user.
db-password={your-password}
# The full database JDBC URL. If not provided, a default URL is set based on the selected database vendor.
db-url={jdbc:postgresql://localhost:5432/keycloack-db -- or add your db-url}
# If the server should expose healthcheck endpoints.
health-enabled=true
# Hostname for the Keycloak server.
hostname={your-hostname}
]
  - run the Keycloak by command [path-to-folder/keycloak-25.0.6/bin>kc.bat start-dev] 
  - go to http://{localhost -- or your hostname}:{8080 -- or your custom port}/ 
  - create the realm, create roles, create "regular.user", "admin.user", and assign the appropriate roles to users. 
  - check the login service "you'll find the service on the collection"

- # steps to set up and run Kafka with Zookeeper:
    - download from https://kafka.apache.org/downloads release 3.8.0
    - define the data directory for Zookeeper on {path-to-kafka}/config/zookeeper.properties [edit the property "dataDir", and "clientPort"]
    - define the data directory for Kafka on {path-to-kafka}/config/producer.properties [edit the property "bootstrap.servers={your hostname}:{port}"]
    - define the "listeners", "zookeeper.connect" properties on server.properties [listeners= PLAINTEXT://{bootstrap.server}, zookeeper.connect={{your hostname}:{clientPort}}]
    - to run the zookeeper => {path-to-kafka}/bin> zookeeper-server-start.bat {path-to-kafka}/config/zookeeper.properties 
    - to run the kafka => {path-to-kafka}/bin> kafka-server-start.bat {path-to-kafka}/config/server.properties

- # steps to set up and run Redis cache system:
    - download Redis from https://sourceforge.net/projects/redis-for-windows.mirror/
    - go to {path-to-redis}\Redis\redis-cli to check the redis db and keys.

# Running the application #
   - depends on the last configurations edit the "*.properties" properties files on "banquemisr.challenge05-app-config".
   - starts the application on the same upper order.