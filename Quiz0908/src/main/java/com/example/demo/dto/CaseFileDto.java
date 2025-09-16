package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.entity.CaseStatus;
import com.example.demo.entity.Detective;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaseFileDto {

	
    private String caseName;

    private String description;

    private String evidenceImageName;

    private LocalDate reportedDate;

    private CaseStatus status;

    private Detective detective;

}
