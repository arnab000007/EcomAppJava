package com.example.ecomappjava.Reporsitory;

import com.example.ecomappjava.models.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionsRepository extends JpaRepository<UserSession, Long> {
    UserSession save(UserSession userSession);
}
