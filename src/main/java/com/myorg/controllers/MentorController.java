package com.myorg.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myorg.exceptions.ResourceNotFoundException;
import com.myorg.models.Mentor;
import com.myorg.repositories.MentorRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class MentorController
{
	@Autowired
	MentorRepository mentorRepository;

	/**
	 * Returns a specific user by searching for their Id
	 * @param userId
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_MENTOR')")
    @GetMapping("/mentor/")
    public Mentor getMentorById(@RequestParam(value = "id") Integer mentorId) 
    {
        return mentorRepository.findById(mentorId)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor", "id", mentorId));
    }
	
	/**
	 * Creates a new mentor
	 * @param mentor
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_MENTOR')")
	@PostMapping("/mentor")
    public Mentor createMentor(@Valid @RequestBody Mentor mentor) 
	{	
        return mentorRepository.save(mentor);
    }
	
	/**
	 * Returns mentor details by searching for name
	 * @param name
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/mentor/{name}")
	public List<Mentor> getMentorByUserName(@RequestParam(value = "name") String name)
	{
		return mentorRepository.findByMentorName(name);
	}
	
	/**
	 * Returns mentor details by searching for name
	 * @param name
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_MENTOR')")
	@GetMapping("/mentor/{mentorId}")
	public String getMentorIdFromUserId(@RequestParam(value = "userId") String userId)
	{
		return mentorRepository.findMentorIdFromUserId(userId).toString();
	}
	
	/**
	 * Returns a list of all of the mentor Ids
	 * @param mentorId
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/mentor/ids")
	public String[] getListOfMentors()
	{
		return mentorRepository.getListOfMentors();
	}
	
	
	/**
	 * Updates the details of a mentor
	 * @param mentorId
	 * @param mentorDetails
	 * @return Mentor
	 */
	@PutMapping("/mentor/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_MENTOR')")
	public Mentor updateMentor(@PathVariable(value = "id") Integer mentorId, @Valid @RequestBody Mentor mentorDetails) 
	{
		Mentor mentor = mentorRepository.findById(mentorId).orElseThrow(() -> new ResourceNotFoundException("Mentor", "id", mentorId));
		
		mentor.setFacilities(mentorDetails.getFacilities())
			  .setLinkedIn(mentorDetails.getLinkedIn())
			  .setYearsOfExp(mentorDetails.getYearsOfExp());
      
		Mentor updatedMentor = mentorRepository.save(mentor);
		return updatedMentor;
	}
}