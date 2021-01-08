package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.example.demo.util.db.DataSourceMConfig;
import com.example.demo.util.upload.FileUploadConfig;

@SpringBootApplication
@EnableAspectJAutoProxy // AOP
@EnableConfigurationProperties({
    FileUploadConfig.class
})// Enable Upload
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
