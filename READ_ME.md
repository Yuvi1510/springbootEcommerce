┌─────────────────────────────────────────────────────────────────┐
│                        REQUEST FLOW                              │
├─────────────────────────────────────────────────────────────────┤
│                                                                   │
│  React App ─────────────────────────────────────────────────┐   │
│  (http://localhost:3000)                                    │   │
│                                                              ▼   │
│  Request with Header:                               Spring Boot  │
│  Authorization: Bearer eyJhbGc...                  (port 8080)   │
│                                                              │   │
└──────────────────────────────────────────────────────────────┼───┘
│
▼
┌─────────────────────────────────────────────────────────────────┐
│                    SECURITY FILTER CHAIN                         │
├─────────────────────────────────────────────────────────────────┤
│                                                                   │
│  [1] CorsFilter                                                  │
│      └─ Adds CORS headers                                        │
│                                                                   │
│  [2] JwtAuthenticationFilter (OUR FILTER) ⭐⭐⭐                   │
│      ├─ Extract token from header                                │
│      ├─ Validate token                                           │
│      ├─ Get username from token                                  │
│      ├─ Load user from database                                  │
│      └─ Set Authentication in SecurityContext                    │
│                                                                   │
│  [3] UsernamePasswordAuthenticationFilter                        │
│      └─ (Skipped because we already set auth)                    │
│                                                                   │
│  [4] ExceptionTranslationFilter                                  │
│      └─ Handles auth exceptions                                  │
│                                                                   │
│  [5] FilterSecurityInterceptor                                   │
│      └─ Checks if user has permission for this endpoint          │
│                                                                   │
└─────────────────────────────────────────────────────────────────┘
│
▼
┌─────────────────────────────────────────────────────────────────┐
│                      CONTROLLER LAYER                            │
├─────────────────────────────────────────────────────────────────┤
│                                                                   │
│  @RestController                                                 │
│  public class DashboardController {                              │
│                                                                   │
│      @GetMapping("/api/dashboard")                               │
│      public String dashboard() {                                 │
│          // Get authenticated user from SecurityContext          │
│          Authentication auth =                                   │
│              SecurityContextHolder.getContext()                  │
│                  .getAuthentication();                           │
│                                                                   │
│          String username = auth.getName();                       │
│          return "Welcome " + username;                           │
│      }                                                           │
│  }                                                               │
│                                                                   │
└─────────────────────────────────────────────────────────────────┘


# **Token Validation Flow - Each Subsequent Request**
┌─────────────────────────────────────────────────────────────┐
│                    REQUEST WITH TOKEN                        │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  Request: GET /api/products                                  │
│  Headers:                                                    │
│    Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...            │
│                                                              │
└─────────────────────────────────────────────────────────────┘
│
▼
┌─────────────────────────────────────────────────────────────┐
│              JwtAuthenticationFilter                         │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  String token = getTokenFromRequest(request);               │
│  // token = "eyJhbGciOiJIUzUxMiJ9..."                       │
│                                                              │
│  if (token != null) {                                        │
│      // Validate token signature & expiry                   │
│      boolean isValid = tokenProvider.validateToken(token);  │
│      // isValid = true                                       │
│                                                              │
│      // Get username from token                              │
│      String username = tokenProvider.getUsernameFromToken(token);│
│      // username = "john@email.com"                          │
│                                                              │
│      // Load user from database                              │
│      UserDetails user = userDetailsService                   │
│          .loadUserByUsername(username);                      │
│                                                              │
│      // Create Authentication object                         │
│      UsernamePasswordAuthenticationToken auth =              │
│          new UsernamePasswordAuthenticationToken(            │
│              user, null, user.getAuthorities());             │
│                                                              │
│      // Set in SecurityContext                               │
│      SecurityContextHolder.getContext()                      │
│          .setAuthentication(auth);                           │
│  }                                                           │
│                                                              │
│  // Continue filter chain                                    │
│  filterChain.doFilter(request, response);                    │
│                                                              │
└─────────────────────────────────────────────────────────────┘
│
▼
┌─────────────────────────────────────────────────────────────┐
│                    CONTROLLER                                │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  @GetMapping("/api/products")                                │
│  public List<Product> getProducts() {                        │
│      // Authentication already set in context                │
│      Authentication auth = SecurityContextHolder             │
│          .getContext().getAuthentication();                  │
│                                                              │
│      // auth.isAuthenticated() = true                        │
│      // auth.getName() = "john@email.com"                    │
│                                                              │
│      return productService.findAll();                        │
│  }                                                           │
│                                                              │
└─────────────────────────────────────────────────────────────┘

