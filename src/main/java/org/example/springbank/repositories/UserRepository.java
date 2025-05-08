package org.example.springbank.repositories;

import org.example.springbank.enums.UserRole;
import org.example.springbank.models.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, Long> {
    @Query("SELECT u FROM CustomUser u WHERE u.email = :email")
    Optional<CustomUser> findByEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM CustomUser u WHERE u.role = :role")
    boolean existsByRole(@Param("role") UserRole role);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM CustomUser u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);
}
