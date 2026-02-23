# API Response Implementation Cheat Sheet

## Quick Commands

### ResponseUtil Methods at a Glance

```java
ResponseUtil.ok(data, msg)                          // 200 OK
ResponseUtil.created(data, msg)                     // 201 Created
ResponseUtil.noContent(msg)                         // 204 No Content
ResponseUtil.badRequest(msg)                        // 400 Bad Request
ResponseUtil.badRequest(msg, errorList)             // 400 with details
ResponseUtil.unauthorized(msg)                      // 401 Unauthorized
ResponseUtil.forbidden(msg)                         // 403 Forbidden
ResponseUtil.notFound(msg)                          // 404 Not Found
ResponseUtil.conflict(msg)                          // 409 Conflict
ResponseUtil.internalServerError(msg)               // 500 Server Error
ResponseUtil.error(HttpStatus, msg)                 // Custom status
ResponseUtil.success(data, msg)                     // 200 (legacy)
ResponseUtil.error(msg)                             // 400 (legacy)
```

---

## Controller Patterns

### Pattern: Standard GET

```java
@GetMapping("/{id}")
public ResponseEntity<ApiResponse<Entity>> getById(@PathVariable Long id) {
    Entity entity = service.getById(id);
    if (entity == null) throw EntityNotFoundException.forId("Entity", id);
    return ResponseUtil.ok(entity, "Entity retrieved successfully");
}
```

### Pattern: Standard POST

```java
@PostMapping
public ResponseEntity<ApiResponse<Entity>> create(@Valid @RequestBody EntityRequest req) {
    Entity created = service.create(req);
    return ResponseUtil.created(created, "Entity created successfully");
}
```

### Pattern: Standard PUT

```java
@PutMapping("/{id}")
public ResponseEntity<ApiResponse<Entity>> update(
        @PathVariable Long id, 
        @Valid @RequestBody EntityRequest req) {
    Entity updated = service.update(id, req);
    if (updated == null) throw EntityNotFoundException.forId("Entity", id);
    return ResponseUtil.ok(updated, "Entity updated successfully");
}
```

### Pattern: Standard DELETE

```java
@DeleteMapping("/{id}")
public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseUtil.noContent("Entity deleted successfully");
}
```

### Pattern: GET All

```java
@GetMapping
public ResponseEntity<ApiResponse<List<Entity>>> getAll() {
    List<Entity> entities = service.getAll();
    return ResponseUtil.ok(entities, "Entities retrieved successfully");
}
```

---

## Error Handling Patterns

### Pattern: 404 Not Found

```java
Entity entity = service.getById(id);
if (entity == null) {
    throw EntityNotFoundException.forId("Flight", id);
}
return ResponseUtil.ok(entity, "Retrieved");
```

### Pattern: 400 Bad Request (Manual)

```java
if (request.getDeparture().isBefore(Instant.now())) {
    throw new IllegalArgumentException("Departure cannot be in the past");
}
```

### Pattern: 400 Bad Request (Validation Errors)

```java
List<ErrorDetail> errors = List.of(
    ErrorDetail.ofField("email", "Email already exists"),
    ErrorDetail.ofField("phone", "Invalid phone format")
);
return ResponseUtil.badRequest("Validation failed", errors);
```

### Pattern: 409 Conflict

```java
if (existingFlight != null) {
    return ResponseUtil.conflict("Flight with this number already exists");
}
```

### Pattern: 401 Unauthorized

```java
if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
    return ResponseUtil.unauthorized("Invalid credentials");
}
```

### Pattern: 403 Forbidden

```java
if (!user.isAdmin()) {
    return ResponseUtil.forbidden("You need admin permission");
}
```

---

## Exception Handling Auto-Conversion

These exceptions are automatically caught and converted by `GlobalExceptionHandler`:

