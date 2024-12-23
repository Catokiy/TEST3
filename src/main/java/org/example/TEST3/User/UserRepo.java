package org.example.TEST3.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

//    @Query("select t from users t where t.fullName like %?1%")
//    Optional<User> findByFullName(String fullName);
}
