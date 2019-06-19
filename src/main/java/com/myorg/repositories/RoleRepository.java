package com.myorg.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myorg.models.Role;
import com.myorg.models.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> 
{
    Optional<Role> findByName(RoleName roleName);
}
