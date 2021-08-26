package com.spring.controller;

import com.spring.dto.requestDto.ArtistsCreateRequestDto;
import com.spring.dto.requestDto.MainNoticeCreateRequestDto;
import com.spring.dto.responseDto.CurrentIdsResponseDto;
import com.spring.dto.responseDto.DefaultResponseDto;
import com.spring.model.Gallery;
import com.spring.service.*;
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
@Api(value = "notice API", tags = "공지 관련 api")
@RequestMapping("api/v1/mainNotice")
public class MainNoticeController {

    private final MainNoticeService mainNoticeService;
    private final FileStorageService fileStorageService;

    private final BooksService booksService;
    private final GalleryService galleryService;
    private final EducationService educationService;
    private final EtcService etcService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "공지 반환 해줌.", response = DefaultResponseDto.class)
    })
    @ApiOperation(value = "공지를 반환해주는 api", notes = "")
    @GetMapping("/getMainNotice/{page}/{size}")
    public ResponseEntity<?> getMainNotice(
            @ApiParam(value = "page", required = true, example = "page") @PathVariable("page") int page,
            @ApiParam(value = "size", required = true, example = "size") @PathVariable("size") int size){
        return new ResponseEntity<>(mainNoticeService.findAllPageSize(page, size), HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "공지 디테일을 반환 해줌.", response = DefaultResponseDto.class)
    })
    @ApiOperation(value = "공지에 대한 디테일을 반환해주는 api", notes = "")
    @GetMapping("/getMainNoticeDetail/{id}")
    public ResponseEntity<?> getMainNoticeDetail(
            @ApiParam(value = "id", required = true, example = "id") @PathVariable("id") int id){
        return mainNoticeService.getMainNotice(id);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "공지를 잘 생성하면 객체를 반환 해줌.", response = DefaultResponseDto.class)
    })
    @ApiOperation(value = "공지 정보를 생성해주는 api", notes = "")
    @PostMapping("/createMainNotice")
    @RequestMapping(value = "/createMainNotice", method = {RequestMethod.POST})
    public ResponseEntity<?> createMainNotice(@ModelAttribute MainNoticeCreateRequestDto mainNoticeCreateRequestDto,
                                        @RequestParam(value = "image") MultipartFile image
    ){
        String imageUrl = "";
        if(image != null && !image.isEmpty()){
            imageUrl = fileStorageService.saveFile(image).getFileDownloadUri();
        }
        return new ResponseEntity<>(mainNoticeService.createMainNotice(mainNoticeCreateRequestDto.toEntity(imageUrl)), HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "각 게시물의 최근 아이디들을 반환해줌.", response = DefaultResponseDto.class)
    })
    @ApiOperation(value = "각 게시물의 최근 아이디들을 반환해주는 api", notes = "")
    @GetMapping("/getCurrentIds")
    public ResponseEntity<?> getCurrentIds(){
        CurrentIdsResponseDto currentIdsResponseDto = CurrentIdsResponseDto.builder()
                .bookId(booksService.findAllPageSize(0,1).get(0).getId())
                .educationId(educationService.findAllPageSize(0,1).get(0).getId())
                .galleryId(galleryService.findAllPageSize(0,1).get(0).getId())
                .etcId(etcService.findAllPageSize(0,1).get(0).getId())
                .build();
        return new ResponseEntity<>(currentIdsResponseDto, HttpStatus.OK);
    }
}
