package com.udea.service_platform.modules.users.repository;

import com.udea.service_platform.modules.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByCorreo(String correo);
}
