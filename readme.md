# Data Processor Application

This is a Maven-based multi-module project that contains a back-end application, a CLI client, an npm based React TypeScript front-end application 

## Prerequisites

- Java 17
- Latest Docker (for GUI build as it is a multi-stage docker build)
- Latest Maven (optional because of wrapper)
- Node.js Hydrogen version (for development)
- Recommended IDE: IntelliJ

## Building the Project

You can build the project by running the following command in the root project:

`mvn clean package -DskipTests`

Or you can also use the Maven wrapper:
- For Unix-based systems: `./mvnw clean package -DskipTests`
- For Windows: `mvnw.cmd clean package -DskipTests`

This will create two Docker images:

- `data-processor-app:latest`
- `data-processor-gui:latest`

And in the `data-processor-cli/target` library will be a `data-processor-cli-{ProjectVersion}.zip` file which contains the CLI application.

## Running the Application

After a successful build, you can start the whole stack (database, back-end, front-end) using Docker Compose.

In the `/misc` directory, there is a `docker-compose.yml` file. Navigate to this directory and run:

`docker-compose up -d`

This will start all the necessary services. Once the services are up and running, you can start using the CLI.

## Development

For development, Java, Docker, Maven (optional), and Node.js Hydrogen version are required.

The project contains `.run` directory with `*.run.xml` files that can be processed by the IDE, and you can start the application (frond-end, back-end, and CLI) from the IDE with proper parameters.

There is an `application-developer.properties` file in the `/data-processor-app/src/main/resources/` directory with H2 as the default database configured, and also an example for PostgreSQL.
