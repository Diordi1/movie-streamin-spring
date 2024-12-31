package com.upload.pulo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
@Component
public interface videodao extends JpaRepository<video, String>{

}
