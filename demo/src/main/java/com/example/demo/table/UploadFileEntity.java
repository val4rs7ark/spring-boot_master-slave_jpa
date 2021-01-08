package com.example.demo.table;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
 
import org.hibernate.annotations.CreationTimestamp;
 
@Entity
@Table(name="upload_file")
public class UploadFileEntity {
 
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    
    @Column(name="file_name")
    private String fileName;
    
    @Column(name="size")
    private long size;
    
    @Column(name="mime_type")
    private String mimeType;
    
    @Column(name="down_link")
    private String downLink;
    
    @CreationTimestamp    // 입력시 시간 정보를 자동으로 입력해는 어노테이션.
    @Column(name="insert_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertDate;
 
    public UploadFileEntity() {
    }
    
 
    public UploadFileEntity(String fileName, long size, String mimeType, String downLink) {
        this.fileName = fileName;
        this.size = size;
        this.mimeType = mimeType;
        this.downLink = downLink;
    }
 
    public long getId() {
        return id;
    }
 
    public void setId(long id) {
        this.id = id;
    }
 
    public String getFileName() {
        return fileName;
    }
 
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
 
    public long getSize() {
        return size;
    }
 
    public void setSize(long size) {
        this.size = size;
    }
 
    public String getMimeType() {
        return mimeType;
    }
 
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
 
    public Date getInsertDate() {
        return insertDate;
    }
 
    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }
 
    @Override
    public String toString() {
        return "UploadFile [id=" + id + ", fileName=" + fileName + ", size=" + size + ", mimeType=" + mimeType
                + ", insertDate=" + insertDate + "]";
    }


	public String getDownLink() {
		return downLink;
	}


	public void setDownLink(String downLink) {
		this.downLink = downLink;
	}
}
