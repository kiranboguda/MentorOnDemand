package com.myorg.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myorg.exceptions.ResourceNotFoundException;
import com.myorg.models.Technologies;
import com.myorg.repositories.TechnologiesRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TechnologiesController
{
	@Autowired
	TechnologiesRepository technologiesRepository;
	
	/**
	 * Returns all technologies
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_MENTOR')")
	@GetMapping("/tech")
    public List<Technologies> getAllTechnologies() 
	{
        return technologiesRepository.findAll();
    }
	
	/**
	 * Returns the name of all technologies
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_MENTOR') OR hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
	@GetMapping("/technames")
    public List<String> getAllTechNames() 
	{
        return technologiesRepository.findAllTechNames();
    }
	
	/**
	 * Returns a specific technologies by searching for their Id
	 * @param techId
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/tech/{id}")
    public Technologies getUserById(@PathVariable(value = "id") Integer techId) 
    {
        return technologiesRepository.findById(techId)
                .orElseThrow(() -> new ResourceNotFoundException("Technologies", "id", techId));
    }
	
	/**
	 * Creates a new technology
	 * @param Technologies
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/tech/add")
    public Technologies createTech(@Valid @RequestBody Technologies technologies) 
	{	
        return technologiesRepository.save(technologies);
    }
	
	/**
	 * Updates the details of a technology
	 * @param techId
	 * @param technologiesDetails
	 * @return Technologies
	 */
	@PutMapping("/tech/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Technologies updateTechnologies(@PathVariable(value = "id") Integer techId, @Valid @RequestBody Technologies technologiesDetails) 
	{
		Technologies technologies = technologiesRepository.findById(techId).orElseThrow(() -> new ResourceNotFoundException("Technologies", "id", techId));
		
		technologies.setTechName(technologiesDetails.getTechName());
      
		Technologies updatedTechnologies = technologiesRepository.save(technologies);
		return updatedTechnologies;
	}
	
    @DeleteMapping("/tech/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteTech(@RequestParam(value = "techId") Integer techId) 
    {
        Technologies technologies = technologiesRepository.findById(techId)
                .orElseThrow(() -> new ResourceNotFoundException("Technology", "id", techId));

        technologiesRepository.delete(technologies); 

        return ResponseEntity.ok().build();
    }
}