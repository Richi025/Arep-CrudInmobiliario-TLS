package escuelaing.edu.co.JPA;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/**
 * Security configuration class for the application.
 * Defines how HTTP requests, login, and logout will be managed.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Defines a bean for password encoding using BCrypt.
     * This bean will be used to encrypt user passwords.
     * 
     * @return a PasswordEncoder instance using BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the security filter chain to manage HTTP requests.
     * Sets the access rules and login/logout behaviors.
     * 
     * @param http HttpSecurity object to configure HTTP security.
     * @return a configured SecurityFilterChain for the application.
     * @throws Exception if there is an error in the configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/properties").authenticated()
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/login.html")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
        return http.build();
    }
}
