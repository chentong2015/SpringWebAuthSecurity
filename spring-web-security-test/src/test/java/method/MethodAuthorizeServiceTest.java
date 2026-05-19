package method;

import org.example.method.MethodAuthorizeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
public class MethodAuthorizeServiceTest {

    @Autowired
    MethodAuthorizeService methodAuthorizeService;

    @WithMockUser(roles="ADMIN")
    @Test
    void readAccountWithAdminRoleThenInvokes() {
        this.methodAuthorizeService.runAdminRequest(10l);
        Assertions.assertTrue(true);
    }

    @WithMockUser(roles="WRONG")
    @Test
    void readAccountWithWrongRoleThenAccessDenied() {
        Assertions.assertThrows(AccessDeniedException.class,
                () -> this.methodAuthorizeService.runAdminRequest(10l));
    }
}
