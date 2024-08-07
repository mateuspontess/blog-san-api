package br.com.blogsanapi.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.blogsanapi.model.user.User;
import br.com.blogsanapi.model.user.UserRole;
import br.com.blogsanapi.repository.UserRepository;
import jakarta.annotation.PostConstruct;

@Configuration
public class DefaultAdminConfiguration {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @PostConstruct
    void createDefaultAdminUser() {
        if (!userRepository.existsByLogin("root")) {
            User userAdmin = User.builder()
                .name("admin")
                .login("root")
                .password(encoder.encode("root"))
                .role(UserRole.ADMIN)
                .build();

            userRepository.save(userAdmin);
        }
    }
}
