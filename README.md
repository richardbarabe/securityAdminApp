# Security Management Example Application

This is an example of an identity management web application in java/html/javascript.
The project is separated in two parts : the server and the client.
The server expose identity management features through json web services.
It uses Spring framework(context, jdbc, mvc), apache shiro for authentication/authorization and
MySQL for persistence.

## The server
### The Database
First you'll need a mysql server installed.  You'll have to create a database in wich you have to execute the sql in server/database.sql.
The default configuration to access database is "shiroExample" as a database name and a "root" user with no password.  You can change these
settings in server/src/main/resources/spring/application-config.xml (search for the "datasource" bean configuration).

### Building and starting the server
The server uses maven a a build system, with the cargo maven plugin configured.  So to build and start the application, you do : 

```bash
mvn clean package cargo:run
```
This will by default start a jetty web server with the following url as the root : http://localhost:8080/shiroExample

##The client
The Client application is built with HTML5 and JavaScript.  It uses google's AngularJS framework and is built using Grunt.
To install grunt, you'll first need to install node.js.  Then you'll be able to install it like this :

```bash
sudo npm install grunt-cli
```

To build and start the client application, go to the client directory and launch the following command : 

```bash
grunt concat connect
```
The application should now run on : http://localhost:9001

You can loggin in the application using admin:admin




##Database
To make this project work, you'll also need mysql with a database named "shiroExample".  You'll find the script in the file cleverly named "database.sql".
Make sure you have a root user with no password, or edit the file /src/main/resource/spring/application-config.xml accordingly (see the "datasource bean" at the end of the file).
