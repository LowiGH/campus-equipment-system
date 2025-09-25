@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthController(StudentRepository repo, PasswordEncoder encoder, AuthenticationManager manager) {
        this.studentRepository = repo;
        this.passwordEncoder = encoder;
        this.authenticationManager = manager;
    }

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        studentRepository.save(student);
        return ResponseEntity.ok("Registration successful!");
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        // authenticate using Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        return ResponseEntity.ok("Login successful!");
    }
}
