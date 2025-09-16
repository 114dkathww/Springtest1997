package com.example.demo.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.CaseFileDto;
import com.example.demo.entity.CaseFile;
import com.example.demo.entity.Detective;
import com.example.demo.service.CaseFileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CaseFileController {

	private final CaseFileService caseFileService;
	
	// 리스트 페이지
	@GetMapping("/home")
	public String basicPage() {
		
		return "/cases/home";
	}
	
	// 루트 페이지
	@GetMapping("/")
	public String rootPage() {
		
		return "/cases/home";
	}
	
	
	// 모든 사건 조회
	@GetMapping("/cases/archive")		// cases	
	public String archive(Model model) {
		List<CaseFile> list = this.caseFileService.findAll();
		model.addAttribute("cases", list);
		return "/cases/archive-list";
	}

	// 현재 로그인한 사용자의 사건조회
	@GetMapping("/cases/my-cases")		// cases	
	public String myCrime(Model model, Principal principal) {
		
		String dogName = principal.getName();
		
		List<CaseFile> myCrime = this.caseFileService.findByDogname(dogName);
		model.addAttribute("cases", myCrime);
		
		return "/cases/list";
	}
	
	// 사건 추가
	@GetMapping("/cases/new")
	public String createCrime(@ModelAttribute("caseFile") CaseFileDto caseFileDto) {	//caseFile
		
		return "/cases/new";
	}
	
	// 사건 추가 	//@PathVariable("id") Long id
	@PostMapping("/cases/new")
	public String createCrime(@Valid @ModelAttribute("caseFile")CaseFileDto caseFileDto, BindingResult bindingResult,
			@RequestParam("file") MultipartFile file, Principal principal) throws IOException {	//caseFile
		
		if(bindingResult.hasErrors()) {
			return "cases/new";
		}
		
		String username = principal.getName();
		this.caseFileService.createCrime(caseFileDto, file, username);		
		return "redirect:/cases/my-cases";
	}
	
	// 사건 수정
	@GetMapping("/cases/{id}/edit")
	public String editCrime(@PathVariable("id") Long id, Model model) {			// caseFile
		
		
		model.addAttribute("caseFile", this.caseFileService.findById(id));

		return "/cases/edit";
	}
	
	// 사건 수정
	@PostMapping("/cases/{id}/edit")
	public String editCrime(@PathVariable("id") Long id, Model model, CaseFileDto caseFileDto) {			// caseFile
		

		this.caseFileService.editCrime(caseFileDto, id);

		return "redirect:/cases/my-cases";
	}
	
	// 사건 종결
	@PostMapping("/cases/{id}/close")
	public String closeCrime(@PathVariable("id") Long id) {
		
		this.caseFileService.closeCrime(id);
		return "redirect:/cases/my-cases";
	}
	
}
