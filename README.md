[![Build Status](https://travis-ci.org/bwolf/quotes.png?branch=master)](https://travis-ci.org/bwolf/quotes)

# Running the beast
1. Clone the repository `git clone https://github.com/bwolf/quotes/`
2. Build `mvn package`
3. Run the database server `mvn exec:java`
4. In new Terminal run application server `mvn tomcat7:run-war`
5. Browse to http://localhost:8080/quotes/

# IDE Setup

## Setup for IntelliJ
1. Open project
2. TODO

## Setup for Eclipse Kepler 4.3
1. Download Eclipse IDE for Java EE Developers (i.e. Kepler). It comes with maven support which is used indirectly here.
2. Start Eclipse
3. Install GWT Plugin for Eclipse. Browse to [GWT Website](http://www.gwtproject.org/download.html) and follow the link to install SDKs and Plugins, to determine the proper link matching the installed Eclipse version (i.e. 4.3 Kepler). Copy this link.
4. In Eclipse Help > Install New Software > Add (Name: gwt, Location: "installation URL") > OK. Select 'Google Plugin for Eclipse', follow installation.
5. In terminal 'mvn clean package' followed by 'mvn eclipse:eclipse'.
6. In Eclipse > Import > General > Existing Projects into Workspace by selecting project base directory.
7. Download [Tomcat 7](http://tomcat.apache.org).
8. In Eclipse > Servers > Create new > Apache > Tomcat 7 > Name it according to server version and project name (e.g. 'Tomcat7 myproject'), chose installation directory and finish.
9. In Eclipse > Project Explorer > Servers > 'myserver', edit 'server.xml':

        <Context path="/quotes"
          reloadable="true"
          docBase="project-basedir-here/target/artifact-name-1.0-SNAPSHOT">
        </Context>

10. Select Run > Run Configurations… > Maven Build > Create New > Name: "HSQLDB", Browse Workspace "select eclipse basedir", Goal: "exec:java", click run.
11. Select Data Source Explorer > Database Connections > New… HSQLDB
12. Click next and chose Driver from ~/.m2/…
13. Select driver settings such as '''jdbc:hsqldb:hsql://localhost/quotes'''
14. Click OK
15. Select file from workspace: "src/test/resources/schema-hsqldb.sql", followed by "src/test/resources/data.sql".
16. Chose Run SQL Files via right click one by one.
17. Select Servers > Tomcat v7 > Start server in debug mode
18. Browse to http://localhost:8080/quotes and login.
19. Note that registering a new user will not work, because a mail server is required for this to work.
20. In Eclipse > Preferences > Google > Web Toolkit > set gwt SDK path, download SDK and unzip.
21. In Eclipse > Project > Properties > Google > Web Application > 'This project has a WAR directory': 'src/main/webapp'.
22. In Eclipse > Project > Properties > Google > Web Toolkit: 'Use Google Web Toolkit'.
22. In Eclipse > Debug Configurations… > Web Application > Select new, name it 'DevMode', main class 'com.google.gwt.dev.DevMode', don't use built-in server, URL http://localhost:8080/quotes/some.html
23. To launch: Run HSQLDB, Debug Tomcat 7, Debug DevMode.

## DB Access from database browser or external tool:
With running HSQLDB server use the URL:

        jdbc:hsqldb:hsql://localhost/quotes

## Dev Mode configuration in IntelliJ:
1. Module to load: org.spqrinfo.first.first
2. Dev mode parameters: -noserver -logLevel INFO -codeServerPort 9997
3. Start page: http://localhost:8080/some.html
4. Open in browser: Firefox

EOF
