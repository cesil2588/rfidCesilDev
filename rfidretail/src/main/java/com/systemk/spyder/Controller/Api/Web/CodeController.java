package com.systemk.spyder.Controller.Api.Web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.systemk.spyder.Entity.Main.CodeInfo;
import com.systemk.spyder.Entity.Main.ParentCodeInfo;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.ParentCodeInfoRepository;
import com.systemk.spyder.Security.LoginUser;
import com.systemk.spyder.Service.CodeService;
import com.systemk.spyder.Service.ParentCodeService;
import com.systemk.spyder.Service.RedisService;
import com.systemk.spyder.Util.SecurityUtil;
import com.systemk.spyder.Util.StringUtil;

@RestController
@RequestMapping("/code")
public class CodeController {
	
	private ParentCodeInfoRepository parentCodeInfoRepository;
	
	private ParentCodeService parentCodeService;
	
	private CodeService codeService;
	
	private RedisService redisService;
	
	@Autowired
	public CodeController(ParentCodeInfoRepository parentCodeInfoRepository,
						  ParentCodeService parentCodeService,
						  CodeService codeService,
						  RedisService redisService){
		this.parentCodeInfoRepository = parentCodeInfoRepository;
		this.parentCodeService = parentCodeService;
		this.codeService = codeService;
		this.redisService = redisService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<ParentCodeInfo> getCodeListAll() throws Exception {
		return parentCodeInfoRepository.findAllByOrderBySortAsc();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/findAll")
	public String findAll() throws Exception {
		return parentCodeService.findAll();
	}

	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ParentCodeInfo> addParentCode(@RequestBody ParentCodeInfo parentInfo) throws Exception {
		
		ParentCodeInfo tempCode = parentCodeInfoRepository.findByCodeValue(parentInfo.getCodeValue());
		if(tempCode != null){
			return new ResponseEntity<ParentCodeInfo>(parentInfo, HttpStatus.CONFLICT);
		}
		LoginUser user = SecurityUtil.getCustomUser();
		if(user != null){
			parentInfo.setRegUserSeq(user.getUserSeq());
		}
		parentInfo.setRegDate(new Date());
        return new ResponseEntity<ParentCodeInfo>(parentCodeService.save(parentInfo), HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<ParentCodeInfo> modParentCode(@RequestBody ParentCodeInfo parentInfo) throws Exception {
		
		ParentCodeInfo tempCode = parentCodeInfoRepository.findByCodeValue(parentInfo.getCodeValue());
		
		if(tempCode != null &&
		   parentInfo.getCodeValue().equals(tempCode.getCodeValue()) && 
		   parentInfo.getParentCodeSeq() != tempCode.getParentCodeSeq()){
			return new ResponseEntity<ParentCodeInfo>(parentInfo, HttpStatus.CONFLICT);
		}
		
		LoginUser user = SecurityUtil.getCustomUser();
		if(user != null){
			parentInfo.setUpdUserSeq(user.getUserSeq());
		}
		
		parentCodeService.update(parentInfo);
		
        return new ResponseEntity<ParentCodeInfo>(parentInfo, HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<ParentCodeInfo> delParentCode(@RequestBody ParentCodeInfo parentInfo) throws Exception {
		parentCodeService.delete(parentInfo);
        return new ResponseEntity<ParentCodeInfo>(parentInfo, HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.POST, value = "/child")
    public ResponseEntity<CodeInfo> addChildCode(@RequestBody CodeInfo codeInfo) throws Exception {
		LoginUser user = SecurityUtil.getCustomUser();
		if(user != null){
			codeInfo.setRegUserSeq(user.getUserSeq());
		}
		codeInfo.setRegDate(new Date());
		
		CodeInfo returnCodeInfo = codeService.save(codeInfo);
		
		ParentCodeInfo parentCodeInfo = parentCodeInfoRepository.findOne(codeInfo.getParentCodeInfo().getParentCodeSeq());
		redisService.save("codeList", parentCodeInfo.getParentCodeSeq(), StringUtil.convertJsonString(parentCodeInfo));
		
        return new ResponseEntity<CodeInfo>(returnCodeInfo, HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.PUT, value = "/child")
    public ResponseEntity<CodeInfo> modChildCode(@RequestBody CodeInfo codeInfo) throws Exception {
		LoginUser user = SecurityUtil.getCustomUser();
		if(user != null){
			codeInfo.setUpdUserSeq(user.getUserSeq());
		}
		codeInfo.setUpdDate(new Date());
		
		CodeInfo returnCodeInfo = codeService.save(codeInfo);
		
		ParentCodeInfo parentCodeInfo = parentCodeInfoRepository.findOne(codeInfo.getParentCodeInfo().getParentCodeSeq());
		redisService.save("codeList", parentCodeInfo.getParentCodeSeq(), StringUtil.convertJsonString(parentCodeInfo));
		
        return new ResponseEntity<CodeInfo>(returnCodeInfo, HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/child")
    public ResponseEntity<CodeInfo> delChildCode(@RequestBody CodeInfo codeInfo) throws Exception {
		codeService.delete(codeInfo);
		
		ParentCodeInfo parentCodeInfo = parentCodeInfoRepository.findOne(codeInfo.getParentCodeInfo().getParentCodeSeq());
		redisService.save("codeList", parentCodeInfo.getParentCodeSeq(), StringUtil.convertJsonString(parentCodeInfo));
		
        return new ResponseEntity<CodeInfo>(codeInfo, HttpStatus.OK);
    }
}
