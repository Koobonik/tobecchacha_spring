package com.spring.service;

import com.spring.dto.responseDto.DefaultResponseDto;
import com.spring.model.News;
import com.spring.model.NewsRepository;
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
public class NewsService {

    private final NewsRepository newsRepository;

    public List<News> findAllPageSize(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return newsRepository.findAllByIsShow(true,pageRequest);
    }
    public ResponseEntity<?> createNews(News news){
        News news1 = newsRepository.save(news);
        return new ResponseEntity<>(news1, HttpStatus.OK);
    }

    public ResponseEntity<?> getNews(int id){
        News news = newsRepository.findById(id);
        if(news == null) return DefaultResponseDto.canNotFindBook();
        return new ResponseEntity<>(news, HttpStatus.OK);
    }
}
