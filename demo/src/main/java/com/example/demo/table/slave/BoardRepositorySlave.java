package com.example.demo.table.slave;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.table.BoardEntity;

public interface BoardRepositorySlave extends JpaRepository<BoardEntity, Long> {

}
