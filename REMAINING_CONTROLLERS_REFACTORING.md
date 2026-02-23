# Refactoring Remaining Controllers - Quick Guide

## Overview

4 controllers still need refactoring to match the unified response pattern. This guide shows you exactly how to do each one.

---

## ✅ Controllers Already Refactored

These 5 controllers are ready to use as templates:
- `FlightController.java` ← **USE THIS AS YOUR TEMPLATE**
- `AircraftController.java`
- `AirportController.java`
- `RouteController.java`
- `SeatController.java`

---

## ⏳ Controllers Awaiting Refactoring

1. `AdminController.java`
2. `UserController.java`
3. `HealthCheck.java` (or `HealthCheckController.java`)
4. Other controllers (if any)

---

## Step-by-Step Refactoring Process

### Step 1: Add Required Imports

Add these imports to your controller:

```java
import com.airline.airline.exception.EntityNotFoundException;
import com.airline.airline.util.ResponseUtil;
import com.airline.airline.dto.response.ApiResponse;
```

### Step 2: Add This Pattern to Method Signatures

**Before:**
```java
@GetMapping("/{id}")
public ResponseEntity<?> getById(@PathVariable Long id) {
```

**After:**
```java
@GetMapping("/{id}")
public ResponseEntity<ApiResponse<YourEntity>> getById(@PathVariable Long id) {
```

### Step 3: Use ResponseUtil in Implementations

**Before:**
```java
@PostMapping
public ResponseEntity<?> create(@RequestBody YourRequest request) {
    YourEntity created = service.create(request);
    return ResponseEntity.ok(new ApiResponse<>(true, "Created", created));
}
```

**After:**
```java
@PostMapping
public ResponseEntity<ApiResponse<YourEntity>> create(@RequestBody YourRequest request) {
    YourEntity created = service.create(request);
    return ResponseUtil.created(created, "Entity created successfully");
}
```

### Step 4: Handle Missing Resources

**Before:**
```java
@GetMapping("/{id}")
public ResponseEntity<?> getById(@PathVariable Long id) {
    YourEntity entity = service.getById(id);
    return ResponseEntity.ok(new ApiResponse<>(true, "Retrieved", entity));  // Null OK?
}
```

**After:**
```java
@GetMapping("/{id}")
public ResponseEntity<ApiResponse<YourEntity>> getById(@PathVariable Long id) {
    YourEntity entity = service.getById(id);
    if (entity == null) {
        throw EntityNotFoundException.forId("YourEntity", id);  // Auto-handled → 404
    }
    return ResponseUtil.ok(entity, "Entity retrieved successfully");
}
```

### Step 5: Use Proper Status Codes

**Before:**
```java
@DeleteMapping("/{id}")
public ResponseEntity<?> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(true, "Deleted", null));  // Wrong: 200
}
```

**After:**
```java
@DeleteMapping("/{id}")
public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseUtil.noContent("Entity deleted successfully");  // Right: 204
}
```

---

## Template Code for Each HTTP Method

Copy and adapt these templates:

### GET All (200 OK)
```java
@GetMapping
public ResponseEntity<ApiResponse<List<YourEntity>>> getAll() {
    List<YourEntity> entities = service.getAll();
    return ResponseUtil.ok(entities, "Entities retrieved successfully");
}
```

### GET One (200 OK or 404 Not Found)
```java
@GetMapping("/{id}")
public ResponseEntity<ApiResponse<YourEntity>> getById(@PathVariable Long id) {
    YourEntity entity = service.getById(id);
    if (entity == null) {
        throw EntityNotFoundException.forId("YourEntity", id);
    }
    return ResponseUtil.ok(entity, "Entity retrieved successfully");
}
```

### POST Create (201 Created or 400 Bad Request)
```java
@PostMapping
public ResponseEntity<ApiResponse<YourEntity>> create(@Valid @RequestBody YourRequest request) {
    YourEntity created = service.create(request);
    return ResponseUtil.created(created, "Entity created successfully");
}
```

