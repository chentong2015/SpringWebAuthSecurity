package org.example.methd;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

// @PreAuthorize: Authorizes the condition before executing the method.
// @PostAuthorize: Authorizes the condition after the method is executed
//  @EnableGlobalMethodSecurity(prePostEnabled = true) to our configuration class
@Service
public class MethodAuthorizeService {

    @PreAuthorize("hasRole('ADMIN')")
    public void runAdminRequest(Long id) {
        // only invoked if the `Authentication` has the `ROLE_ADMIN` authority
        System.out.println("Invoked");
    }
}
