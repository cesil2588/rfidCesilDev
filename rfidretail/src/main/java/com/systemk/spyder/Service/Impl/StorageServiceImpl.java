package com.systemk.spyder.Service.Impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.systemk.spyder.Service.StorageService;

@Service
public class StorageServiceImpl implements StorageService{
	
	private final Path rootLocation = Paths.get("d://upload");
	
	@Override
	public void store(MultipartFile file) {
		
		try {
			Path path = rootLocation.resolve(file.getOriginalFilename());
			boolean isExists = Files.exists(path);
			
			if(isExists){
				Files.delete(rootLocation.resolve(file.getOriginalFilename()));
				Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
			} else {
				Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
			}
        } catch (Exception e) {
        	e.printStackTrace();
        	throw new RuntimeException("FAIL!");
        }
	}

	@Override
	public Resource loadFile(String filename) {
		try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }else{
            	throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
        	throw new RuntimeException("FAIL!");
        }
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void init() {
		try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
		
	}
}
