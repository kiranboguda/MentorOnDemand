package com.myorg.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.myorg.models.Technologies;

@Repository
public interface TechnologiesRepository extends JpaRepository<Technologies, Integer>
{
    @Query(value = "SELECT technology_name FROM technologies"
        	,nativeQuery = true)
    public List<String> findAllTechNames();
}