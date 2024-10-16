package escuelaing.edu.co.JPA;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * UserService handles the business logic for user management, such as authentication,
 * user registration, and loading user details for security.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Loads a user by their username for authentication purposes.
     * 
     * @param username the username of the user to load
     * @return UserDetails containing the user's data required for authentication
     * @throws UsernameNotFoundException if the user is not found
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(), user.getPassword(), new ArrayList<>());
    }

     /**
     * Saves a new user in the database.
     * 
     * @param user the user to save
     * @return the saved User entity
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Authenticates a user by checking if the provided password matches the stored one.
     * 
     * @param username the username of the user to authenticate
     * @param rawPassword the raw password provided by the user
     * @return true if the authentication is successful, false otherwise
     */
    public boolean authenticate(String username, String rawPassword) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent() && passwordEncoder.matches(rawPassword, user.get().getPassword());
    }
}
