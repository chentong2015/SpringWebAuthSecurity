package org.example.methd;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class MethodAuthorizeService {

    @PreAuthorize("hasRole('ADMIN')")
    public void runAdminRequest(Long id) {
        // only invoked if the `Authentication` has the `ROLE_ADMIN` authority
        System.out.println("Invoked");
    }
}
