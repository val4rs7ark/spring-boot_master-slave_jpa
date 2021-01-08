package com.example.demo.util.exception;

public class FileUploadException extends RuntimeException {

	private static final long serialVersionUID = -9127212617072561410L;

    public FileUploadException(String message) {
        super(message);
    }
    
    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }	
}
