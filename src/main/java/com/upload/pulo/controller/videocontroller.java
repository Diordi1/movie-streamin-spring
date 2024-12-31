package com.upload.pulo.controller;

//import java.awt.PageAttributes.MediaType;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
//import java.net.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.tomcat.util.file.ConfigurationSource.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.upload.pulo.video;
import com.upload.pulo.impl.videoimpl;
import com.upload.pulo.service.videoservice;

@RestController
public class videocontroller {
	@Autowired
	videoimpl vi;
	
	@PostMapping("/upload/video")
	public ResponseEntity<?> uploda(@RequestParam MultipartFile file,@RequestParam String title,@RequestParam String description){
		video v1=new video();
		v1.setTitle(title);
		v1.setContentType(file.getContentType());
//		v1.setTitle(title)
		v1.setDescription(description);
		
		v1.setVideoId("text1");
		;
		if(vi.save(v1, file)!=null) {
			return new ResponseEntity<>("uploaded",HttpStatus.OK);
		}
		return new ResponseEntity<>("not uploaded",HttpStatus.BAD_REQUEST);
		
	}
	@GetMapping("/video/stream/{videoID}")
	public  ResponseEntity<?> getVideo(@PathVariable String videoID){
		video v1=vi.get(videoID);
		String filePath=v1.getFilePath();
		FileSystemResource resource=new FileSystemResource(filePath);
		return new ResponseEntity<>(resource,HttpStatus.OK);
		
	}
	@GetMapping("/video/all")
	public ResponseEntity<?> getAll(){
		return new ResponseEntity<>(vi.getAll(),HttpStatus.OK);
		
		
	}
	@GetMapping("/video/range/{videID}")
	public ResponseEntity<?> rangeShift(@PathVariable String videID,@RequestHeader(value="range",required=false) String range){
		System.out.print(range);
		
		 System.out.println(range);
	        //

	        video video = vi.get(videID);
	        Path path = Paths.get(video.getFilePath());

	        FileSystemResource resource = new FileSystemResource(path);

	        String contentType = video.getContentType();

	        if (contentType == null) {
	            contentType = "application/octet-stream";

	        }

	        //file ki length
	        long fileLength = path.toFile().length();


	        //pahle jaisa hi code hai kyuki range header null
	        if (range == null) {
	            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
	        }

	        //calculating start and end range

	        long rangeStart;

	        long rangeEnd;

	        String[] ranges = range.replace("bytes=", "").split("-");
	        rangeStart = Long.parseLong(ranges[0]);

//	        rangeEnd = rangeStart + AppConstants.CHUNK_SIZE - 1;
//
//	        if (rangeEnd >= fileLength) {
//	            rangeEnd = fileLength - 1;
//	        }
	        
	        rangeEnd=rangeStart+AppConstants.CHUNK_SIZE-1;
	        if(rangeEnd>=fileLength) {
	        	rangeEnd=fileLength-1;
	        	
	        }

//	        if (ranges.length > 1) {
//	            rangeEnd = Long.parseLong(ranges[1]);
//	        } else {
//	            rangeEnd = fileLength - 1;
//	        }
//	
//	        if (rangeEnd > fileLength - 1) {
//	            rangeEnd = fileLength - 1;
//	        }


	        System.out.println("range start : " + rangeStart);
	        System.out.println("range end : " + rangeEnd);
	        InputStream inputStream;

	        try {

	            inputStream = Files.newInputStream(path);
	            inputStream.skip(rangeStart);
	            long contentLength = rangeEnd - rangeStart + 1;

	            byte data[]=new byte[(int)contentLength];
	            int read=inputStream.read(data, 0, data.length);
	            
//	            byte[] data = new byte[(int) contentLength];
//	            int read = inputStream.read(data, 0, data.length);
	            System.out.println("read(number of bytes) : " + read);

	            HttpHeaders headers = new HttpHeaders();
	            headers.add("Content-Range", "bytes " + rangeStart + "-" + rangeEnd + "/" + fileLength);
	            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	            headers.add("Pragma", "no-cache");
	            headers.add("Expires", "0");
	            headers.add("X-Content-Type-Options", "nosniff");
	            headers.setContentLength(contentLength);

	            return ResponseEntity
	                    .status(HttpStatus.PARTIAL_CONTENT)
	                    .headers(headers)
	                    .contentType(MediaType.parseMediaType(contentType))
	                    .body(new ByteArrayResource(data));


	        } catch (IOException ex) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }

		
		
		
		
	}
	
}
class AppConstants {
    public static final int CHUNK_SIZE=1024*1024;//1MB
}