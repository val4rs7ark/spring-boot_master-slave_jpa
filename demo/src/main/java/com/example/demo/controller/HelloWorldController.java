package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.BoardService;
import com.example.demo.table.BoardEntity;

@RestController
public class HelloWorldController {

	private static final Logger logger = LoggerFactory.getLogger(HelloWorldController.class);
	
	@Autowired
	BoardService boardService;
	
	@RequestMapping("/hello")
	public String hello() {
		try {
			Long id = (long) 1;
			
			BoardEntity board = boardService.getBoard(id);
			BoardEntity insert = new BoardEntity();
			insert.setTitle("insertTest");
			insert.setContents("insertTest");
			boardService.insertBoard(insert);
			if(board != null) {
				
				logger.info(board.getContents());
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return "hello World!";
	}
	
}
