package com.systemk.spyder.Controller.Api.Web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.systemk.spyder.Entity.Main.RfidTagReissueRequestDetail;
import com.systemk.spyder.Repository.Main.RfidTagReissueRequestDetailRepository;

@RestController
@RequestMapping("/rfidTagReissueRequest")
public class RfidTagReissueRequestController {

	@Autowired
	private RfidTagReissueRequestDetailRepository rfidTagReissueRequestDetailRepository;
	
	@RequestMapping(method = RequestMethod.GET, value="/{flag}/{seq}")
	public List<RfidTagReissueRequestDetail> findByBartagSeq(@PathVariable(value = "flag", required = true) String flag, 
													   @PathVariable(value = "seq", required = true) long seq) throws Exception {
		if(flag.equals("bartag")){
			return rfidTagReissueRequestDetailRepository.findByProductionStorageBartagMasterBartagSeq(seq);
		} else {
			return rfidTagReissueRequestDetailRepository.findByProductionStorageProductionStorageSeq(seq);
		}
	}
}
