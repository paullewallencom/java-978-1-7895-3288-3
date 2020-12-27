# Section 1: Getting Started with Java EE 8

This is the demo source code for the first section if the Packt Course `Building Web Services with java EE 8`.
The Git repository contains several branches, each resembling the next step in the development of a simple
Java EE 8 powered web service.

* **master**: The final and working solution
* **step-00**: The skeleton Maven project
* **step-01**: Added Java EE 8 dependency to `pom.xml`
* **step-02**: Added `bean.xml` file
* **step-03**: Implemented JAX-RS configuration and REST resource
* **step-04**: Added a Dockerfile

## Video 1.4: Getting Started

In this video we will develop a basic Hello World web service using Java EE 8. The rough
Maven skeleton project is contained in branch `step-00`.

### Step 1

Next we need to add the Java EE 8 API dependency to our Maven POM (see branch `step-01`).
Add the following snippet to the `pom.xml``file:

```xml
<dependency>
    <groupId>javax</groupId>
    <artifactId>javaee-api</artifactId>
    <version>8.0</version>
    <scope>provided</scope>
</dependency>
```

### Step 2

In the next step we will add a `beans.xml` file to the `src/main/webapp/WEB-INF` directory
of the project. This is so that our classes are recognized as CDI beans.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://xmlns.jcp.org/xml/ns/javaee"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
                      http://xmlns.jcp.org/xml/ns/javaee/beans_1_1.xsd"
       bean-discovery-mode="all">
</beans>
```

### Step 3

In the final step we will add the JAX-RS configuration class as well as a simple
REST resource class.

To start, add a class that extends `javax.ws.rs.core.Application` and specify the
base path of the REST API using the `@ApplicationPath` annnotation.

```java
@ApplicationPath("api")
public class JAXRSConfiguration extends Application {
}
```

Then implement a basic REST resource class like the following. Annotate the class
with `@Path` and specify the path for this resource. Add a resource method for `@GET`
and implement the body to return a response.

```java
@Path("hello")
public class HelloWorldResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response helloWorld() {
        Map<String, String> response = singletonMap("message", "Building Web Services with Java EE 8.");
        return Response.ok(response).build();
    }
}
```

Now you can build the project and deploy the final artifact to your Payara Server.

```bash
$ mvn clean package
```

## Video 1.5: Containerization

To containerize our microservice, we have to write a `Dockerfile`. See the `step-04`
branch for the final result.

First, we need to decide which container base image to use. Have a look at Docker Hub.
The two options presented here are:

* Payara Server Full 5.0
* Payara Micro 5.0

Add a `Dockerfile` to the base directory of this project and add the following content:

```
FROM payara/server-full:5-SNAPSHOT
COPY target/hello-javaee8.war $DEPLOY_DIR
```

To build the image and run it as a container, open a command prompt and issue the following
shell commands:
```bash
$ mvn package

$ docker build -t hello-javaee8:1.0-full .
$ docker run -it -p 8080:8080 hello-javaee8:1.0-full
```

You should now be able to access the web service under the IP of you Docker host.

Next, we want to use the Payara Micro base image instead. Add the following content to the `Dockerfile`:
```
FROM payara/micro:5-SNAPSHOT
COPY target/hello-javaee8.war /opt/payara/deployments
```

To build the image and run it as a container, open a command prompt and issue the following
shell commands:
```bash
$ mvn package

$ docker build -t hello-javaee8:1.0-micro .
$ docker run -it -p 8080:8080 hello-javaee8:1.0-micro
```

Again, you should now be able to access the web service under the IP of you Docker host.
