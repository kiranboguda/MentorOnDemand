package com.myorg.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myorg.models.Mentor;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Integer>
{
    @Query(value = "SELECT * FROM mentor m INNER JOIN users u ON m.user_id = u.id WHERE u.name = :name"
        	,nativeQuery = true)
    public List<Mentor> findByMentorName(@Param("name") String name);
    
    @Query(value = "SELECT mentor_id FROM mentor WHERE user_id = :userId"
    		,nativeQuery = true)
    public String findMentorIdFromUserId(@Param("userId") String userId);
    
	@Query(value = "SELECT DISTINCT mentor_id from mentor"
			,nativeQuery = true)
		public String[] getListOfMentors();
}