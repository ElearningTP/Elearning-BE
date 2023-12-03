package com.api.learning.ElearningBE.services;

import com.api.learning.ElearningBE.constant.ELearningConstant;
import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.UploadFileDto;
import com.api.learning.ElearningBE.form.UploadFileForm;
import com.api.learning.ElearningBE.security.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Objects;

@Service
@Slf4j
public class FileService {
    @Autowired
    private UserService userService;

    static final String[] UPLOAD_TYPES = new String[]{"LOGO", "AVATAR", "IMAGE", "DOCUMENT", "THUMBNAIL", "SUBMISSION_FILE"};

    public ApiMessageDto<UploadFileDto> storeFile(UploadFileForm uploadFileForm){
        ApiMessageDto<UploadFileDto> apiMessageDto = new ApiMessageDto<>();
        try {
            boolean matches = Arrays.stream(UPLOAD_TYPES).anyMatch(uploadFileForm.getType()::equalsIgnoreCase);
            if (!matches){
                apiMessageDto.setResult(false);
                apiMessageDto.setMessage("Type is required: LOGO, AVATAR, IMAGE, DOCUMENT, THUMBNAIL,SUBMISSION_FILE");
                return apiMessageDto;
            }
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(uploadFileForm.getFile().getOriginalFilename()));
            String ext = FilenameUtils.getExtension(fileName);
            //Upload
            String baseFileName = FilenameUtils.getBaseName(fileName);
            String finalFile = baseFileName + "_" + RandomStringUtils.randomAlphanumeric(10) + "." + ext;
            String typeFolder = File.separator + uploadFileForm.getType();

            Long accountId = userService.getAccountId();
            if (accountId != null){
                typeFolder = File.separator + accountId + typeFolder;
            }
            Path fileStorageLocation = Paths.get(ELearningConstant.PATH_DIRECTORY + typeFolder).toAbsolutePath().normalize();
            Files.createDirectories(fileStorageLocation);
            Path targetLocation = fileStorageLocation.resolve(finalFile);
            Files.copy(uploadFileForm.getFile().getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            UploadFileDto uploadFileDto = new UploadFileDto();
            uploadFileDto.setFilePath(typeFolder + File.separator + finalFile);

            apiMessageDto.setData(uploadFileDto);
            apiMessageDto.setMessage("Upload file successfully");
        }catch (IOException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            log.error("Occurred an error when uploading file: "+e.getMessage());
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("[Ex2]: "+e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            log.error("Occurred an error when uploading file: "+e.getMessage());
        }
        return apiMessageDto;
    }

    public Resource loadFileAsResource(String folder, String fileName){
        try {
            Path fileStorageLocation = Paths.get(ELearningConstant.PATH_DIRECTORY + File.separator + folder).toAbsolutePath().normalize();
            Path filePath = fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            }
        } catch (MalformedURLException ex) {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }

    public void deleteFile(String filePath){
        File file = new File(ELearningConstant.PATH_DIRECTORY + filePath);
        if (file.exists()){
            file.delete();
        }else{
            log.error(String.format("Can not find file with path: %s", filePath));
        }
    }
}
