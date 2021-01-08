package com.example.demo.service;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.table.UploadFileEntity;
import com.example.demo.table.master.UploadFileRepositoryMaster;
import com.example.demo.table.slave.UploadFileRepositorySlave;
import com.example.demo.util.exception.FileDownloadException;
import com.example.demo.util.upload.FileUploadConfig;

@Service
public class FileUpAndDownloadService {
	
	@Autowired
	private UploadFileRepositoryMaster uploadFileRepositoryM;//master db
	
	@Autowired
	private UploadFileRepositorySlave uploadFileRepositoryS;//slvae db

	private final Path fileLocation;
	
	@Autowired
	public FileUpAndDownloadService(FileUploadConfig config)  throws FileUploadException{//생성자
		this.fileLocation = Paths.get(config.getUploadDir())
				.toAbsolutePath().normalize();
		
		try {
			Files.createDirectories(this.fileLocation);
		} catch (Exception e) {
			throw new FileUploadException("파일을 업로드할 디렉토리를 생성하지 못했습니다.", e);
		}
	}
	
   public Iterable<UploadFileEntity> getFileList(){//업로드된 모든 파일 조회
        Iterable<UploadFileEntity> iterable = uploadFileRepositoryS.findAll();
        
        if(null == iterable) {
            throw new FileDownloadException("업로드 된 파일이 존재하지 않습니다.");
        }
        
        return  iterable;
    }
    
    public Optional<UploadFileEntity> getUploadFile(long id) {//pk로 업로드 파일 조회
        Optional<UploadFileEntity> uploadFile = uploadFileRepositoryS.findById(id);
        
        if(null == uploadFile) {
            throw new FileDownloadException("해당 아이디["+id+"]로 업로드 된 파일이 존재하지 않습니다.");
        }
        return uploadFile;
    }
	
	public UploadFileEntity uploadFile(MultipartFile file) throws FileUploadException {//파일 업로드
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		try {
			
			if(fileName.contains("..")) throw new FileUploadException("파일명에 부적합 문자가 포함되어 있습니다. " + fileName);
			
			Path targetLocation = this.fileLocation.resolve(fileName);
            
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(fileName)
                    .toUriString();
            
            UploadFileEntity uploadFile = new UploadFileEntity(fileName, file.getSize(), file.getContentType(), fileDownloadUri);
            uploadFileRepositoryM.save(uploadFile);
            
            return uploadFile;
        }catch(Exception e) {
            throw new FileUploadException("["+fileName+"] 파일 업로드에 실패하였습니다. 다시 시도하십시오.",e);
        }
	}
	
	public Resource downloadResource(String fileName) {//파일 다운로드
		
        try {
        	
            Path filePath = this.fileLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            
            if(resource.exists()) {
                return resource;
            }else {
                throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.");
            }
        }catch(MalformedURLException e) {
            throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.", e);
        }
    }
}
