package com.spring.service;

import com.spring.dto.responseDto.DefaultResponseDto;
import com.spring.model.Artists;
import com.spring.model.ArtistsRepository;
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
public class ArtistsService {

    private final ArtistsRepository artistsRepository;

    public List<Artists> findAllPageSize(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return artistsRepository.findAll(pageRequest).getContent();
    }
    public ResponseEntity<?> createArtists(Artists artists){
        Artists artists1 = artistsRepository.save(artists);
        return new ResponseEntity<>(artists1, HttpStatus.OK);
    }

    public ResponseEntity<?> getArtists(int id){
        Artists artists = artistsRepository.findById(id);
        if(artists == null) return DefaultResponseDto.canNotFindBook();
        return new ResponseEntity<>(artists, HttpStatus.OK);
    }
}
