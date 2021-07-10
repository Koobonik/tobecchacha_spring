package com.spring.service;

import com.spring.dto.responseDto.DefaultResponseDto;
import com.spring.model.Books;
import com.spring.model.MainNotice;
import com.spring.model.MainNoticeRepository;
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
public class MainNoticeService {

    private final MainNoticeRepository mainNoticeRepository;

    public List<MainNotice> findAllPageSize(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return mainNoticeRepository.findAll(pageRequest).getContent();
    }
    public ResponseEntity<?> createMainNotice(MainNotice mainNotice){
        MainNotice mainNotice1 = mainNoticeRepository.save(mainNotice);
        return new ResponseEntity<>(mainNotice1, HttpStatus.OK);
    }

    public ResponseEntity<?> getMainNotice(int id){
        MainNotice mainNotice = mainNoticeRepository.findById(id);
        if(mainNotice == null) return DefaultResponseDto.canNotFindBook();
        return new ResponseEntity<>(mainNotice, HttpStatus.OK);
    }
}
