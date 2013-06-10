picketlink-authentication-google: PicketLink Authentication with Google+ login
===============================
Author: Marek Posolda
Technologies: CDI, PicketLink
Summary: Basic example that demonstrates google+ authentication using PicketLink
Target Product: EAP
Source: <https://github.com/jboss-jdf/jboss-as-quickstart/>

What is it?
-----------

This example demonstrates the use of *CDI 1.0* and *PicketLink* in *JBoss Enterprise Application Platform 6* or *JBoss AS 7*.

System requirements
-------------------

All you need to build this project is Java 6.0 (Java SDK 1.6) or better, Maven 3.0 or better.

The application this project produces is designed to be run on JBoss Enterprise Application Platform 6 or JBoss AS 7.


Configure Maven
---------------

If you have not yet done so, you must [Configure Maven](../README.md#mavenconfiguration) before testing the quickstarts.


Obtain Google Application ClientID, ClientSecret
--------------------------------------------------
You will have to log in to Google Developer (http://developers.google.com/) account and register an application in API console <https://code.google.com/apis/console/>.
Then you will be provided a ClientID and ClientSecret. You also need to provide your returnURL to redirect after finish of OAuth flow.
In tab "services", you also need to enable "Google+ API" .

Configure JBoss Enterprise Application Platform 6 or JBoss AS 7
---------------------------------------------------------------
You will need to open file JBOSS_HOME/bin/standalone.conf and attach some system properties to JAVA_OPTS variable.
At least you will need to add those system properties:
GOOGLE_CLIENT_ID  - ClientID of your App
GOOGLE_CLIENT_SECRET - Client secret of your App
GOOGLE_RETURN_URL - ReturnURL of your app. This is URL where your app is running. You need to configure this URL also in Google API console as mentioned in previous
paragraph

Example of line to attach to the end of file standalone.conf:

JAVA_OPTS="$JAVA_OPTS -DGOOGLE_CLIENT_ID=1154897-jcsr9lqpgkokpwuavrp0gal4879to9dtg.apps.googleusercontent.com -DGOOGLE_CLIENT_SECRET=C44fKL1gqRS487fZ1WeJZfPa -DGOOGLE_RETURN_URL=http://localhost:8080/jboss-as-picketlink-authentication-google"

Start JBoss Enterprise Application Platform 6 or JBoss AS 7 with the Web Profile
-------------------------

1. Open a command line and navigate to the root of the JBoss server directory.
2. The following shows the command line to start the server with the web profile:

        For Linux:   JBOSS_HOME/bin/standalone.sh
        For Windows: JBOSS_HOME\bin\standalone.bat


Build and Deploy the Quickstart
-------------------------

_NOTE: The following build command assumes you have configured your Maven user settings. If you have not, you must include Maven setting arguments on the command line. See [Build and Deploy the Quickstarts](../README.md#buildanddeploy) for complete instructions and additional options._

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. Type this command to build and deploy the archive:

        mvn clean package jboss-as:deploy

4. This will deploy `target/jboss-as-picketlink-authentication-google.war` to the running instance of the server.


Access the application
---------------------

The application will be running at the following URL: <http://localhost:8080/jboss-as-picketlink-authentication-google/>.


Undeploy the Archive
--------------------

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. When you are finished testing, type this command to undeploy the archive:

        mvn jboss-as:undeploy


Run the Quickstart in JBoss Developer Studio or Eclipse
-------------------------------------
You can also start the server and deploy the quickstarts from Eclipse using JBoss tools. For more information, see [Use JBoss Developer Studio or Eclipse to Run the Quickstarts](../README.md#useeclipse)


Debug the Application
------------------------------------

If you want to debug the source code or look at the Javadocs of any library in the project, run either of the following commands to pull them into your local repository. The IDE should then detect them.

        mvn dependency:sources
        mvn dependency:resolve -Dclassifier=javadoc