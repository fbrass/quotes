[![Build Status](https://travis-ci.org/bwolf/quotes.png?branch=master)](https://travis-ci.org/bwolf/quotes)

# Running the beast
1. Clone the repository `git clone https://github.com/bwolf/quotes/`
2. Build it: `mvn package`
3. Run the database server: `mvn exec:java`
4. Run the application server in a new terminal window: `mvn tomcat7:run-war`
5. Browse to [http://localhost:8080/quotes/](http://localhost:8080/quotes/)

# IDE Setup

## Setup for IntelliJ
1. Open project
2. Have fun

### DevMode configuration
1. Module to load: *All*
2. VM options: `-Xmx512m`
3. Dev mode parameters: `-noserver -logLevel INFO -codeServerPort 9997`
4. Start page: http://localhost:8080/quotes/home
5. Open in browser: *Firefox*
6. Check: *Single instance only*

## Setup for Eclipse Kepler 4.3
1. Download Eclipse IDE for Java EE Developers (i.e. Kepler). It comes with maven support which is used indirectly here.
1. Start Eclipse
1. Install GWT Plugin for Eclipse. Browse to [GWT Website](http://www.gwtproject.org/download.html) and follow the link to install SDKs and Plugins, to determine the proper link matching the installed Eclipse version (i.e. 4.3 Kepler). Copy this link.
1. In Eclipse Help > Install New Software > Add (Name: gwt, Location: "installation URL") > OK. Select 'Google Plugin for Eclipse', follow installation.
1. In terminal 'mvn clean package' followed by 'mvn eclipse:eclipse'.
1. In Eclipse > Import > General > Existing Projects into Workspace by selecting project base directory.
1. Download [Tomcat 7](http://tomcat.apache.org).
1. In Eclipse > Servers > Create new > Apache > Tomcat 7 > Name it according to server version and project name (e.g. 'Tomcat7 myproject'), chose installation directory and finish.
9. In Eclipse > Project Explorer > Servers > 'myserver', edit 'server.xml':

        <Context path="/quotes"
          reloadable="true"
          docBase="project-basedir-here/target/artifact-name-1.0-SNAPSHOT">
        </Context>

1. Select Run > Run Configurations… > Maven Build > Create New > Name: "HSQLDB", Browse Workspace "select eclipse basedir", Goal: "exec:java", click run.
1. Select Data Source Explorer > Database Connections > New… HSQLDB
1. Click next and chose Driver from ~/.m2/…
1. Select driver settings such as `jdbc:hsqldb:hsql://localhost/quotes`
1. Click OK
1. Select file from workspace: `src/test/resources/schema-hsqldb.sql`, followed by `src/test/resources/data.sql`.
1. Chose Run SQL Files via right click one by one.
1. Select Servers > Tomcat v7 > Start server in debug mode, if an error occurs perform a `mvn clean package`.
1. Browse to [http://localhost:8080/quotes](http://localhost:8080/quotes) and login.
1. Note that registering a new user will require the use of a mail server: see the section _Mail based registration_ on how to do this.
1. In Eclipse > Preferences > Google > Web Toolkit > set gwt SDK path, download SDK and unzip.
1. In Eclipse > Project > Properties > Google > Web Application > 'This project has a WAR directory': 'src/main/webapp'.
1. In Eclipse > Project > Properties > Google > Web Toolkit: 'Use Google Web Toolkit'.
1. In Eclipse > Debug Configurations… > Web Application > Select new, name it 'DevMode', main class 'com.google.gwt.dev.DevMode', don't use built-in server, URL http://localhost:8080/quotes/some.html
1. To launch: Run HSQLDB, Debug Tomcat 7, Debug DevMode.
1. If an error occurs while running Tomcat or DevMode, perform a `mvn clean package`.

## DB Access from database browser or external tool:
With running HSQLDB server use the URL:

        jdbc:hsqldb:hsql://localhost/quotes

## Mail based registration
Clone the the project [https://github.com/bwolf/smtpconsole.git](https://github.com/bwolf/smtpconsole.git), run it and watch the console for incoming messages.

        git clone https://github.com/bwolf/smtpconsole.git
        cd smtpconsole
        mvn package exec:java

EOF