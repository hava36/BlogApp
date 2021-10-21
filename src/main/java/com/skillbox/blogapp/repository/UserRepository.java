package com.skillbox.blogapp.repository;

import com.skillbox.blogapp.model.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Users entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    String USERS_BY_LOGIN_CACHE = "usersByName";

    String USERS_BY_EMAIL_CACHE = "usersByEmail";

    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);

}
