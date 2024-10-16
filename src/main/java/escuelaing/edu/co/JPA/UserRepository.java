package escuelaing.edu.co.JPA;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRepository is a Data Access Layer interface that extends JpaRepository.
 * It provides methods for performing CRUD operations on User entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their username.
     * 
     * @param username the username to search for.
     * @return an Optional containing the found User or empty if no user is found.
     */
    Optional<User> findByUsername(String username);
}
