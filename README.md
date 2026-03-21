WorkFlow Lite
📌 Overview
WorkFlow Lite is a backend REST API built with Java and Spring Boot for managing projects.
The project is intentionally scoped to a single domain (Project) and focuses on demonstrating clean architecture, domain-driven design principles, and production-grade backend practices.

🎯 Purpose
This project was built as a learning and portfolio exercise to showcase:
* Clean code structure
* Separation of concerns
* Domain-driven design (DDD) concepts
* Robust validation and business rule enforcement
* Consistent API error handling

🧱 Architecture
The application follows a package-by-feature (modular monolith) structure:
workflow-lite
 project
 * → api            # Controllers (HTTP layer)
 * → application    # Use-case services (business orchestration)
 * → domain         # Core business logic (entities & rules)
 * → repository     # Data access layer
 * → dto            # Request & response models
 * → mapper         # DTO ↔ Entity mapping

 commons
 * → api            # Standard API response models
 * → exception      # Global error handling

🧠 Design Approach
1. Feature-Based Modular Structure
All logic related to a feature is grouped together, improving readability and scalability.

2. Use-Case Oriented Services
Instead of a single service class, operations are split into focused use cases:
* CreateProjectService
* UpdateProjectService
* ChangeProjectStatusService
* GetProjectService

3. Rich Domain Model
The Project entity is not just a data holder — it contains business logic and enforces rules internally.
Example behaviors:
* activate()
* complete()
* updateDetails(...)

4. Clear Separation of Responsibilities
Layer	Responsibility
Controller	Handles HTTP requests and responses
Application	Orchestrates use cases and transactions
Domain	Enforces business rules and state changes
Repository	Handles persistence
⚙️ Core Features
Project Management
* Create projects
* Update project details (supports partial updates)
* Retrieve project information
* Change project status

📏 Business Rules
* Project names must be unique
* Project must have a valid date range (start < end)
* Default status is DRAFT
* Status transitions are controlled:
    * DRAFT → ACTIVE
    * ACTIVE → COMPLETED
    * DRAFT||ACTIVE → CANCELLED
* Completed projects cannot be modified
* Cancelled projects are deleted after a while

❗ Error Handling
The application uses a centralized exception handling mechanism.
All errors follow a consistent structure:
{
  "timestamp": "2026-01-01T12:00:00",
  "status": 400,
  "error": "BUSINESS_RULE_VIOLATION",
  "message": "Project name already exists",
  "path": "/api/projects"
}

🔁 Request Flow
Client Request
   ↓
Controller
   ↓
Application Service (Use Case)
   ↓
Domain (Business Rules)
   ↓
Repository
   ↓
Database

🧪 Testing
The project includes testing strategies for:
* Domain logic (unit tests)
* Application services (mock-based tests)

🚀 Future Improvements
Planned enhancements include:
* Docker containerization
* Redis caching
* Deployment (cloud hosting)
* Domain events for decoupled workflows

🛠️ Tech Stack
* Java
* Spring Boot
* Spring Data JPA
* Hibernate
* PostgreSQL (or compatible database)

📌 Key Takeaway
This project is less about features and more about demonstrating how to build backend systems correctly using clean architecture and domain-driven principles.

👤 Author → Augustine (Gusbom)
A backend-focused software engineer passionate about clean architecture and scalable system design.
