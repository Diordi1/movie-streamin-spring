package com.upload.pulo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class video {
	
	@Id
	String videoId;
	
	String title;
	String description;
	String contentType;
	String filePath;
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public video(String videoId, String title, String description, String contentType, String filePath) {
		super();
		this.videoId = videoId;
		this.title = title;
		this.description = description;
		this.contentType = contentType;
		this.filePath = filePath;
	}
	
	public video() {
		
	}
}