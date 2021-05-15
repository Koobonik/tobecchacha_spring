package com.spring.controller;

import com.spring.dto.requestDto.BookCreateRequestDto;
import com.spring.dto.responseDto.DefaultResponseDto;
import com.spring.service.BooksService;
import com.spring.service.FileStorageService;
import com.spring.util.jwt.JwtTokenProvider;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@Api(value = "books API", tags = "books 카테고리 관련 api")
@RequestMapping("api/v1/books")
public class BooksController {

    private final JwtTokenProvider jwtTokenProvider;
    private final BooksService booksService;
    private final FileStorageService fileStorageService;
    @ApiOperation(value = "HTTP GET EXAMPLE", notes = "GET 요청에 대한 예제 입니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버에러"),
            @ApiResponse(code = 404, message = "찾을 수 없음")
    })
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody String main(@ApiParam(value = "테스트 파라미터_1", required = true, example = "test_parameter_1") @RequestParam String test1,
                                     @ApiParam(value = "테스트 파라미터_2", required = true, example = "test_parameter_2") @RequestParam String test2) {
        return test1 + " : " + test2;
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "books를 반환 해줌.", response = DefaultResponseDto.class)
    })
    @ApiOperation(value = "책들을 반환해주는 api", notes = "")
    @GetMapping("/getBooks/{page}/{size}")
    public ResponseEntity<?> sendEmailForAuthEmail(
            @ApiParam(value = "page", required = true, example = "page") @PathVariable("page") int page,
            @ApiParam(value = "size", required = true, example = "size") @PathVariable("size") int size){

        return new ResponseEntity<>(booksService.findAllPageSize(page, size), HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "책을 잘 생성하면 객체를 반환 해줌.", response = DefaultResponseDto.class)
    })
    @ApiOperation(value = "책 정보를 생성해주는 api", notes = "")
    @PostMapping("/getBooks/createBook")
    @RequestMapping(value = "/getBooks/createBook", method = {RequestMethod.POST})
    public ResponseEntity<?> createBook(@ModelAttribute BookCreateRequestDto bookCreateRequestDto,
                                        @RequestParam(value = "images1") MultipartFile images1
//                                        @RequestParam(value = "image2", required = false) MultipartFile image2,
//                                        @RequestParam(value = "image3", required = false) MultipartFile image3
    ){
        List<String> images_string = new ArrayList<>();
        if(!images1.isEmpty()){
            images_string.add(fileStorageService.saveFile(images1).getFileDownloadUri());
        }
        return new ResponseEntity<>(booksService.createBook(bookCreateRequestDto.toEntity(images_string)), HttpStatus.OK);
    }
}
