package com.systemk.spyder.Controller.Api.Web;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.systemk.spyder.Entity.Main.BartagMaster;
import com.systemk.spyder.Entity.Main.RfidTagMaster;
import com.systemk.spyder.Entity.Main.UserEmailInfo;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.BartagMasterRepository;
import com.systemk.spyder.Repository.Main.RfidTagMasterRepository;
import com.systemk.spyder.Repository.Main.UserInfoRepository;
import com.systemk.spyder.Security.LoginUser;
import com.systemk.spyder.Service.BartagService;
import com.systemk.spyder.Service.BatchTriggerService;
import com.systemk.spyder.Service.MailService;
import com.systemk.spyder.Service.RfidTagService;
import com.systemk.spyder.Service.UserNotiService;
import com.systemk.spyder.Service.CustomBean.CountModel;
import com.systemk.spyder.Service.CustomBean.FileNames;
import com.systemk.spyder.Util.SecurityUtil;


@RestController
@RequestMapping("/rfidTag")
public class RfidTagController {
	
	@Autowired
	private RfidTagService rfidTagService;
	
	@Autowired
	private RfidTagMasterRepository rfidTagMasterRepository;
	
	@Autowired
	private BartagService bartagService;
	
	@Autowired
	private BatchTriggerService batchTriggerService;
	
	@Autowired
	private UserNotiService userNotiService;
	
	@Autowired
    private UserInfoRepository userInfoRepository;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private BartagMasterRepository bartagMasterRepository;
	
	private final Path rootLocation = Paths.get("d://upload");
	
