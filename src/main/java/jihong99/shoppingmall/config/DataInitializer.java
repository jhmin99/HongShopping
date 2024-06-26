package jihong99.shoppingmall.config;

import jakarta.annotation.PostConstruct;
import jihong99.shoppingmall.entity.Users;
import jihong99.shoppingmall.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import static jihong99.shoppingmall.entity.enums.Roles.SUPER_ADMIN;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        createSuperAdmin();
    }

    private void createSuperAdmin() {
        String superAdminIdentification = "superadmin";
        String superAdminPassword = "superadmin_password";

        if (userRepository.findByIdentification(superAdminIdentification).isEmpty()) {
            Users superAdmin = Users.builder()
                            .identification(superAdminIdentification)
                                    .password(passwordEncoder.encode(superAdminPassword))
                                            .build();
            superAdmin.updateRole(SUPER_ADMIN);
            userRepository.save(superAdmin);
            System.out.println("Super Admin created with ID: " + superAdminIdentification);
        }
    }
}