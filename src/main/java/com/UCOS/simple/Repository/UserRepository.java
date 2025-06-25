package com.UCOS.simple.Repository;

import com.UCOS.simple.Entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

    @Transactional
    Long deleteByUsername(String username);
}
