package escuelaing.edu.co.JPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


/**
 * REST Controller to handle user-related operations such as registration and authentication.
 */
@CrossOrigin(origins = "https://labserverapache.duckdns.org", allowCredentials = "true")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    /**
     * Constructor-based dependency injection for PasswordEncoder and UserService.
     *
     * @param passwordEncoder the password encoder to use for hashing passwords.
     * @param userService the user service to handle user operations.
     */
    @Autowired
    public UserController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    /**
     * Registers a new user by encoding the password and saving the user entity.
     *
     * @param user the user object containing username and password.
     * @return a success message if the user is registered successfully.
     */
    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return "Usuario registrado exitosamente";
    }

    /**
     * Authenticates a user by validating the username and password.
     *
     * @param user the user object containing the login credentials.
     * @return 200 OK if authentication is successful, 401 Unauthorized otherwise.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody User user) {
        if (userService.authenticate(user.getUsername(), user.getPassword())) {
            return ResponseEntity.ok("Authenticated");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