	/**
	 * 텍스트 업로드
	 * 엣지 브라우저에서는 업로드 디렉토리 path까지 전달되는 오류가 있어 fileName을 따로 받도록 수정
	 * @param file
	 * @param rfid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value="/{seq}")
    public List<RfidTagMaster> uploadFileMulti(@PathVariable(value = "seq", required = true) long seq, 
    										   @RequestParam("uploadfile") MultipartFile file,
    										   @RequestParam(value = "flag", required = false, defaultValue = "") String flag,
    										   @RequestParam("fileName") String fileName) throws Exception {
		ArrayList<RfidTagMaster> rfidTagMaster = new ArrayList<>();
		
		try {
			File convFile = new File(rootLocation + "/" + fileName);
		    convFile.createNewFile();
		    FileOutputStream fos = new FileOutputStream(convFile);
		    fos.write(file.getBytes());
		    fos.close();
		    
		    LoginUser user = SecurityUtil.getCustomUser();
		    
		    if(flag.equals("publish")){
		    	rfidTagMaster = rfidTagService.textLoad(convFile, seq, user.getUserSeq());
		    } else {
		    	rfidTagMaster = rfidTagService.textLoadReissue(convFile, seq, user.getUserSeq());
		    }

		} catch (Exception e) {
			e.printStackTrace();
//			throw new Exception("업로드 실패. 파일 크기가 5MB를 넘어선 안됩니다.");
		}
		
		return rfidTagMaster;
    }
	
	/**
	 * 텍스트 업로드
	 * 엣지 브라우저에서는 업로드 디렉토리 path까지 전달되는 오류가 있어 fileName을 따로 받도록 수정
	 * @param file
	 * @param rfid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value="/all")
    public boolean uploadFile(@RequestParam("uploadfile") MultipartFile file, @RequestParam("fileName") String fileName) throws Exception {
		
		boolean success = false;
		
		try {
			
			File convFile = new File(rootLocation + "/" + fileName);
		    convFile.createNewFile();
		    FileOutputStream fos = new FileOutputStream(convFile);
		    fos.write(file.getBytes());
		    fos.close();
		    
		    LoginUser user = SecurityUtil.getCustomUser();
		    
		    UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());
		    
		    success = batchTriggerService.save(fileName, "1", userInfo);
		    
		    // 알림 추가
		    userNotiService.save(success ? "배치 업로드 작업이 예약되었습니다." : "배치 업로드 작업이 예약 실패하였습니다.", userInfo, "textUploadBatch", Long.valueOf(0));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return success;
    }
	
	/**
	 * 텍스트 업로드(다중 파일 업로드 지원)
	 * @param file
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value="/allFiles/{flag}")
    public boolean uploadFileAll(@RequestBody FileNames fileNames, @PathVariable(value = "flag", required = true) String flag) throws Exception {
		
		boolean success = false;
		
		try {
			
			String type = "";
			
			if(flag.equals("textUploadBatch")){
				type = "1";
			} else if(flag.equals("textUploadReissueBatch")){
				type = "4";
			}
			
		    LoginUser user = SecurityUtil.getCustomUser();
		    
		    UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());
		    
		    success = batchTriggerService.save(fileNames.getFileNames(), type, userInfo);
		    
		    // 알림 추가
		    userNotiService.save(success ? "배치 업로드 작업이 예약되었습니다." : "배치 업로드 작업이 예약 실패하였습니다.", userInfo, flag, Long.valueOf(0));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return success;
    }
	

	/**
	 * 최종확인
	 * @param rfidTagMaster
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value="/finalConfirm/{seq}")
	public boolean finalConfirm(@PathVariable(value = "seq", required = true) long seq) throws Exception{
		
		boolean success = true;
		Long count = rfidTagMasterRepository.countByBartagSeqAndStat(seq, "1");
		
		if(count > 0){
			success = false;
		}
		
		if(success){
			
			LoginUser user = SecurityUtil.getCustomUser();
			
			String customerCd = bartagService.bartagCompleteProcess(seq, user.getUserSeq());
			
			// 생산 입고예정 업체 대상 이메일 전송
			UserInfo emailInfo = userInfoRepository.findTop1ByCompanyInfoCustomerCode(customerCd);
			
			if(emailInfo != null){
				for(UserEmailInfo email : emailInfo.getUserEmailInfo()){
					mailService.sendMail(email.getEmail(), "RFID 태그입고예정", "RFID 태그입고예정 정보가 업데이트 되었습니다.<br /><a href='http://spyderrfid.co.kr/'>U의류관리시스템</a>에 접속하여 RFID 태그입고정보를 확인해주세요.", "3");
				}
				
				// 알림 추가
				userNotiService.save("RFID 태그입고예정 정보가 업데이트 되었습니다.", emailInfo, "production", Long.valueOf(0));
			}
		}
		return success;
	}
	
	/**
	 * 재발행 최종확인
	 * @param rfidTagMaster
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value="/refinalConfirm/{seq}")
	public boolean refinalConfirm(@PathVariable(value = "seq", required = true) long seq) throws Exception{
		
		boolean success = true;
		
		Long checkCount = rfidTagMasterRepository.countByBartagSeqAndStat(seq, "4");
		Long targetCount = rfidTagMasterRepository.countByBartagSeqAndStat(seq, "6");
		
		if(checkCount != targetCount){
			success = false;
		}
		
		if(success){
			
			LoginUser user = SecurityUtil.getCustomUser();
			
			UserInfo userInfo = userInfoRepository.findOne(user.getUserSeq());
			BartagMaster bartag = bartagMasterRepository.findOne(seq);
			
			bartagService.bartagReissueProcess(bartag, userInfo);
		}
		return success;
	}

	
	/**
	 * rfid태그 페이징
	 * @param pageable
	 * @param search
	 * @param option
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value="/{seq}")
	public Page<RfidTagMaster> findAll(@PathVariable(value = "seq", required = true) long seq,
											@PageableDefault(sort = {"rfidTagSeq"}, direction = Sort.Direction.ASC, size = 10) Pageable pageable,
											@RequestParam(value = "stat", required = false, defaultValue = "") String stat,
			  								@RequestParam(value = "search", required = false, defaultValue = "") String search,
			  								@RequestParam(value = "option", required = false, defaultValue = "") String option) throws Exception {
		return rfidTagService.findAll(seq, stat, search, option, pageable);
	}
	
	/**
	 * 태그 상태별 수량
	 * @param bartagSeq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value="/count")
	public ResponseEntity<String> getRfidTagStatCount(@RequestParam(value = "seq", required = true) Long bartagSeq) throws Exception {
		JSONObject obj = new JSONObject();
		
		CountModel count = rfidTagService.count(bartagSeq);
		obj.put("statSize", rfidTagMasterRepository.countByBartagSeq(bartagSeq));
		obj.put("stat1", count.getStat1_amount());
		obj.put("stat2", count.getStat2_amount());
		obj.put("stat3", count.getStat3_amount());
		obj.put("stat4", count.getStat4_amount());
		obj.put("stat5", count.getStat5_amount());
		obj.put("stat6", count.getStat6_amount());
		obj.put("stat7", count.getStat7_amount());
		return new ResponseEntity<String>(obj.toString(), HttpStatus.OK);
	}
	
	/**
	 * 태그상태별 수량 클릭시 해당 수량 출력
	 * @param rfidTagMaster
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/stat")
	public Page<RfidTagMaster> rfidStat(@RequestParam(value = "bartagSeq", required = true) long bartagSeq,
										@RequestParam(value = "stat", required = true) String stat,
										@PageableDefault(sort = {"rfidTagSeq"}, direction = Sort.Direction.ASC, size = 10) Pageable pageable) throws Exception{
		return rfidTagService.getTagStat(bartagSeq, stat, pageable);
	}
	
	/**
	 * 태그 상세 정보
	 * @param bartagSeq
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value="/detail/{rfidTag}")
	public ResponseEntity<Map<String, Object>> findOne(@PathVariable(value = "rfidTag", required = true) String rfidTag) throws Exception {
		return new ResponseEntity<Map<String, Object>>(rfidTagService.getRfidTagDetail(rfidTag), HttpStatus.OK);
	}
	
}
