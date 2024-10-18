package org.example.service;

import org.example.credentials.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<UserCredentials, String> {

    UserCredentials findByUsername(String username);
}
