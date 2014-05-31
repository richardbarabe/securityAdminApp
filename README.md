# Security Management Application

##Building
This application uses maven to build a java war project.
On the client side it is using AngularJS and [grunt](http://gruntjs.com/) as a javascript build system.
Since AngularJS components are separated in multiple physical files, [grunt concat](https://github.com/gruntjs/grunt-contrib-concat) is used to concat all of these files into a single one at build time.  At runtime, Jsp files are including this file.

Grunt is launched by maven at the "generate-source" phase.
To build the project, first you'll need to have node.js, since grunt command line tool is to install via npm :
```bash
sudo npm install grunt-cli
```

Grunt and grunt-contrib-concat are already installed in this project (see /node-modules).


##Database
To make this project work, you'll also need mysql with a database named "shiroExample".  You'll find the script in the file cleverly named "database.sql".
Make sure you have a root user with no password, or edit the file /src/main/resource/spring/application-config.xml accordingly (see the "datasource bean" at the end of the file).