| Exception | Status | Code | Handling |
|-----------|--------|------|----------|
| `EntityNotFoundException` | 404 | NOT_FOUND | Custom exception |
| `MethodArgumentNotValidException` | 400 | VALIDATION_ERROR | Spring validation |
| `IllegalArgumentException` | 400 | INVALID_ARGUMENT | Business validation |
| `DataIntegrityViolationException` | 409 | CONSTRAINT_VIOLATION | Database constraint |
| `Exception` (any) | 500 | INTERNAL_SERVER_ERROR | Catch-all |

**Example:**
```java
@GetMapping("/{id}")
public ResponseEntity<ApiResponse<Flight>> getFlight(@PathVariable Long id) {
    Flight flight = service.getFlight(id);  // Returns null if not found
    if (flight == null) {
        throw EntityNotFoundException.forId("Flight", id);  // Auto-caught → 404
    }
    return ResponseUtil.ok(flight, "Retrieved");
}
```

---

## Creating Custom Exceptions

```java
// Custom exception class
public class BusinessLogicException extends RuntimeException {
    public BusinessLogicException(String message) {
        super(message);
    }
}

// Add handler in GlobalExceptionHandler
@ExceptionHandler(BusinessLogicException.class)
public ResponseEntity<ApiResponse<Void>> handleBusinessLogic(
        BusinessLogicException ex, 
        HttpServletRequest request) {
    ApiResponse<Void> body = new ApiResponse<>();
    body.setSuccess(false);
    body.setStatus(HttpStatus.BAD_REQUEST.value());
    body.setMessage("Business logic error");
    body.setErrors(List.of(ErrorDetail.of("BUSINESS_ERROR", ex.getMessage())));
    body.setPath(request.getRequestURI());
    body.setTraceId(ResponseUtil.generateTraceId());
    
    return ResponseEntity.badRequest().body(body);
}

// Use in controller
if (businessRuleViolated) {
    throw new BusinessLogicException("Business rule violated");  // Auto-caught → 400
}
```

---

## Response Structure Quick Ref

```json
{
  "success": boolean,           // true/false
  "status": int,                // 200, 201, 400, 404, 500, etc.
  "message": "string",          // User-friendly message
  "data": <T>,                  // Response payload (null for errors)
  "errors": [                   // null for success, array for errors
    {
      "field": "optional",      // For field validation only
      "code": "ERROR_CODE",     // e.g., VALIDATION_ERROR, NOT_FOUND
      "message": "string"       // Error details
    }
  ],
  "path": "string",             // Request URL
  "traceId": "uuid",            // For error tracking
  "timestamp": "ISO-8601"       // When response was created
}
```

---

## Common Mistakes

### ❌ Wrong: Always returning 200
```java
@PostMapping
public ResponseEntity<ApiResponse<Flight>> create(...) {
    Flight created = service.create(request);
    return ResponseEntity.ok(created);  // WRONG: should be 201
}
```

### ✅ Correct: Proper status codes
```java
@PostMapping
public ResponseEntity<ApiResponse<Flight>> create(...) {
    Flight created = service.create(request);
    return ResponseUtil.created(created, "Created");  // RIGHT: 201
}
```

---

### ❌ Wrong: Returning null data
```java
@GetMapping("/{id}")
public ResponseEntity<ApiResponse<Flight>> get(@PathVariable Long id) {
    Flight flight = service.get(id);  // May be null
    return ResponseUtil.ok(flight, "Retrieved");  // Returns null data
}
```

### ✅ Correct: Handle null explicitly
```java
@GetMapping("/{id}")
public ResponseEntity<ApiResponse<Flight>> get(@PathVariable Long id) {
    Flight flight = service.get(id);
    if (flight == null) {
        throw EntityNotFoundException.forId("Flight", id);  // → 404
    }
    return ResponseUtil.ok(flight, "Retrieved");
}
```

---

### ❌ Wrong: Exposing stack traces
```java
@ExceptionHandler(Exception.class)
public ResponseEntity<Map<String, Object>> handleError(Exception ex) {
    return ResponseEntity.status(500).body(Map.of(
        "error": ex.getMessage(),
        "stackTrace": ex.getStackTrace()  // DANGEROUS!
    ));
}
```

