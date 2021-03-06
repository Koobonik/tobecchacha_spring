package com.spring.service;

import com.spring.dto.responseDto.DefaultResponseDto;
import com.spring.model.Etc;
import com.spring.model.EtcRepository;
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
public class EtcService {

    private final EtcRepository etcRepository;

    public List<Etc> findAllPageSize(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return etcRepository.findAllByIsShow(true,pageRequest);
    }
    public ResponseEntity<?> createEtc(Etc etc){
        Etc etc1 = etcRepository.save(etc);
        return new ResponseEntity<>(etc1, HttpStatus.OK);
    }

    public ResponseEntity<?> getEtc(int id){
        Etc etc = etcRepository.findById(id);
        if(etc == null) return DefaultResponseDto.canNotFindBook();
        return new ResponseEntity<>(etc, HttpStatus.OK);
    }
}
