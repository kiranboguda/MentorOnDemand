package com.myorg.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.myorg.models.User;

/**
 * Persistence layer for User entity
 * Adds findByUsername method for use with authentication
 * @author KiranBoguda
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    
    @Query(value = "SELECT name FROM users u INNER JOIN user_roles ur ON u.id = ur.user_id WHERE ur.role_id = 2",
    	nativeQuery = true
    )
    public List<String> getMentorName();
    
    @Query(value = "SELECT * from users WHERE user_blocked = 'N'",
    		nativeQuery = true
    )
    public List<User> getUnblockedUsers();
    
    @Query(value = "SELECT * from users WHERE user_blocked = 'Y'",
    		nativeQuery = true
    )
    public List<User> getBlockedUsers();
}