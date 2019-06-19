package com.myorg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myorg.models.Payments;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Integer>
{
	
}