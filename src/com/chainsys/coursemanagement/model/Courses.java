package com.chainsys.coursemanagement.model;

import java.time.LocalDateTime;

public class Courses {
	private int id;
	private String name;
	private int status;
	private LocalDateTime createdOn;
	private int createdBy;
	private LocalDateTime modifiedOn;
	private int modifiedBy;
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public int getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Courses [id=" + id + ", name=" + name + ", status=" + status
				+ ", createdOn=" + createdOn + ", createdBy=" + createdBy
				+ ", modifiedOn=" + modifiedOn + ", modifiedBy=" + modifiedBy
				+ "]";
	}	
}
