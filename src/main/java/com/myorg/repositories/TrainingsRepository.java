package com.myorg.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myorg.models.Trainings;

@Repository
public interface TrainingsRepository extends JpaRepository<Trainings, Integer>
{
	public List<Trainings> findByStartDate(Date startDate);
	public List<Trainings> findByTechId(int techId);
	
	@Query(value = "SELECT * FROM trainings tr INNER JOIN technologies t ON tr.tech_id = t.tech_id WHERE t.technology_name = :name AND tr.status = 'N'"
        	,nativeQuery = true)
    public List<Trainings> findByTechName(@Param("name") String name);
	
	@Query(value = "SELECT * FROM trainings WHERE mentor_id = :mentor_id AND status = 'P'"
        	,nativeQuery = true)
    public List<Trainings> findProposedTrainingsByMentorId(@Param("mentor_id") int mentor_id);
	
	@Query(value = "SELECT * FROM trainings WHERE user_id = :user_id AND status = 'A'"
			,nativeQuery = true)
	public List<Trainings> findAcceptedTrainingsByUserId(@Param("user_id") int user_id);
	
	@Query(value = "SELECT * FROM trainings WHERE user_id = :user_id AND status = 'F'"
			,nativeQuery = true)
	public List<Trainings> findCurrentTrainingsById(@Param("user_id") int user_id);
	
	@Query(value = "SELECT tr.rating FROM trainings tr INNER JOIN mentor m ON tr.mentor_id = m.mentor_id INNER JOIN users u ON u.id = m.user_id WHERE u.name = :name"
			,nativeQuery = true)
	public List<Integer> findAllMentorRatings(@Param("name") String name);
	
	@Query(value = "SELECT * FROM trainings WHERE mentor_id = :mentor_id AND status = 'F'"
			,nativeQuery = true)
	public List <Trainings> findAllInProgressTrainingsByMentorId(@Param("mentor_id") int mentor_id);
	
	@Query(value = "SELECT * FROM trainings WHERE status = 'F'"
			,nativeQuery = true)
	public List <Trainings> findAllInProgressTrainings();
	
	@Query(value = "SELECT * FROM trainings WHERE status = 'N'"
			,nativeQuery = true)
	public List<Trainings> findTrainingsForCalendar();
}