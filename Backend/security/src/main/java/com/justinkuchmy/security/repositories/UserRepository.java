package com.justinkuchmy.security.repositories;
import com.justinkuchmy.security.dto.UserDto;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDto, String> {

    @Query(value ="SELECT * FROM _user WHERE EMAIL = ?1", nativeQuery = true ) 
    Optional<UserDto> findByEmail(String EMAIL);
}
