package com.upload.pulo.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.upload.pulo.video;
import com.upload.pulo.videodao;
import com.upload.pulo.service.videoservice;

import jakarta.annotation.PostConstruct;

@Component
public class videoimpl implements videoservice{
	@Value("${file.video}")
	String dir;
	@PostConstruct
	public void init() {
		File file=new File(dir);
		if(file.exists()==false) {
				file.mkdir();
				System.out.println("created");
		}else {
			System.out.println("already");
		}
	}
	@Autowired
	videodao vd;
	
	
	@Override
	public video save(video vd1, MultipartFile mp)  {
		// TODO Auto-generated method stub
		String fileName=mp.getOriginalFilename();
		String contentType=mp.getContentType();
		Path path=Paths.get(dir,fileName);
		try {
			
			InputStream is=mp.getInputStream();
			Files.copy(is,path, StandardCopyOption.REPLACE_EXISTING);
			vd1.setContentType(contentType);
			vd1.setFilePath(path.toString());
			vd.save(vd1);
			return vd1;
			
		}catch(Exception e) {
			System.out.println(e);
		}
		
//		System.out.println(path);
		
		return null;
	}

	@Override
	public video get(String videoId) {
		// TODO Auto-generated method stub
		video temp=vd.findById(videoId).get();
		
		
		return temp;
	}

	@Override
	public video getByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<video> getAll() {
		// TODO Auto-generated method stub
		return vd.findAll();
	}

}
