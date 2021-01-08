package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.table.BoardEntity;
import com.example.demo.table.master.BoardRepositoryMaster;
import com.example.demo.table.slave.BoardRepositorySlave;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepositoryMaster boardRepositoryM;//master db
	
	@Autowired
	private BoardRepositorySlave boardRepositoryS;//slvae db
	
	
	@Transactional(readOnly=true)
	public BoardEntity getBoard(Long boardId) throws Exception {
		return boardRepositoryS.findById(boardId)
                .orElseGet(BoardEntity::new);
	}
	
	@Transactional(readOnly=false)
	public void insertBoard(BoardEntity board) throws Exception {
		boardRepositoryM.save(board);
	}
}
