package com.spring.controller;

import com.spring.dto.requestDto.GalleryCreateRequestDto;
import com.spring.dto.responseDto.DefaultResponseDto;
import com.spring.service.FileStorageService;
import com.spring.service.GalleryService;
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
@Api(value = "gallery API", tags = "gallery 카테고리 관련 api")
@RequestMapping("api/v1/gallery")
public class GalleryController {

    private final GalleryService galleryService;
    private final FileStorageService fileStorageService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "gallery를 반환 해줌.", response = DefaultResponseDto.class)
    })
    @ApiOperation(value = "갤러리들을 반환해주는 api", notes = "")
    @GetMapping("/getGallery/{page}/{size}")
    public ResponseEntity<?> getGallery(
            @ApiParam(value = "page", required = true, example = "page") @PathVariable("page") int page,
            @ApiParam(value = "size", required = true, example = "size") @PathVariable("size") int size){

        return new ResponseEntity<>(galleryService.findAllPageSize(page, size), HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "gallery를 반환 해줌.", response = DefaultResponseDto.class)
    })
    @ApiOperation(value = "갤러리에 대한 디테일을 반환해주는 api", notes = "")
    @GetMapping("/getGalleryDetail/{id}")
    public ResponseEntity<?> getGalleryDetail(
            @ApiParam(value = "id", required = true, example = "id") @PathVariable("id") int id){

        return galleryService.getGallery(id);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "갤러리를 잘 생성하면 객체를 반환 해줌.", response = DefaultResponseDto.class)
    })
    @ApiOperation(value = "갤러리 정보를 생성해주는 api", notes = "")
    @PostMapping("/createGallery")
    @RequestMapping(value = "/createGallery", method = {RequestMethod.POST})
    public ResponseEntity<?> createGallery(@ModelAttribute GalleryCreateRequestDto galleryCreateRequestDto,
                                        @RequestParam(value = "images1") MultipartFile images1,
                                        @RequestParam(value = "images2", required = false) MultipartFile images2,
                                        @RequestParam(value = "images3", required = false) MultipartFile images3,
                                        @RequestParam(value = "images4", required = false) MultipartFile images4,
                                        @RequestParam(value = "images5", required = false) MultipartFile images5,
                                        @RequestParam(value = "images6", required = false) MultipartFile images6,
                                        @RequestParam(value = "images7", required = false) MultipartFile images7,
                                        @RequestParam(value = "images8", required = false) MultipartFile images8,
                                        @RequestParam(value = "images9", required = false) MultipartFile images9,
                                        @RequestParam(value = "images10", required = false) MultipartFile images10,
                                           @RequestParam(value = "images11", required = false) MultipartFile images11,
                                           @RequestParam(value = "images12", required = false) MultipartFile images12,
                                           @RequestParam(value = "images13", required = false) MultipartFile images13,
                                           @RequestParam(value = "images14", required = false) MultipartFile images14,
                                           @RequestParam(value = "images15", required = false) MultipartFile images15,
                                           @RequestParam(value = "images16", required = false) MultipartFile images16,
                                           @RequestParam(value = "images17", required = false) MultipartFile images17,
                                           @RequestParam(value = "images18", required = false) MultipartFile images18,
                                           @RequestParam(value = "images19", required = false) MultipartFile images19,
                                           @RequestParam(value = "images20", required = false) MultipartFile images20
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
        if(images11 != null && !images11.isEmpty()){
            images_string.add(fileStorageService.saveFile(images11).getFileDownloadUri());
        }
        if(images12 != null && !images12.isEmpty()){
            images_string.add(fileStorageService.saveFile(images12).getFileDownloadUri());
        }
        if(images13 != null && !images13.isEmpty()){
            images_string.add(fileStorageService.saveFile(images13).getFileDownloadUri());
        }
        if(images14 != null && !images14.isEmpty()){
            images_string.add(fileStorageService.saveFile(images14).getFileDownloadUri());
        }
        if(images15 != null && !images15.isEmpty()){
            images_string.add(fileStorageService.saveFile(images15).getFileDownloadUri());
        }
        if(images16 != null && !images16.isEmpty()){
            images_string.add(fileStorageService.saveFile(images16).getFileDownloadUri());
        }
        if(images17 != null && !images17.isEmpty()){
            images_string.add(fileStorageService.saveFile(images17).getFileDownloadUri());
        }
        if(images18 != null && !images18.isEmpty()){
            images_string.add(fileStorageService.saveFile(images18).getFileDownloadUri());
        }
        if(images19 != null && !images19.isEmpty()){
            images_string.add(fileStorageService.saveFile(images19).getFileDownloadUri());
        }
        if(images20 != null && !images20.isEmpty()){
            images_string.add(fileStorageService.saveFile(images20).getFileDownloadUri());
        }
        return new ResponseEntity<>(galleryService.createGallery(galleryCreateRequestDto.toEntity(images_string)), HttpStatus.OK);
    }
}
