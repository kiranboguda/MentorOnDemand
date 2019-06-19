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
import com.myorg.models.Trainings;
import com.myorg.repositories.TrainingsRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TrainingsController
{
	@Autowired
	TrainingsRepository trainingsRepository;
	
	/**
	 * Returns all trainings
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/training")
    public List<Trainings> getAllTrainings() 
	{
        return trainingsRepository.findAll();
    }
	
	/**
	 * Creates a new trainings row
	 * @param trainings
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_MENTOR')")
	@PostMapping("/trainings")
    public Trainings createTrainings(@Valid @RequestBody Trainings trainings) 
	{	
        return trainingsRepository.save(trainings);
    }
	
	/**
	 * Returns available trainings by searching for technology name
	 * @param name
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/trainings/{name}")
	public List<Trainings> getTechByTechName(@RequestParam(value = "name") String name)
	{
		return trainingsRepository.findByTechName(name);
	}
	
	/**
	 * Returns proposed trainings for a mentor
	 * @param name
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_MENTOR')")
	@GetMapping("/trainings/proposed/{mentorid}")
	public List<Trainings> getProposedTrainingsByMentorId(@RequestParam(value = "mentorId") int mentorId)
	{
		return trainingsRepository.findProposedTrainingsByMentorId(mentorId);
	}
	
	/**
	 * Returns confirmed trainings for a user that are awaiting payment
	 * @param name
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/trainings/proposed/{userId}")
	public List<Trainings> getConfirmedTrainingsByUserId(@RequestParam(value = "userId") int userId)
	{
		return trainingsRepository.findAcceptedTrainingsByUserId(userId);
	}
	
	/**
	 * Creates a new training row
	 * 
	 * @param trainingsId
	 * @param trainingsDetails
	 * @return
	 */
	@PutMapping("/trainings/{id}")
	@PreAuthorize("hasRole('ROLE_MENTOR','ROLE_ADMIN')")
	public Trainings updateTrainings(@PathVariable(value = "id") Integer trainingsId, @Valid @RequestBody Trainings trainingsDetails) 
	{
		Trainings trainings = trainingsRepository.findById(trainingsId).orElseThrow(() -> new ResourceNotFoundException("Trainings", "id", trainingsId));
		
		trainings.setEndDate(trainingsDetails.getEndDate());
		trainings.setProgress(trainingsDetails.getProgress());
		trainings.setRating(trainingsDetails.getRating());
		trainings.setStartDate(trainingsDetails.getStartDate());
		trainings.setStatus('N'); //New Training
		trainings.setTechId(trainingsDetails.getTechId());
		trainings.setMentorId(trainingsDetails.getMentorId());
		trainings.setCost(trainingsDetails.getCost());
		trainings.setPaymentFromUser(trainingsDetails.getPaymentFromUser());
		trainings.setPaymentToMentor(trainingsDetails.getPaymentToMentor());
      
		Trainings updatedTrainings = trainingsRepository.save(trainings);
		return updatedTrainings;
	}
	
	/**
	 * Confirms a proposed training course 
	 * @param trainingsId
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/trainings/propose/")
	public Trainings proposeTraining(@RequestParam(value = "trainingId") int trainingId, @RequestParam(value = "userId") int userId)
	{
		Trainings trainings = trainingsRepository.findById(trainingId).orElseThrow(() -> new ResourceNotFoundException("Trainings", "id", trainingId));
		trainings.setStatus('P'); //Proposed Training
		trainings.setUserId(userId);
		
		Trainings updatedTrainings = trainingsRepository.save(trainings);
		return updatedTrainings;
	}
	
	/**
	 * Confirms a proposed training course 
	 * @param trainingsId
	 */
	@PreAuthorize("hasRole('ROLE_MENTOR')")
	@PutMapping("/trainings/confirm/{id}")
	public Trainings confirmTraining(@PathVariable(value = "id") Integer trainingsId)
	{
		Trainings trainings = trainingsRepository.findById(trainingsId).orElseThrow(() -> new ResourceNotFoundException("Trainings", "id", trainingsId));
		trainings.setStatus('A'); //Accepted Training
		
		Trainings updatedTrainings = trainingsRepository.save(trainings);
		return updatedTrainings;
	}
	
	/**
	 * Reject a proposed training course 
	 * @param trainingsId
	 */
	@PreAuthorize("hasRole('ROLE_MENTOR')")
	@PutMapping("/trainings/reject/{id}")
	public Trainings rejectTraining(@PathVariable(value = "id") Integer trainingsId)
	{
		Trainings trainings = trainingsRepository.findById(trainingsId).orElseThrow(() -> new ResourceNotFoundException("Trainings", "id", trainingsId));
		trainings.setStatus('N'); //Rejected so it returns back to original state
		
		Trainings updatedTrainings = trainingsRepository.save(trainings);
		return updatedTrainings;
	}
	
	/**
	 * Finalizes the a training course after payment is received
	 * @param finaliseTraining
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("trainings/finalise/")
	public void finaliseTraining(@Valid @RequestParam(value = "trainingId") int trainingId, @RequestParam(value = "cost") int cost)
	{
		Trainings trainings = trainingsRepository.findById(trainingId).orElseThrow(() -> new ResourceNotFoundException("Trainings", "id", trainingId));
		trainings.setPaymentFromUser(cost);
		trainings.setStatus('F'); //Finalised Training
		trainingsRepository.save(trainings);
	}
	
	/**
	 * Returns confirmed trainings for a user that are awaiting payment
	 * @param name
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/trainings/current/{userId}")
	public List<Trainings> getCurrentTrainingsByUserId(@RequestParam(value = "userId") int userId)
	{
		return trainingsRepository.findCurrentTrainingsById(userId);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("trainings/rate/")
	public void rateTraining(@Valid @RequestParam(value = "trainingId") int trainingId, @RequestParam(value = "rating") int rating)
	{
		Trainings trainings = trainingsRepository.findById(trainingId).orElseThrow(() -> new ResourceNotFoundException("Trainings", "id", trainingId));
		trainings.setRating(rating);
		trainingsRepository.save(trainings);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("trainings/ratings/{name}")
	public List<Integer> getTrainingRatings(@Valid @RequestParam(value = "name") String name)
	{
		return trainingsRepository.findAllMentorRatings(name);
	}

	/**
	 * Returns finalised trainings for a mentor to update training progress
	 * @param name
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_MENTOR')")
	@GetMapping("/trainings/finalised/{mentorid}")
	public List<Trainings> getAllInProgressTrainingsByMentorId(@RequestParam(value = "mentorId") int mentorId)
	{
		return trainingsRepository.findAllInProgressTrainingsByMentorId(mentorId);
	}
	
	@PreAuthorize("hasRole('ROLE_MENTOR')")
	@PutMapping("trainings/progress/")
	public void updateProgress(@Valid @RequestParam(value = "trainingId") int trainingId, @RequestParam(value = "progress") int progress)
	{
		Trainings trainings = trainingsRepository.findById(trainingId).orElseThrow(() -> new ResourceNotFoundException("Trainings", "id", trainingId));
		trainings.setProgress(progress);
		trainingsRepository.save(trainings);
	}
	
	/**
	 * Returns finalised trainings to check payment history
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/trainings/paymenthistory/")
	public List<Trainings> getAllInProgressTrainings()
	{
		return trainingsRepository.findAllInProgressTrainings();
	}
	
	/**
	 * Returns all trainings
	 * @return
	 */
	@GetMapping("/trainings/calendar")
    public List<Trainings> getTrainingsForCalendar() 
	{
        return trainingsRepository.findTrainingsForCalendar();
    }
}