### PUT Update (200 OK, 404 Not Found, or 400 Bad Request)
```java
@PutMapping("/{id}")
public ResponseEntity<ApiResponse<YourEntity>> update(
        @PathVariable Long id,
        @Valid @RequestBody YourRequest request) {
    YourEntity updated = service.update(id, request);
    if (updated == null) {
        throw EntityNotFoundException.forId("YourEntity", id);
    }
    return ResponseUtil.ok(updated, "Entity updated successfully");
}
```

### DELETE (204 No Content or 404 Not Found)
```java
@DeleteMapping("/{id}")
public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseUtil.noContent("Entity deleted successfully");
}
```

---

## Refactoring AdminController (Example)

### Before (Current)
```java
@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @PostMapping("/user-suspend")
    public ResponseEntity<?> suspendUser(@RequestBody Map<String, Long> request) {
        // Some implementation
        return ResponseEntity.ok("User suspended");
    }
}
```

### After (Refactored)
```java
@RestController
@RequestMapping("/admin")
public class AdminController {
    
    private final AdminService adminService;
    
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    
    @PostMapping("/user-suspend")
    public ResponseEntity<ApiResponse<Void>> suspendUser(@PathVariable Long userId) {
        adminService.suspendUser(userId);
        return ResponseUtil.noContent("User suspended successfully");
    }
    
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<User>> getUser(@PathVariable Long id) {
        User user = adminService.getUser(id);
        if (user == null) {
            throw EntityNotFoundException.forId("User", id);
        }
        return ResponseUtil.ok(user, "User retrieved successfully");
    }
}
```

---

## Refactoring UserController (Example)

### Before (Current)
```java
@RestController
@RequestMapping("/user")
public class UserController {
    
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(new ApiResponse<>(true, "Profile", user));
    }
    
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody UserRequest request) {
        User updated = userService.updateProfile(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Updated", updated));
    }
}
```

### After (Refactored)
```java
@RestController
@RequestMapping("/user")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * GET - Retrieve current user profile
     * Returns 200 OK with user data
     */
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<User>> getProfile() {
        User user = userService.getCurrentUser();
        if (user == null) {
            throw EntityNotFoundException.forId("User", -1L);  // or handle differently
        }
        return ResponseUtil.ok(user, "Profile retrieved successfully");
    }
    
    /**
     * PUT - Update current user profile
     * Returns 200 OK with updated user data
     */
    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<User>> updateProfile(@Valid @RequestBody UserRequest request) {
        User updated = userService.updateProfile(request);
        return ResponseUtil.ok(updated, "Profile updated successfully");
    }
    
    /**
     * GET - Get user by ID (admin)
     * Returns 200 OK or 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            throw EntityNotFoundException.forId("User", id);
        }
        return ResponseUtil.ok(user, "User retrieved successfully");
    }
    
    /**
     * DELETE - Delete user account
     * Returns 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseUtil.noContent("User deleted successfully");
    }
}
```

---

## Refactoring HealthCheck (Example)

### Before (Current - if applicable)
```java
@RestController
@RequestMapping("/health")
public class HealthCheck {
    
    @GetMapping
    public ResponseEntity<?> health() {
        return ResponseEntity.ok("OK");
    }
}
```

### After (Refactored)
```java
@RestController
@RequestMapping("/health")
public class HealthCheckController {
    
    @GetMapping
    public ResponseEntity<ApiResponse<HealthStatus>> getHealth() {
        HealthStatus status = new HealthStatus("UP", System.currentTimeMillis());
        return ResponseUtil.ok(status, "Application is healthy");
    }
}

// Simple DTO
public class HealthStatus {
    private String status;
    private long timestamp;
    
    public HealthStatus(String status, long timestamp) {
        this.status = status;
        this.timestamp = timestamp;
    }
    
    // Getters
    public String getStatus() { return status; }
    public long getTimestamp() { return timestamp; }
}
```

---

## Checklist for Each Controller

Use this checklist when refactoring each controller:

### Imports
- [ ] Added `EntityNotFoundException` import
- [ ] Added `ResponseUtil` import
- [ ] Added `ApiResponse` import
- [ ] Removed old response utility imports

### Method Signatures
- [ ] All methods return `ResponseEntity<ApiResponse<T>>`
- [ ] Generic type properly specified
- [ ] Void for DELETE operations

### GET Methods
- [ ] Uses `ResponseUtil.ok()` with data
- [ ] Checks for null and throws `EntityNotFoundException`
- [ ] Has proper documentation comment

