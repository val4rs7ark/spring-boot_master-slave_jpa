package com.example.demo.table;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "board")
public class BoardEntity implements Serializable {

	private static final long serialVersionUID = 5673391639727988082L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long board_id;
	
	@Column(length = 100)
	private String title;

	@Column(length = 4000)
	private String contents;

	public Long getBoardId() {
		return board_id;
	}

	public void setBoardId(Long board_id) {
		this.board_id = board_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
	
}
