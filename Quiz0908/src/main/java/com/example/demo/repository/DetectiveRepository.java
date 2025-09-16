package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Detective;

public interface DetectiveRepository extends JpaRepository<Detective, Long>{

	Optional<Detective> findByUsername(String username);
	
}
