package com.spring.service;

import com.spring.dto.responseDto.DefaultResponseDto;
import com.spring.model.Books;
import com.spring.model.BooksRepository;
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
public class BooksService {

    private final BooksRepository booksRepository;

    public List<Books> findAllPageSize(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return booksRepository.findAllByIsShow(true,pageRequest);
    }
    public ResponseEntity<?> createBook(Books books){
        Books books1 = booksRepository.save(books);
        return new ResponseEntity<>(books1, HttpStatus.OK);
    }

    public ResponseEntity<?> getBook(int id){
        Books book = booksRepository.findById(id);
        if(book == null) return DefaultResponseDto.canNotFindBook();
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
}
