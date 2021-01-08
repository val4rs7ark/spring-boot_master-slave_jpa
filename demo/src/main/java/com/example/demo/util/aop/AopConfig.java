package com.example.demo.util.aop;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AopConfig {

	private static final Logger logger = LoggerFactory.getLogger(AopConfig.class);
	
    @Before("execution(* com.example.demo.controller.*Controller.*(..))")
    public void doBefore() {
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    	logger.info("");
        logger.info("●● RequestURI  : "+request.getRequestURI());
        logger.info("●● QueryString : "+request.getQueryString());
    }

    @After("execution(* com.example.demo.controller.*Controller.*(..)) ")
    public void doAfter() {
    	ServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
    	HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    	httpServletResponse.setHeader("HeaderCustom", "Hello");
    }
}
