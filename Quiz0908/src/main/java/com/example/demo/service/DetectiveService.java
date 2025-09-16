package com.example.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.DetectiveDto;
import com.example.demo.entity.Detective;
import com.example.demo.repository.DetectiveRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DetectiveService {

	private final DetectiveRepository detectiveRepository;
	
	private final PasswordEncoder passwordEncoder;

	public void createDog(DetectiveDto detectiveDto) {
		Detective dog = Detective.builder()
				.username(detectiveDto.getUsername())
				.password(passwordEncoder.encode(detectiveDto.getPassword()))
				.build();
		this.detectiveRepository.save(dog);
	}
	
	public void findByDogId(Long id) {
		this.detectiveRepository.findById(id);
	}
	
	
}
