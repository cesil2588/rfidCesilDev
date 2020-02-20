package com.systemk.spyder.Controller.Api.Web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.systemk.spyder.Entity.External.RfidAc18If;
import com.systemk.spyder.Repository.External.RfidAc18IfRepository;

@RestController
@RequestMapping("/external")
public class ExternalController {

	private RfidAc18IfRepository rfidAc18IfRepository;
	
	@Autowired
	public ExternalController(RfidAc18IfRepository rfidAc18IfRepository){
		this.rfidAc18IfRepository = rfidAc18IfRepository;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<RfidAc18If> getExternalListAll() throws Exception {
		return rfidAc18IfRepository.findAll();
	}
}