### POST Methods
- [ ] Uses `ResponseUtil.created()` 
- [ ] Has `@Valid` annotation on request body
- [ ] Returns 201 Created

### PUT Methods
- [ ] Uses `ResponseUtil.ok()` with updated data
- [ ] Checks for null and throws `EntityNotFoundException`
- [ ] Returns 200 OK

### DELETE Methods
- [ ] Uses `ResponseUtil.noContent()`
- [ ] Does NOT include data in response
- [ ] Returns 204 No Content

### Error Handling
- [ ] Throws `EntityNotFoundException` for missing resources
- [ ] Lets validation errors bubble up to `GlobalExceptionHandler`
- [ ] No try-catch blocks for normal errors

### Testing
- [ ] Tested GET returning 200
- [ ] Tested POST returning 201
- [ ] Tested PUT returning 200
- [ ] Tested DELETE returning 204
- [ ] Tested 404 for missing resources
- [ ] Tested 400 for validation errors

---

## Real-World Command Reference

### ResponseUtil Methods Used Most
```java
ResponseUtil.ok(data, "Message")              // GET/PUT - 200
ResponseUtil.created(data, "Message")         // POST - 201
ResponseUtil.noContent("Message")             // DELETE - 204
ResponseUtil.badRequest("Message")            // Validation - 400
ResponseUtil.unauthorized("Message")          // Auth - 401
ResponseUtil.forbidden("Message")             // Perms - 403
// EntityNotFoundException                     // Missing - 404 (auto-caught)
ResponseUtil.conflict("Message")              // Duplicate - 409
ResponseUtil.internalServerError("Message")   // Error - 500 (auto-caught)
```

---

## Common Gotchas to Avoid

❌ **DON'T** - Return 200 for DELETE
```java
@DeleteMapping("/{id}")
public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseUtil.ok(null, "Deleted");  // WRONG!
}
```

✅ **DO** - Return 204 for DELETE
```java
@DeleteMapping("/{id}")
public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseUtil.noContent("Deleted successfully");  // RIGHT!
}
```

---

❌ **DON'T** - Return null data for GET
```java
@GetMapping("/{id}")
public ResponseEntity<ApiResponse<User>> get(@PathVariable Long id) {
    User user = service.get(id);  // May be null
    return ResponseUtil.ok(user, "Retrieved");  // WRONG if null!
}
```

✅ **DO** - Check for null
```java
@GetMapping("/{id}")
public ResponseEntity<ApiResponse<User>> get(@PathVariable Long id) {
    User user = service.get(id);
    if (user == null) {
        throw EntityNotFoundException.forId("User", id);  // Auto-caught → 404
    }
    return ResponseUtil.ok(user, "Retrieved successfully");
}
```

---

## Estimated Effort

| Task | Time | Difficulty |
|------|------|-----------|
| Refactor 1 controller | 15-20 min | Easy |
| Refactor 4 controllers | 1-1.5 hours | Easy |
| Test all responses | 30-45 min | Easy |
| Full refactoring + test | 2-3 hours | Easy |

---

## Support Resources

If you get stuck:

1. **See working example:** `FlightController.java`
2. **See all patterns:** `API_RESPONSE_CHEATSHEET.md`
3. **See response examples:** `API_RESPONSE_EXAMPLES.md`
4. **See full guide:** `API_RESPONSE_GUIDE.md`

---

## Verification Steps

After refactoring each controller, verify:

```bash
# 1. Compile
mvn clean compile

# 2. Test with cURL (GET)
curl http://localhost:8080/your-endpoint/1

# 3. Test with cURL (POST)
curl -X POST http://localhost:8080/your-endpoint \
  -H "Content-Type: application/json" \
  -d '{...}'

# 4. Test with cURL (DELETE)
curl -X DELETE http://localhost:8080/your-endpoint/1

# 5. Test with cURL (404)
curl http://localhost:8080/your-endpoint/99999

# 6. Test with cURL (400 - bad request)
curl -X POST http://localhost:8080/your-endpoint \
  -H "Content-Type: application/json" \
  -d '{}'
```

---

*Complete refactoring takes approximately 2-3 hours for all 4 remaining controllers*
