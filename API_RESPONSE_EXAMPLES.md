# API Response Examples - Real-World Scenarios

## Table of Contents
1. [Success Responses](#success-responses)
2. [Validation Errors](#validation-errors)
3. [Authentication Errors](#authentication-errors)
4. [Authorization Errors](#authorization-errors)
5. [Resource Not Found](#resource-not-found)
6. [Conflict/Duplicate Errors](#conflictduplicate-errors)
7. [Server Errors](#server-errors)

---

## Success Responses

### Case 1: GET Single Resource - 200 OK

**Request:**
```http
GET /flight/1 HTTP/1.1
Host: api.airline.com
```

**Response:**
```json
HTTP/1.1 200 OK
Content-Type: application/json

{
  "success": true,
  "status": 200,
  "message": "Flight retrieved successfully",
  "data": {
    "id": 1,
    "flightNumber": "AI101",
    "departure": "2026-03-01T10:00:00Z",
    "arrival": "2026-03-01T12:30:00Z",
    "aircraft": {
      "id": 1,
      "model": "Boeing 737",
      "capacity": 189
    }
  },
  "errors": null,
  "path": "/flight/1",
  "traceId": null,
  "timestamp": "2026-02-23T23:35:00Z"
}
```

---

### Case 2: GET Multiple Resources - 200 OK

**Request:**
```http
GET /flight?page=0&size=10 HTTP/1.1
Host: api.airline.com
```

**Response:**
```json
HTTP/1.1 200 OK
Content-Type: application/json

{
  "success": true,
  "status": 200,
  "message": "Flights retrieved successfully",
  "data": [
    {
      "id": 1,
      "flightNumber": "AI101",
      "departure": "2026-03-01T10:00:00Z",
      "arrival": "2026-03-01T12:30:00Z"
    },
    {
      "id": 2,
      "flightNumber": "AI102",
      "departure": "2026-03-01T14:00:00Z",
      "arrival": "2026-03-01T16:30:00Z"
    }
  ],
  "errors": null,
  "path": "/flight",
  "traceId": null,
  "timestamp": "2026-02-23T23:35:00Z"
}
```

---

### Case 3: POST Create Resource - 201 Created

**Request:**
```http
POST /flight HTTP/1.1
Host: api.airline.com
Content-Type: application/json

{
  "flightNumber": "AI103",
  "departure": "2026-03-02T08:00:00Z",
  "arrival": "2026-03-02T10:30:00Z",
  "aircraftId": 1
}
```

**Response:**
```json
HTTP/1.1 201 Created
Content-Type: application/json
Location: /flight/3

{
  "success": true,
  "status": 201,
  "message": "Flight created successfully",
  "data": {
    "id": 3,
    "flightNumber": "AI103",
    "departure": "2026-03-02T08:00:00Z",
    "arrival": "2026-03-02T10:30:00Z",
    "aircraft": {
      "id": 1,
      "model": "Boeing 737"
    }
  },
  "errors": null,
  "path": "/flight",
  "traceId": null,
  "timestamp": "2026-02-23T23:35:00Z"
}
```

---

### Case 4: PUT Update Resource - 200 OK

**Request:**
```http
PUT /flight/1 HTTP/1.1
Host: api.airline.com
Content-Type: application/json

{
  "flightNumber": "AI101",
  "departure": "2026-03-01T10:00:00Z",
  "arrival": "2026-03-01T13:00:00Z",
  "aircraftId": 2
}
```

**Response:**
```json
HTTP/1.1 200 OK
Content-Type: application/json

{
  "success": true,
  "status": 200,
  "message": "Flight updated successfully",
  "data": {
    "id": 1,
    "flightNumber": "AI101",
    "departure": "2026-03-01T10:00:00Z",
    "arrival": "2026-03-01T13:00:00Z",
    "aircraft": {
      "id": 2,
      "model": "Airbus A320"
    }
  },
  "errors": null,
  "path": "/flight/1",
  "traceId": null,
  "timestamp": "2026-02-23T23:35:00Z"
}
```

---

### Case 5: DELETE Resource - 204 No Content

**Request:**
```http
DELETE /flight/1 HTTP/1.1
Host: api.airline.com
```

**Response:**
```
HTTP/1.1 204 No Content
Content-Length: 0

(Empty body - no content to return)
```

---

## Validation Errors

### Case 1: Missing Required Fields - 400 Bad Request

**Request:**
```http
POST /flight HTTP/1.1
Host: api.airline.com
Content-Type: application/json

{
  "departure": "2026-03-02T08:00:00Z"
}
```

**Response:**
```json
HTTP/1.1 400 Bad Request
Content-Type: application/json

{
  "success": false,
  "status": 400,
  "message": "Validation failed",
  "data": null,
  "errors": [
    {
      "field": "flightNumber",
      "code": "NotBlank",
      "message": "Flight number cannot be blank"
    },
    {
      "field": "arrival",
      "code": "NotNull",
      "message": "Arrival time is required"
    },
    {
      "field": "aircraftId",
      "code": "NotNull",
      "message": "Aircraft ID is required"
    }
  ],
  "path": "/flight",
  "traceId": "550e8400-e29b-41d4-a716-446655440000",
  "timestamp": "2026-02-23T23:35:00Z"
}
```

---

### Case 2: Invalid Data Types - 400 Bad Request

**Request:**
```http
POST /flight HTTP/1.1
Host: api.airline.com
Content-Type: application/json

{
  "flightNumber": "AI104",
  "departure": "invalid-date",
  "aircraftId": "not-a-number"
}
```

**Response:**
```json
HTTP/1.1 400 Bad Request
Content-Type: application/json

{
  "success": false,
  "status": 400,
  "message": "Validation failed",
  "data": null,
  "errors": [
    {
      "field": "departure",
      "code": "DateTimeParseException",
      "message": "Invalid ISO-8601 date format"
    },
    {
      "field": "aircraftId",
      "code": "NumberFormatException",
      "message": "Invalid number format"
    }
  ],
  "path": "/flight",
  "traceId": "550e8400-e29b-41d4-a716-446655440001",
  "timestamp": "2026-02-23T23:35:00Z"
}
```

---

### Case 3: Business Logic Validation - 400 Bad Request

**Request:**
```http
POST /flight HTTP/1.1
Host: api.airline.com
Content-Type: application/json

{
  "flightNumber": "AI105",
  "departure": "2026-03-01T13:00:00Z",
  "arrival": "2026-03-01T10:00:00Z",
  "aircraftId": 1
}
```

**Response:**
```json
HTTP/1.1 400 Bad Request
Content-Type: application/json

{
  "success": false,
  "status": 400,
  "message": "Invalid argument",
  "data": null,
  "errors": [
    {
      "field": "arrival",
      "code": "INVALID_TIME_RANGE",
      "message": "Arrival time must be after departure time"
    }
  ],
  "path": "/flight",
  "traceId": "550e8400-e29b-41d4-a716-446655440002",
  "timestamp": "2026-02-23T23:35:00Z"
}
```

---

## Authentication Errors

### Case: Login with Invalid Credentials - 401 Unauthorized

**Request:**
```http
POST /api/auth/login HTTP/1.1
Host: api.airline.com
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "wrongpassword"
}
```

**Response:**
```json
HTTP/1.1 401 Unauthorized
Content-Type: application/json

{
  "success": false,
  "status": 401,
  "message": "Invalid email or password",
  "data": null,
  "errors": [
    {
      "code": "UNAUTHORIZED",
      "message": "Invalid email or password"
    }
  ],
  "path": "/api/auth/login",
  "traceId": "550e8400-e29b-41d4-a716-446655440003",
  "timestamp": "2026-02-23T23:35:00Z"
}
```

---

## Authorization Errors

### Case: Insufficient Permissions - 403 Forbidden

**Request:**
```http
DELETE /flight/1 HTTP/1.1
Host: api.airline.com
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Response:**
```json
HTTP/1.1 403 Forbidden
Content-Type: application/json

{
  "success": false,
  "status": 403,
  "message": "You don't have permission to delete flights",
  "data": null,
  "errors": [
    {
      "code": "FORBIDDEN",
      "message": "You don't have permission to delete flights"
    }
  ],
  "path": "/flight/1",
  "traceId": "550e8400-e29b-41d4-a716-446655440004",
  "timestamp": "2026-02-23T23:35:00Z"
}
```

---

## Resource Not Found

### Case 1: GET Non-existent Resource - 404 Not Found

**Request:**
```http
GET /flight/99999 HTTP/1.1
Host: api.airline.com
```

**Response:**
```json
HTTP/1.1 404 Not Found
Content-Type: application/json

{
  "success": false,
  "status": 404,
  "message": "Resource not found",
  "data": null,
  "errors": [
    {
      "code": "NOT_FOUND",
      "message": "Flight with id '99999' not found"
    }
  ],
  "path": "/flight/99999",
  "traceId": "550e8400-e29b-41d4-a716-446655440005",
  "timestamp": "2026-02-23T23:35:00Z"
}
```

---

### Case 2: UPDATE Non-existent Resource - 404 Not Found

**Request:**
```http
PUT /flight/99999 HTTP/1.1
Host: api.airline.com
Content-Type: application/json

{
  "flightNumber": "AI999",
  "departure": "2026-03-01T10:00:00Z",
  "arrival": "2026-03-01T13:00:00Z"
}
```

**Response:**
```json
HTTP/1.1 404 Not Found
Content-Type: application/json

{
  "success": false,
  "status": 404,
  "message": "Resource not found",
  "data": null,
  "errors": [
    {
      "code": "NOT_FOUND",
      "message": "Flight with id '99999' not found"
    }
  ],
  "path": "/flight/99999",
  "traceId": "550e8400-e29b-41d4-a716-446655440006",
  "timestamp": "2026-02-23T23:35:00Z"
}
```

---

## Conflict/Duplicate Errors

### Case: Duplicate Flight Number - 409 Conflict

**Request:**
```http
POST /flight HTTP/1.1
Host: api.airline.com
Content-Type: application/json

{
  "flightNumber": "AI101",
  "departure": "2026-03-02T08:00:00Z",
  "arrival": "2026-03-02T10:30:00Z",
  "aircraftId": 1
}
```

**Response:**
```json
HTTP/1.1 409 Conflict
Content-Type: application/json

{
  "success": false,
  "status": 409,
  "message": "Data conflict: duplicate or constraint violation",
  "data": null,
  "errors": [
    {
      "code": "CONSTRAINT_VIOLATION",
      "message": "Flight with this number already exists"
    }
  ],
  "path": "/flight",
  "traceId": "550e8400-e29b-41d4-a716-446655440007",
  "timestamp": "2026-02-23T23:35:00Z"
}
```

---

## Server Errors

### Case 1: Unexpected Runtime Error - 500 Internal Server Error

**Scenario:** Database connection error, null pointer, etc. NOT EXPOSED to client

**Response:**
```json
HTTP/1.1 500 Internal Server Error
Content-Type: application/json

{
  "success": false,
  "status": 500,
  "message": "Internal server error",
  "data": null,
  "errors": [
    {
      "code": "INTERNAL_SERVER_ERROR",
      "message": "An unexpected error occurred. Please try again later."
    }
  ],
  "path": "/flight",
  "traceId": "550e8400-e29b-41d4-a716-446655440008",
  "timestamp": "2026-02-23T23:35:00Z"
}
```

**Server Logs (Not shown to client):**
```
[ERROR] TraceID: 550e8400-e29b-41d4-a716-446655440008
java.lang.NullPointerException: Aircraft not found
  at com.airline.airline.service.FlightService.createFlight(FlightService.java:45)
  at com.airline.airline.controller.FlightController.createFlight(FlightController.java:39)
  ...
```

**To Debug:** Use the `traceId` to find the actual error in logs.

---

### Case 2: Service Unavailable - 503 Service Unavailable

**Scenario:** Database down, external service unavailable

**Response:**
```json
HTTP/1.1 500 Internal Server Error
Content-Type: application/json

{
  "success": false,
  "status": 500,
  "message": "Internal server error",
  "data": null,
  "errors": [
    {
      "code": "INTERNAL_SERVER_ERROR",
      "message": "An unexpected error occurred. Please try again later."
    }
  ],
  "path": "/flight",
  "traceId": "550e8400-e29b-41d4-a716-446655440009",
  "timestamp": "2026-02-23T23:35:00Z"
}
```

---

## Response Headers

All responses include standard HTTP headers:

```http
HTTP/1.1 200 OK
Content-Type: application/json; charset=UTF-8
Content-Length: 584
Date: Mon, 23 Feb 2026 23:35:00 GMT
Server: Apache-Coyote/1.1
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, must-revalidate
Pragma: no-cache
Expires: 0
```

---

## HTTP Status Code Summary

| Code | Meaning | When to Use |
|------|---------|------------|
| **200** | OK | Standard successful GET/PUT/PATCH |
| **201** | Created | Successful POST (resource created) |
| **204** | No Content | Successful DELETE or void response |
| **400** | Bad Request | Validation errors, missing fields |
| **401** | Unauthorized | Authentication failure (wrong password) |
| **403** | Forbidden | Authorization failure (insufficient permission) |
| **404** | Not Found | Resource doesn't exist |
| **409** | Conflict | Duplicate data, constraint violations |
| **500** | Server Error | Unexpected application errors |

---

## Client-Side Error Handling Pattern

### JavaScript/TypeScript Example

```javascript
async function fetchFlight(id) {
  try {
    const response = await fetch(`/flight/${id}`);
    const data = await response.json();
    
    if (!response.ok) {
      // Handle error
      if (response.status === 404) {
        console.error(`Flight not found: ${data.message}`);
        showNotFound();
      } else if (response.status === 401) {
        console.error('Please log in');
        redirectToLogin();
      } else if (response.status === 400) {
        // Show validation errors
        data.errors.forEach(error => {
          console.error(`${error.field}: ${error.message}`);
        });
      } else {
        console.error(`Error ${response.status}: ${data.message}`);
        showErrorModal(data.traceId);  // Show traceId for support
      }
      return null;
    }
    
    // Success
    console.log(data.message);
    return data.data;
    
  } catch (error) {
    console.error('Network error:', error);
    showNetworkError();
  }
}
```

---

## Testing Response Status Codes

### cURL Commands

```bash
# 200 OK
curl -w "\n%{http_code}\n" -X GET http://localhost:8080/flight/1

# 201 Created
curl -w "\n%{http_code}\n" -X POST http://localhost:8080/flight \
  -H "Content-Type: application/json" \
  -d '{"flightNumber":"AI101"}'

# 204 No Content
curl -w "\n%{http_code}\n" -X DELETE http://localhost:8080/flight/1

# 400 Bad Request
curl -w "\n%{http_code}\n" -X POST http://localhost:8080/flight \
  -H "Content-Type: application/json" \
  -d '{}'

# 404 Not Found
curl -w "\n%{http_code}\n" -X GET http://localhost:8080/flight/99999

# 500 Server Error
curl -w "\n%{http_code}\n" -X GET http://localhost:8080/flight/break
```

---

*Last Updated: February 23, 2026*
