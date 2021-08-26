package com.spring.controller;

import com.spring.dto.requestDto.EtcCreateRequestDto;
import com.spring.dto.responseDto.DefaultResponseDto;
import com.spring.service.EtcService;
import com.spring.service.FileStorageService;
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
@Api(value = "etc API", tags = "etc 카테고리 관련 api")
@RequestMapping("api/v1/etc")
public class EtcController {

    private final EtcService etcService;
    private final FileStorageService fileStorageService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "books를 반환 해줌.", response = DefaultResponseDto.class)
    })
    @ApiOperation(value = "책들을 반환해주는 api", notes = "")
    @GetMapping("/getEtc/{page}/{size}")
    public ResponseEntity<?> getBooks(
            @ApiParam(value = "page", required = true, example = "page") @PathVariable("page") int page,
            @ApiParam(value = "size", required = true, example = "size") @PathVariable("size") int size){

        return new ResponseEntity<>(etcService.findAllPageSize(page, size), HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "etcs를 반환 해줌.", response = DefaultResponseDto.class)
    })
    @ApiOperation(value = "etc대한 디테일을 반환해주는 api", notes = "")
    @GetMapping("/getEtcDetail/{id}")
    public ResponseEntity<?> getBookDetail(
            @ApiParam(value = "id", required = true, example = "id") @PathVariable("id") int id){
        return etcService.getEtc(id);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "etc를 잘 생성하면 객체를 반환 해줌.", response = DefaultResponseDto.class)
    })
    @ApiOperation(value = "etc 정보를 생성해주는 api", notes = "")
    @PostMapping("/createEtc")
    @RequestMapping(value = "/createEtc", method = {RequestMethod.POST})
    public ResponseEntity<?> createBook(@ModelAttribute EtcCreateRequestDto etcCreateRequestDto,
                                        @RequestParam(value = "images1") MultipartFile images1,
                                        @RequestParam(value = "images2", required = false) MultipartFile images2,
                                        @RequestParam(value = "images3", required = false) MultipartFile images3,
                                        @RequestParam(value = "images4", required = false) MultipartFile images4,
                                        @RequestParam(value = "images5", required = false) MultipartFile images5,
                                        @RequestParam(value = "images6", required = false) MultipartFile images6,
                                        @RequestParam(value = "images7", required = false) MultipartFile images7,
                                        @RequestParam(value = "images8", required = false) MultipartFile images8,
                                        @RequestParam(value = "images9", required = false) MultipartFile images9,
                                        @RequestParam(value = "images10", required = false) MultipartFile images10
    ){
        List<String> images_string = new ArrayList<>();
        if(images1 != null && !images1.isEmpty()){
            images_string.add(fileStorageService.saveFile(images1).getFileDownloadUri());
        }
        if(images2 != null && !images2.isEmpty()){
            images_string.add(fileStorageService.saveFile(images2).getFileDownloadUri());
        }
        if(images3 != null && !images3.isEmpty()){
            images_string.add(fileStorageService.saveFile(images3).getFileDownloadUri());
        }
        if(images4 != null && !images4.isEmpty()){
            images_string.add(fileStorageService.saveFile(images4).getFileDownloadUri());
        }
        if(images5 != null && !images5.isEmpty()){
            images_string.add(fileStorageService.saveFile(images5).getFileDownloadUri());
        }
        if(images6 != null && !images6.isEmpty()){
            images_string.add(fileStorageService.saveFile(images6).getFileDownloadUri());
        }
        if(images7 != null && !images7.isEmpty()){
            images_string.add(fileStorageService.saveFile(images7).getFileDownloadUri());
        }
        if(images8 != null && !images8.isEmpty()){
            images_string.add(fileStorageService.saveFile(images8).getFileDownloadUri());
        }
        if(images9 != null && !images9.isEmpty()){
            images_string.add(fileStorageService.saveFile(images9).getFileDownloadUri());
        }
        if(images10 != null && !images10.isEmpty()){
            images_string.add(fileStorageService.saveFile(images10).getFileDownloadUri());
        }
        return new ResponseEntity<>(etcService.createEtc(etcCreateRequestDto.toEntity(images_string)), HttpStatus.OK);
    }
}
