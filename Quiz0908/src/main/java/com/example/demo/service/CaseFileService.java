package com.example.demo.service;

import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.CaseFileDto;
import com.example.demo.entity.CaseFile;
import com.example.demo.entity.Detective;
import com.example.demo.repository.CaseFileRepository;
import com.example.demo.repository.DetectiveRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CaseFileService {

	private final CaseFileRepository caseFileRepository;
	
	private final DetectiveRepository detectiveRepository;
	
	
	@Value("${file.upload-dir}")
	private String uploadDir;
	
	
    private String createFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }
    
    
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
    
    private String getFullPath(String filename) { 
    	return uploadDir + filename; 
    }
    
    // 사건 생성
	@Transactional
	public void createCrime(CaseFileDto caseFileDto, MultipartFile file, String username) throws IOException {  
		String fileName = createFileName(file.getOriginalFilename());
		file.transferTo(new File(getFullPath(fileName)));
		Detective dog = this.detectiveRepository.findByUsername(username)
				.orElseThrow(() -> new IllegalArgumentException("탐지견을 찾을수 없습니다."));
		
		CaseFile Crime = CaseFile.builder()
				.caseName(caseFileDto.getCaseName())
				.description(caseFileDto.getDescription())
				.evidenceImageName(fileName)
				.detective(dog)
				.build();
		Crime.prePersist();
		this.caseFileRepository.save(Crime);
	}

	// 사건 조회
	public List<CaseFile> findAll() {

		return this.caseFileRepository.findAll();
	}

	// 사건 id 조회
	public CaseFile findById(Long id) {
			
		return this.caseFileRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("사건을 찾을 수 없습니다. id=" + id));
		
	}

	// 사건 수정
	@Transactional
	public void editCrime(CaseFileDto caseFileDto, Long id) {
		CaseFile edit = findById(id);
		edit.update(caseFileDto.getCaseName(), caseFileDto.getDescription());
	}
	
	// 사건 종료
	@Transactional
	public void closeCrime(Long id) {
		CaseFile close = findById(id);
		close.closeCase();
		this.caseFileRepository.save(close);

	}

	// 사건 담당 탐지견 이름 조회
	public List<CaseFile> findByDogname(String username) {
		// TODO Auto-generated method stub
		return caseFileRepository.findByDetectiveUsername(username);
	}
	
	
}
