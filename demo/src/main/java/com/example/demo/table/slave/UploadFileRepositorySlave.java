package com.example.demo.table.slave;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.table.UploadFileEntity;

public interface UploadFileRepositorySlave extends JpaRepository<UploadFileEntity, Long> {

}
