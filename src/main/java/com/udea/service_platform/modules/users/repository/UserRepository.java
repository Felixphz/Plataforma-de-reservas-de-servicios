package com.udea.service_platform.modules.users.repository;

import com.udea.service_platform.modules.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByCorreo(String correo);

    Optional<User> findByCorreo(String correo);
}
