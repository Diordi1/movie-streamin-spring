package com.upload.pulo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.upload.pulo.video;

public interface videoservice {
	public video save(video vd1,MultipartFile mp);
	public video get(String videoId);
	public video getByTitle(String title);
	public List<video> getAll();
	
}
