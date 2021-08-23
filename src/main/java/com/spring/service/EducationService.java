package com.spring.service;

import com.spring.dto.responseDto.DefaultResponseDto;
import com.spring.model.Books;
import com.spring.model.Education;
import com.spring.model.EducationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class EducationService {

    private final EducationRepository educationRepository;

    public List<Education> findAllPageSize(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return educationRepository.findAllByIsShow(true,pageRequest);
    }
    public ResponseEntity<?> createEducation(Education education){
        Education books1 = educationRepository.save(education);
        return new ResponseEntity<>(books1, HttpStatus.OK);
    }

    public ResponseEntity<?> getEducation(int id){
        Education education = educationRepository.findById(id);
        if(education == null) return DefaultResponseDto.canNotFindBook();
        return new ResponseEntity<>(education, HttpStatus.OK);
    }
}
