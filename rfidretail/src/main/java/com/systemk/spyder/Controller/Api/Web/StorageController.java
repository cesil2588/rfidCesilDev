package com.systemk.spyder.Controller.Api.Web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.systemk.spyder.Service.StorageService;


@RestController
@RequestMapping("/storage")
public class StorageController {
	
	private final Path rootLocation = Paths.get("d://upload");
	
	private StorageService storageService;
	
	@Autowired
	public StorageController(StorageService storageService){
		this.storageService = storageService;
	}

	List<String> files = new ArrayList<String>();
	
	@RequestMapping(method = RequestMethod.GET)
	public List<String> getListFiles() {
		List<String> lstFiles = new ArrayList<String>();
		try {
			lstFiles = files.stream()
					.map(fileName -> MvcUriComponentsBuilder
							.fromMethodName(StorageController.class, "getFile", fileName).build().toString())
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw e;
		}
	return lstFiles;
	}
	
	/**
	 * 일반 파일 다운로드
	 * @param filename
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/{filename:.+}", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> getFile(@PathVariable String filename, HttpServletRequest request) throws Exception{
		
		Resource tempFile = storageService.loadFile(filename);
		
		String docName = new String(tempFile.getFilename().getBytes("UTF-8"), "ISO-8859-1");
		
        String contentType = request.getServletContext().getMimeType(tempFile.getFile().getAbsolutePath());

        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        
        File file = tempFile.getFile();
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + docName + "\"")
				.contentType(MediaType.parseMediaType(contentType)).contentLength(file.length())
				.body(resource);
	}
	
	/**
	 * AT911N용 웹브라우저 파일 다운로드
	 * @param filename
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/app/{filename:.+}", method = RequestMethod.GET)
	public StreamingResponseBody getAppFile(@PathVariable String filename, HttpServletResponse response, Device device) throws Exception{
		
		Resource tempFile = storageService.loadFile(filename);
		
		String docName = new String(tempFile.getFilename().getBytes("UTF-8"), "ISO-8859-1");
		
		if(!device.getDevicePlatform().toString().equals("ANDROID")) {
			response.setContentType("application/octet-stream"); 
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
		}
        
        File file = tempFile.getFile();
        InputStream inputStream = new FileInputStream(file);
        
        return outputStream -> {
        	
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                outputStream.write(data, 0, nRead);
            }
            response.flushBuffer();
            inputStream.close();
        };
	}
	
	@RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> uploadFileMulti(@RequestParam("uploadfile") MultipartFile file) throws Exception {
		
		Map<String, Object> obj = new HashMap<String, Object>();
		
		String fileName = "";
    	try {
			storageService.store(file);
			fileName = file.getOriginalFilename();
			
			obj.put("fileName", fileName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return obj; 
    }
	
	
}