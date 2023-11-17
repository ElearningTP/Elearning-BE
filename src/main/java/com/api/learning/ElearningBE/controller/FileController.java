package com.api.learning.ElearningBE.controller;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.UploadFileDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.UploadFileForm;
import com.api.learning.ElearningBE.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.cache.annotation.Cacheable;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/file")
@Slf4j
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/download/{folder}/{fileName:.+}")
    @Cacheable("images")
    public ResponseEntity<Resource> download(@PathVariable String folder, @PathVariable String fileName, HttpServletRequest request){
        Resource  resource= fileService.loadFileAsResource(folder , fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type");
        }
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(7776000, TimeUnit.SECONDS))
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiMessageDto<UploadFileDto> upload(@Valid UploadFileForm uploadFileForm){
        ApiMessageDto<UploadFileDto> apiMessageDto = new ApiMessageDto<>();
        if (uploadFileForm.getFile() == null || uploadFileForm.getFile().isEmpty()){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("File is required");
            return apiMessageDto;
        }
        apiMessageDto = fileService.storeFile(uploadFileForm);
        return apiMessageDto;
    }

    @DeleteMapping("/delete")
    public ApiMessageDto<String> delete(@RequestParam String filePath){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try {
            fileService.deleteFile(filePath);
            apiMessageDto.setMessage("Delete file successfully");
        }catch (NotFoundException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.NOT_FOUND.toString());
        }
        catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }
}
