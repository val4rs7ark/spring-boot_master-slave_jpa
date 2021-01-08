package com.example.demo.util.exception;

public class FileDownloadException extends RuntimeException {

	private static final long serialVersionUID = 6267685941137231616L;

	public FileDownloadException(String message) {
        super(message);
    }
    
    public FileDownloadException(String message, Throwable cause) {
        super(message, cause);
    }	
}
