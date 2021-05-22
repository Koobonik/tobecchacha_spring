package com.spring.service;

import com.spring.dto.responseDto.DefaultResponseDto;
import com.spring.model.Education;
import com.spring.model.Gallery;
import com.spring.model.GalleryRepository;
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
public class GalleryService {

    private final GalleryRepository galleryRepository;

    public List<Gallery> findAllPageSize(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return galleryRepository.findAll(pageRequest).getContent();
    }
    public ResponseEntity<?> createGallery(Gallery gallery){
        Gallery gallery1 = galleryRepository.save(gallery);
        return new ResponseEntity<>(gallery1, HttpStatus.OK);
    }

    public ResponseEntity<?> getGallery(int id){
        Gallery gallery = galleryRepository.findById(id);
        if(gallery == null) return DefaultResponseDto.canNotFindBook();
        return new ResponseEntity<>(galleryRepository.findById(id), HttpStatus.OK);
    }
}
