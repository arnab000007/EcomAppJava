package com.example.ecomappjava.Reporsitory;

import com.example.ecomappjava.models.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ecomappjava.models.State;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserReporsitory extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByEmailAndState(String email, State state);

    Optional<AuthUser> findByIdAndState(Long id, State state);

    AuthUser save(AuthUser user);

}