### ✅ Correct: Hide stack traces, use trace ID
```java
@ExceptionHandler(Exception.class)
public ResponseEntity<ApiResponse<Void>> handleError(Exception ex, HttpServletRequest request) {
    String traceId = ResponseUtil.generateTraceId();
    // Log actual error with traceId (not shown to client)
    logger.error("Error [traceId={}]", traceId, ex);
    
    ApiResponse<Void> body = new ApiResponse<>();
    body.setSuccess(false);
    body.setStatus(500);
    body.setMessage("Internal server error");
    body.setErrors(List.of(ErrorDetail.of("INTERNAL_ERROR", "See traceId: " + traceId)));
    body.setTraceId(traceId);
    
    return ResponseEntity.status(500).body(body);
}
```

---

### ❌ Wrong: Direct ApiResponse constructor
```java
return ResponseEntity.ok(new ApiResponse<>(true, "Message", data));
```

### ✅ Correct: Use ResponseUtil
```java
return ResponseUtil.ok(data, "Message");
```

---

## Testing Responses

### Unit Test Example

```java
@Test
public void testGetFlightSuccess() {
    Flight mockFlight = new Flight();
    mockFlight.setId(1L);
    mockFlight.setFlightNumber("AI101");
    
    when(flightService.getFlightById(1L)).thenReturn(mockFlight);
    
    ResponseEntity<ApiResponse<Flight>> response = 
        flightController.getFlightById(1L);
    
    assertEquals(200, response.getStatusCodeValue());
    assertTrue(response.getBody().isSuccess());
    assertEquals("Flight retrieved successfully", response.getBody().getMessage());
    assertEquals(mockFlight.getId(), response.getBody().getData().getId());
}

@Test
public void testGetFlightNotFound() {
    when(flightService.getFlightById(99L)).thenReturn(null);
    
    assertThrows(EntityNotFoundException.class, () -> {
        flightController.getFlightById(99L);
    });
}
```

---

## Integration Points

### Swagger/OpenAPI Documentation
```java
@RequestMapping(value = "/flight", produces = "application/json")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Flight found"),
    @ApiResponse(responseCode = "404", description = "Flight not found")
})
public class FlightController { ... }
```

### Logging
```java
logger.info("Flight created: flightNumber={}, traceId={}",
    flight.getFlightNumber(), 
    response.getTraceId());

logger.error("Error retrieving flight: id={}, traceId={}",
    id,
    response.getTraceId());
```

### Client Error Tracking
Send `traceId` to error monitoring service (Sentry, etc.) for correlation:
```javascript
if (!response.ok) {
  const data = await response.json();
  errorTracking.captureException(new Error(data.message), {
    tags: { traceId: data.traceId }
  });
}
```

---

## Files Reference

| Component | File |
|-----------|------|
| **ApiResponse<T>** | `src/main/java/com/.../dto/response/ApiResponse.java` |
| **ErrorDetail** | `src/main/java/com/.../dto/response/ErrorDetail.java` |
| **ResponseUtil** | `src/main/java/com/.../util/ResponseUtil.java` |
| **GlobalExceptionHandler** | `src/main/java/com/.../exception/GlobalExceptionHandler.java` |
| **EntityNotFoundException** | `src/main/java/com/.../exception/EntityNotFoundException.java` |

---

## Checklist for New Endpoint

- [ ] Controller method has proper HTTP method (`@GetMapping`, `@PostMapping`, etc.)
- [ ] Returns `ResponseEntity<ApiResponse<T>>`
- [ ] Uses `ResponseUtil` for success responses
- [ ] Uses proper HTTP status code (200, 201, 204, etc.)
- [ ] Throws `EntityNotFoundException` for 404 cases
- [ ] Validation on request with `@Valid`
- [ ] Request body uses `@RequestBody`
- [ ] Documents success & error responses in comments
- [ ] Tested with unit & integration tests
- [ ] Added to Swagger documentation if applicable

---

*Last Updated: February 23, 2026*
*Version: 1.0*
