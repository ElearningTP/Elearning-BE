package com.api.learning.ElearningBE.controller;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.UploadFileDto;
import com.api.learning.ElearningBE.form.UploadFileForm;
import com.api.learning.ElearningBE.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
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

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/download/{folder}/{fileName:.+}")
    @Cacheable("images")
    public ResponseEntity<Resource> download(@PathVariable String folder,
                                             @PathVariable String fileName, HttpServletRequest request){
        Resource resource = fileService.loadFileAsResource(folder , fileName);

        if (resource == null || !resource.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(new ByteArrayResource("An error occurred. The resource could not be loaded".getBytes()));
        }

        String contentType = getContentType(resource, request);

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        // Check if the content type is suitable for inline display
        boolean isInlineDisplayable = isInlineDisplayable(contentType);

        HttpHeaders headers = new HttpHeaders();
        if (isInlineDisplayable) {
            // If the content type is suitable for inline display, use inline instead of attachment
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"");
        } else {
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
        }

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(7776000, TimeUnit.SECONDS))
                .contentType(MediaType.parseMediaType(contentType))
                .headers(headers)
                .body(resource);
    }

    @GetMapping("/download/{accountId}/{folder}/{fileName:.+}")
    @Cacheable("images")
    public ResponseEntity<Resource> downloadWithAccount(@PathVariable Long accountId,
                                                        @PathVariable String folder,
                                                        @PathVariable String fileName,
                                                        HttpServletRequest request) {
        final String path = accountId.toString() + "/" + folder;
        Resource resource = fileService.loadFileAsResource(path, fileName);

        if (resource == null || !resource.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(new ByteArrayResource("An error occurred. The resource could not be loaded".getBytes()));
        }

        String contentType = getContentType(resource, request);

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        // Check if the content type is suitable for inline display
        boolean isInlineDisplayable = isInlineDisplayable(contentType);

        HttpHeaders headers = new HttpHeaders();
        if (isInlineDisplayable) {
            // If the content type is suitable for inline display, use inline instead of attachment
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"");
        } else {
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
        }

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(7776000, TimeUnit.SECONDS))
                .contentType(MediaType.parseMediaType(contentType))
                .headers(headers)
                .body(resource);
    }

    private String getContentType(Resource resource, HttpServletRequest request) {
        try {
            return request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type");
            return null;
        }
    }

    private boolean isInlineDisplayable(String contentType) {
        // Add any additional content types that you want to display inline
        return contentType.startsWith("image/") || contentType.startsWith("application/pdf");
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
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }
}
