package com.myorg.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myorg.exceptions.ResourceNotFoundException;
import com.myorg.models.Payments;
import com.myorg.repositories.PaymentsRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class PaymentsController
{
	@Autowired
	PaymentsRepository paymentsRepository;
	
	/**
	 * Returns transaction details by payment Id
	 * @param paymentId
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/payments/{id}")
    public Payments getPaymentById(@PathVariable(value = "id") Integer paymentId) 
    {
        return paymentsRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Technologies", "id", paymentId));
    }
	
	/**
	 * Creates a new payments row and updates training table to confirm booking
	 * @param trainings
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/payments")
    public Payments createPayments(@Valid @RequestBody Payments payments) 
	{	
		return paymentsRepository.save(payments);
    }
}