package com.example.demo.table.master;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.table.BoardEntity;

public interface BoardRepositoryMaster extends JpaRepository<BoardEntity, Long> {

}
