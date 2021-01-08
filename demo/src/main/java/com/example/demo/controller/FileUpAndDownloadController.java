package com.example.demo.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.service.FileUpAndDownloadService;
import com.example.demo.table.UploadFileEntity;
import com.example.demo.util.upload.FileUploadVO;

@RestController
public class FileUpAndDownloadController {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUpAndDownloadController.class);
	
    @Autowired
    private FileUpAndDownloadService service;
    
    @GetMapping("/getUploadFileList")
    public Iterable<UploadFileEntity> getUploadFileList(){
        return service.getFileList();
    }
    
    @GetMapping("/getUploadFile/{id}")
    public Optional<UploadFileEntity> getUploadFile(@PathVariable long id){
        return service.getUploadFile(id);	
    }
    
    
    @PostMapping("/uploadFile")
    public UploadFileEntity uploadFile(@RequestParam("file") MultipartFile file) {
    	
    	logger.info("uploadFile 시작!!");
    	
    	UploadFileEntity uploadFile = null;
		try {
			
			uploadFile = service.uploadFile(file);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
        
        return uploadFile;
    }
    
    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileEntity> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files){
    	return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request){
    	
    	
    	// Load file as Resource
        Resource resource = service.downloadResource(fileName);
 
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
 
        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
 
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

	
}
