package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.DetectiveDto;
import com.example.demo.service.CaseFileService;
import com.example.demo.service.DetectiveService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DetectiveController {

	private final DetectiveService detectiveService;
	
	@GetMapping("/users/login")
	public String loginDog() {
		
		return "/users/login";
	}
	
	
	@GetMapping("users/signup")
	public String createDog(@ModelAttribute("user") DetectiveDto detectiveDto) {		// user 객체
		
		
		return "/users/signup";
	}
	
	@PostMapping("users/signup")
	public String createDog(DetectiveDto detectiveDto, BindingResult bindingResult) {		// user 객체
		if(bindingResult.hasErrors()) {
			return "users/signup";
		}
		this.detectiveService.createDog(detectiveDto);
		return "redirect:/home";
	}
	
	
	
}
