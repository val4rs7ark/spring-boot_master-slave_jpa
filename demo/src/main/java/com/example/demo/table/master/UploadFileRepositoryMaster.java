package com.example.demo.table.master;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.table.UploadFileEntity;

public interface UploadFileRepositoryMaster extends JpaRepository<UploadFileEntity, Long> {

}
