# Medical Appointment System - Backend

This repository contains the backend service for a medical appointment system, built with Jakarta EE and deployed on GlassFish. The service provides SOAP web services for managing doctors, patients, and medical appointments.

## Overview

The backend component is designed to work with the infrastructure defined in the [P1-Infrastructure repository](https://github.com/Actividades-Arqui-2510/P1-Infrastructure/tree/main?tab=readme-ov-file). It provides:

* SOAP web services for user management (doctors and patients)
* SOAP web services for appointment scheduling and management
* Connection to Oracle database
* Containerized deployment with GlassFish

## Technologies

* **Jakarta EE 10**: Core enterprise framework
* **GlassFish 7.0**: Application server
* **JPA/Hibernate**: Database ORM
* **JAX-WS**: SOAP web services
* **Oracle 21c**: Database
* **Docker**: Containerization
* **Maven**: Build tool

## Project Structure

```
P1-Backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/p1backend/
│   │   │       ├── config/         # Configuration classes
│   │   │       ├── dto/            # Data transfer objects
│   │   │       ├── mapper/         # Object mappers
│   │   │       ├── model/          # JPA entities
│   │   │       ├── repository/     # Data access layer
│   │   │       ├── service/        # Business logic
│   │   │       └── soap/           # SOAP service endpoints
│   │   ├── resources/
│   │   │   └── META-INF/           # Persistence configuration
│   │   └── webapp/                 # Web resources
│   └── test/                       # Unit tests
├── docker/                         # Docker configuration
└── pom.xml                         # Maven dependencies
```

## Database Model

The backend connects to an Oracle database with the following entities:

* **Doctors**: Medical professionals with specialty information
* **Patients**: Patient records with personal information
* **Appointments**: Scheduled appointments linking doctors and patients

## SOAP Web Services

The application exposes two main SOAP web services:

### User Service
* **WSDL**: `/soap/users?wsdl`
* **Operations**:
   * Get all patients/doctors
   * Get patient/doctor by ID or email
   * Patient/Doctor login
   * Create/Update/Delete patient records
   * Create/Update/Delete doctor records

### Appointment Service
* **WSDL**: `/soap/appointments?wsdl`
* **Operations**:
   * Get appointment details
   * Get all appointments
   * Get appointments by doctor/patient
   * Create/Update/Delete appointments

## Development Setup

### Prerequisites
* JDK 17+
* Maven 3.8+
* Oracle 21c database
* GlassFish 7.0 (for local development)
* Docker (for containerized deployment)

### Local Development

1. Clone the repository:
```bash
git clone https://github.com/Actividades-Arqui-2510/P1-Backend.git
cd P1-Backend
```

2. Configure Oracle database:
   * Ensure an Oracle database is running with the schema created
   * Update glassfish-resources.xml with your database details
   * For development, default credentials are:
      * JDBC URL: `jdbc:oracle:thin:@//localhost:1521/MEDPDB`
      * Username: `medadmin`
      * Password: `12345`

3. Build the project:
```bash
mvn clean package
```

4. Deploy to local GlassFish:
   * Copy ROOT.war to your GlassFish domain's `autodeploy` directory
   * Access the application at http://localhost:8080/

## Docker Deployment

The repository includes Docker configuration for containerized deployment:

```bash
# Build the Docker image
docker build -t p1-backend .

# Run the container (standalone)
docker run -p 8080:8080 -p 4848:4848 p1-backend
```

### Environment Variables

The Docker container accepts the following environment variables:

| Variable | Description | Default Value |
|----------|-------------|---------------|
| ORACLE_PDB | Oracle PDB name | MEDPDB |
| ORACLE_PWD | Oracle system password | 12345 |
| DB_PORT | Database port | 1521 |
| DB_USER | Database username | medadmin |
| DB_PASSWORD | Database user password | 12345 |
| DB_HOST | Database hostname | db |

## Integration with P1-Infrastructure

For full system deployment, this backend should be used with the [P1-Infrastructure repository](https://github.com/Actividades-Arqui-2510/P1-Infrastructure/tree/main?tab=readme-ov-file), which provides:

* Oracle database setup with schema creation
* Container orchestration with Docker Compose
* Environment configuration
* Health monitoring

Refer to the P1-Infrastructure README for complete system deployment instructions.
