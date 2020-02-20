package com.systemk.spyder.Service.Impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Entity.Main.BartagMaster;
import com.systemk.spyder.Entity.Main.BatchTrigger;
import com.systemk.spyder.Entity.Main.BatchTriggerDetail;
import com.systemk.spyder.Entity.Main.RfidTagMaster;
import com.systemk.spyder.Entity.Main.UserEmailInfo;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.BartagMasterRepository;
import com.systemk.spyder.Repository.Main.RfidTagHistoryRepository;
import com.systemk.spyder.Repository.Main.RfidTagMasterRepository;
import com.systemk.spyder.Repository.Main.RfidTagStatusRepository;
import com.systemk.spyder.Repository.Main.UserInfoRepository;
import com.systemk.spyder.Repository.Main.Specification.RfidTagMasterSpecification;
import com.systemk.spyder.Service.BartagLogService;
import com.systemk.spyder.Service.BartagService;
import com.systemk.spyder.Service.MailService;
import com.systemk.spyder.Service.RfidTagService;
import com.systemk.spyder.Service.UserNotiService;
import com.systemk.spyder.Service.CustomBean.CountModel;
import com.systemk.spyder.Service.CustomBean.TextUploadValid;
import com.systemk.spyder.Service.Mapper.RfidCountRowMapper;
import com.systemk.spyder.Util.CalendarUtil;

@Service
public class RfidTagServiceImpl implements RfidTagService {
	
	private final Path rootLocation = Paths.get("d://upload");
	
	@Autowired
	private JdbcTemplate template;
	
	@Autowired
	private RfidTagMasterRepository rfidTagMasterRepository;
	
	@Autowired
	private BartagMasterRepository bartagMasterRepository;
	
	@Autowired
	private RfidTagStatusRepository rfidTagStatusRepository;
	
	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;
	
	@Autowired
	private BartagLogService bartagLogService;
	
	@Autowired
	private BartagService bartagService;
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private UserNotiService userNotiService;
	
	@Autowired
	private RfidTagHistoryRepository rfidTagHistoryRepository;

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


	@Transactional
	@Override
	public ArrayList<RfidTagMaster> textLoad(File file, long seq, long userSeq) {
		
		Date startDate = new Date();
		
		BartagMaster bartag = bartagMasterRepository.findOne(seq);
		
		ArrayList<RfidTagMaster> textList = new ArrayList<>();
		
		int count = 0;
		
		try {
			FileReader fr = null;
			BufferedReader br = null;
			
			String read = null;
			
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			ArrayList<String> lineList = new ArrayList<String>();

			while ((read = br.readLine())!=null) {
				lineList.add(read);
			}
			
			String firstIndex = lineList.get(0).split(",")[0].trim();
			if(!isStringDouble(firstIndex)) {
				lineList.remove(0);
			}
			
			for (int i = 0; i < lineList.size(); i++) {
				
				String a = lineList.get(i);
				
				String rfidTag = a.split(",")[0].trim();
				String createDate = a.split(",")[1].trim();
				String barSeq = a.split(",")[2].trim(); 
				String lineSeq = a.split(",")[3].trim();
				String erpKey = a.split(",")[4].trim();
				String season = a.split(",")[5].trim();
				String orderDegree = a.split(",")[6].trim();
				String customerCd = a.split(",")[7].trim();
				String publishLocation = a.split(",")[8].trim();
				String publishRegDate = a.split(",")[9].trim();
				String publishDegree = a.split(",")[10].trim();
				String rfidSeq = a.split(",")[11].trim();
				
				String compareRfidTag = erpKey + season + orderDegree + customerCd + publishLocation + publishRegDate + publishDegree + rfidSeq;
				
				if(bartag.getErpKey().equals(erpKey) && 
				   bartag.getCreateDate().equals(createDate.replaceAll("-", "")) &&
				   bartag.getLineSeq().toString().equals(lineSeq) && 
				   bartag.getSeq().toString().equals(barSeq) && rfidTag.equals(compareRfidTag)) {
					
					RfidTagMaster rfidTagMaster = rfidTagMasterRepository.findByRfidTag(rfidTag);
					
					if(rfidTagMaster == null){
						update(rfidTag, publishDegree, publishRegDate, seq, rfidSeq);
						 
						count++;
					}
				} 
			}

			if(count > 0){
				// 이전 바택 수량 정보 넣음
				BartagMaster tempBartag = bartagLogService.beforeAmountSetting(bartag);
				bartag = bartagLogService.currentAmountSetting(bartag);
				
				// 바택 발행 미완료 업데이트
				bartag.setStat("4");

				bartagMasterRepository.save(bartag);
				
				// 바택 수량 로그 업데이트
				bartagLogService.save(tempBartag, bartag, userSeq, startDate, "2");
			}
						
			if(fr!=null)fr.close();
			if(br!=null)br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return textList;
	}
	
	@Transactional
	@Override
	public ArrayList<RfidTagMaster> textLoadReissue(File file, long seq, long userSeq){
		
		Date startDate = new Date();
		
		BartagMaster bartag = bartagMasterRepository.findOne(seq);
		
		ArrayList<RfidTagMaster> textList = new ArrayList<>();
		
		int count = 0;
		
		try {
			FileReader fr = null;
			BufferedReader br = null;
			
			String read = null;
			
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			ArrayList<String> lineList = new ArrayList<String>();

			while ((read = br.readLine())!=null) {
				lineList.add(read);
			}
			
			String firstIndex = lineList.get(0).split(",")[0].trim();
			if(!isStringDouble(firstIndex)) {
				lineList.remove(0);
			}
			
			for (int i = 0; i < lineList.size(); i++) {
				
				String a = lineList.get(i);
				System.out.println(i+1 + " = " + a);
				
				String rfidTag = a.split(",")[0].trim();
				String createDate = a.split(",")[1].trim();
				String barSeq = a.split(",")[2].trim(); 
				String lineSeq = a.split(",")[3].trim();
				String erpKey = a.split(",")[4].trim();
				String season = a.split(",")[5].trim();
				String orderDegree = a.split(",")[6].trim();
				String customerCd = a.split(",")[7].trim();
				String publishLocation = a.split(",")[8].trim();
				String publishRegDate = a.split(",")[9].trim();
				String publishDegree = a.split(",")[10].trim();
				String rfidSeq = a.split(",")[11].trim();
				
				String compareRfidTag = erpKey + season + orderDegree + customerCd + publishLocation + publishRegDate + publishDegree + rfidSeq;
				
				if(bartag.getErpKey().equals(erpKey) && 
				   bartag.getCreateDate().equals(createDate.replaceAll("-", "")) &&
				   bartag.getLineSeq().toString().equals(lineSeq) && 
				   bartag.getSeq().toString().equals(barSeq) && rfidTag.equals(compareRfidTag)) {
					
					RfidTagMaster rfidTagMaster = rfidTagMasterRepository.findByRfidTag(rfidTag);
					
					if(rfidTagMaster == null){
						reissueUpdate(erpKey, season, orderDegree, customerCd, rfidSeq, publishDegree, publishRegDate);
						count ++;
					}
				} 
			}
			
			if(count > 0){
				
//				rfidTagMasterRepository.flush();
				
				// 이전 바택 수량 정보 넣음
				BartagMaster tempBartag = bartagLogService.beforeAmountSetting(bartag);
				bartag = bartagLogService.currentAmountSetting(bartag);
				
				bartagMasterRepository.save(bartag);
				
				// 바택 수량 로그 업데이트
				bartagLogService.save(tempBartag, bartag, userSeq, startDate, "4");
			}
						
			if(fr!=null)fr.close();
			if(br!=null)br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return textList;
	}
	
	@Transactional
	@Override
	public void update(String rfidTag, String publishDegree, String publishRegDate, long seq, String rfidSeq) {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "UPDATE dbo.rfid_tag_master SET rfid_tag = ?, publish_reg_date = ?, publish_degree = ?, stat = 2 WHERE bartag_seq = ? AND rfid_seq = ?";
		template.update(query, 
				rfidTag, 
				publishRegDate,
				publishDegree,
				seq,
				rfidSeq);
		
	}
	
	@Transactional
	@Override
	public int update(RfidTagMaster tempRfidTag) {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("rfidTag", tempRfidTag.getRfidTag());
		params.put("publishRegDate", tempRfidTag.getPublishRegDate());
		params.put("publishDegree", tempRfidTag.getPublishDegree());
		params.put("updUserSeq", tempRfidTag.getUpdUserInfo().getUserSeq());
		params.put("createDate", tempRfidTag.getCreateDate());
		params.put("seq", tempRfidTag.getSeq());
		params.put("lineSeq", tempRfidTag.getLineSeq());
		params.put("rfidSeq", tempRfidTag.getRfidSeq());
		
		StringBuffer query = new StringBuffer();
		
		query.append("UPDATE dbo.rfid_tag_master SET rfid_tag = :rfidTag, ");
		query.append("publish_reg_date = :publishRegDate, ");
		query.append("publish_degree = :publishDegree, ");
		query.append("stat = 2, ");
		query.append("upd_date = getDate(), ");
		query.append("upd_user_seq = :updUserSeq ");
		query.append("WHERE create_date = :createDate ");
		query.append("AND seq = :seq ");
		query.append("AND line_seq = :lineSeq ");
		query.append("AND rfid_tag IS NULL ");
		query.append("AND stat = '1' ");
		query.append("AND rfid_seq = :rfidSeq ");
		
		return nameTemplate.update(query.toString(), params);
	}

	@Transactional
	@Override
	public void update(long bartagSeq, String stat, long userSeq) {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "UPDATE dbo.rfid_tag_master SET stat = ?, upd_date = getDate(), upd_user_seq = ? WHERE bartag_seq = ? AND stat = '2'";
		template.update(query, 
				stat,
				userSeq,
				bartagSeq);
		
	}
	
	@Transactional
	@Override
	public void reissueUpdate(String erpKey, String season, String orderDegree, String customerCd, String rfidSeq, String publishDegree, String publishRegDate) {
		
		RfidTagMaster rfidTag = rfidTagMasterRepository.findTopByErpKeyAndSeasonAndOrderDegreeAndCustomerCdAndRfidSeq(erpKey, season, orderDegree, customerCd, rfidSeq);
		
		RfidTagMaster insertRfidTag = new RfidTagMaster();
		insertRfidTag.CopyData(rfidTag);
		insertRfidTag.setPublishLocation("1");
		insertRfidTag.setPublishRegDate(publishRegDate);
		insertRfidTag.setPublishDegree(publishDegree);
		insertRfidTag.setRegDate(new Date());
		insertRfidTag.setStat("4");
		insertRfidTag.setRfidTag(erpKey + season + orderDegree + customerCd + "1" + publishRegDate + publishDegree + rfidSeq);
		
		rfidTagMasterRepository.save(insertRfidTag);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<RfidTagMaster> findAll(Long bartagSeq, String stat, String search, String option, Pageable pageable) {
		
		Page<RfidTagMaster> page =null;
		
		Specifications<RfidTagMaster> specifications = null;
		
		specifications = Specifications.where(RfidTagMasterSpecification.bartagSeqEqual(bartagSeq));
		
		if(!stat.equals("all")){
			specifications = specifications.and(RfidTagMasterSpecification.statEqual(stat));
		}
		
		if(!search.equals("") && option.equals("publishLocation")) {
			specifications = specifications.and(RfidTagMasterSpecification.publishLocationContaining(search));
		} else if(!search.equals("") && option.equals("publishRegDate")) {
			specifications = specifications.and(RfidTagMasterSpecification.publishRegDateContaining(search));
		} else if(!search.equals("") && option.equals("publishDegree")) {
			specifications = specifications.and(RfidTagMasterSpecification.publishDegreeContaining(search));
		} else if(!search.equals("") && option.equals("rfidSeq")) {
			specifications = specifications.and(RfidTagMasterSpecification.rfidSeqContaining(search));
		}
		
		page = rfidTagMasterRepository.findAll(specifications, pageable);
		
		return page;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<RfidTagMaster> getTagStat(Long bartagSeq, String stat, Pageable pageable) {
		return rfidTagMasterRepository.findByBartagSeqAndStat(bartagSeq, stat, pageable);
	}
	
	@Transactional
	@Override
	public Set<BatchTriggerDetail> textLoadBatch(BatchTrigger trigger) throws Exception{
		
		Date startDate = new Date();
		
	    ArrayList<String> bartagSeqList = new ArrayList<String>();
	    ArrayList<String> fileList = new ArrayList<String>();
	    ArrayList<String> customerList = new ArrayList<String>();
	    Set<BatchTriggerDetail> detailSet = new HashSet<BatchTriggerDetail>();
		
		String fileName = trigger.getExplanatory();
		
		if(fileName == null || fileName.equals("") || fileName.length() == 0){
			return null;
		}
		
		if(fileName.contains(",")){
			
			for(String name : fileName.split(",")){
				fileList.add(name);
			}
			
		} else {
			fileList.add(fileName);
		}
		
		for(String fileOriName : fileList){
			
			if(fileOriName.trim().length() == 0){
				continue;
			}
			
			Path file = rootLocation.resolve(fileOriName);
			
			Stream<String> lines = Files.lines(file, StandardCharsets.ISO_8859_1);
			
			BatchTriggerDetail triggerDetail = new BatchTriggerDetail();
			StringBuilder sb = new StringBuilder();
			
			int totalAmount = 0;
			int successAmount = 0;
			int failAmount = 0;
			
			triggerDetail.setExplanatory(fileOriName);
			triggerDetail.setCreateDate(CalendarUtil.convertFormat("yyyyMMdd"));
			triggerDetail.setRegDate(new Date());
			
            for( String lineStr : (Iterable<String>) lines::iterator ){
            	
            	String[] strList = StringUtils.split(lineStr, ",");
            	
            	String rfidTag = strList[0];
				String createDate = strList[1];
				String barSeq = strList[2]; 
				String lineSeq = strList[3];
				String erpKey = strList[4];
				String season = strList[5];
				String orderDegree = strList[6];
				String customerCd = strList[7];
				String publishLocation = strList[8];
				String publishRegDate = strList[9];
				String publishDegree = strList[10];
				String rfidSeq = strList[11];
				
				String compareRfidTag = erpKey + season + orderDegree + customerCd + publishLocation + publishRegDate + publishDegree + rfidSeq;
				
				if(rfidTag.length() != 32 && compareRfidTag.length() != 32) {
					continue;
				}
				
				totalAmount ++;
				
				if(!rfidTag.equals(compareRfidTag)) {
					failAmount ++;
					sb.append("RFID Tag와 다른 항목의 값이 일치하지 않습니다. : " + lineStr);
					sb.append(System.getProperty("line.separator"));
					
					continue;
				}
				
				RfidTagMaster tempRfidTag = new RfidTagMaster();
				
				tempRfidTag.setRfidTag(rfidTag);
				tempRfidTag.setCreateDate(createDate.replaceAll("-", ""));
				tempRfidTag.setSeq(Long.valueOf(barSeq));
				tempRfidTag.setLineSeq(Long.valueOf(lineSeq));
				tempRfidTag.setRfidSeq(rfidSeq);
				tempRfidTag.setPublishDegree(publishDegree);
				tempRfidTag.setPublishRegDate(publishRegDate);
				tempRfidTag.setStat("2");
				tempRfidTag.setUpdDate(new Date());
				tempRfidTag.setUpdUserInfo(trigger.getRegUserInfo());
				
				int result = update(tempRfidTag);
				
				if(result == 0) {
					failAmount ++;
					sb.append("업데이트할 항목이 없습니다. : " + lineStr);
					sb.append(System.getProperty("line.separator"));
					
					continue;
				}
				
				bartagSeqList.add(tempRfidTag.getCreateDate() + "," + tempRfidTag.getSeq() + "," + tempRfidTag.getLineSeq());
				successAmount ++;
            }
            
            if(sb.length() > 0) {
            	triggerDetail.setResultMessage(sb.toString());
            }
            triggerDetail.setTotalAmount(Long.valueOf(totalAmount));
            triggerDetail.setSuccessAmount(Long.valueOf(successAmount));
            triggerDetail.setFailAmount(Long.valueOf(failAmount));
            
            detailSet.add(triggerDetail);
		}
		
		HashSet<String> distinctData = new HashSet<String>(bartagSeqList);
		bartagSeqList = new ArrayList<String>(distinctData);
		
		// 바택 발행에 바택 발행 대기 수량 업데이트
		for(String bartagSeq : bartagSeqList){
			
			String[] strList = StringUtils.split(bartagSeq, ",");
			
			BartagMaster bartag = bartagMasterRepository.findByCreateDateAndSeqAndLineSeq(strList[0], Long.valueOf(strList[1]), Long.valueOf(strList[2]));
			
			// 이전 바택 수량 정보 넣음
			BartagMaster tempBartag = bartagLogService.beforeAmountSetting(bartag);
			bartag = bartagLogService.currentAmountSetting(bartag);
			
			// 바택 발행 미완료 업데이트
			bartag.setStat("4");
			
			bartag = bartagMasterRepository.save(bartag);
			
			// 바택 수량 로그 업데이트
			bartagLogService.save(tempBartag, bartag, trigger.getRegUserInfo().getUserSeq(), startDate, "2");
			
			if(bartag.getStat1Amount() == 0){
				customerList.add(bartagService.bartagCompleteProcessMod(bartag, trigger.getRegUserInfo()));
			}
		}
		
		if(customerList.size() > 0){
			
			HashSet<String> distinctCustomerList = new HashSet<String>(customerList);
			customerList = new ArrayList<String>(distinctCustomerList);
			
			for(String customerCd : customerList){
				
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
		}
		
		return detailSet;
	}
	
	@Transactional
	@Override
	public Set<BatchTriggerDetail> textLoadReissueBatch(BatchTrigger trigger) throws Exception {
		
		Date startDate = new Date();
		
	    ArrayList<Long> bartagSeqList = new ArrayList<Long>();
	    ArrayList<String> fileList = new ArrayList<String>();
	    ArrayList<RfidTagMaster> rfidTagList = new ArrayList<RfidTagMaster>();
	    ArrayList<String> customerList = new ArrayList<String>();
	    Set<BatchTriggerDetail> detailSet = new HashSet<BatchTriggerDetail>();
		
		String fileName = trigger.getExplanatory();
		if(fileName.contains(",")){
			
			for(String name : fileName.split(",")){
				fileList.add(name);
			}
			
		} else {
			fileList.add(fileName);
		}
		
		for(String fileOriName : fileList){
			
			if(fileOriName.trim().length() == 0){
				continue;
			}
			
			Path file = rootLocation.resolve(fileOriName);
			
			Stream<String> lines = Files.lines(file, StandardCharsets.ISO_8859_1);
			
			BatchTriggerDetail triggerDetail = new BatchTriggerDetail();
			StringBuilder sb = new StringBuilder();
			
			int totalAmount = 0;
			int successAmount = 0;
			int failAmount = 0;
			
			triggerDetail.setExplanatory(fileOriName);
			triggerDetail.setCreateDate(CalendarUtil.convertFormat("yyyyMMdd"));
			triggerDetail.setRegDate(new Date());
			
            for( String lineStr : (Iterable<String>) lines::iterator ){
            	
            	String[] strList = StringUtils.split(lineStr, ",");
            	
            	String rfidTag = strList[0];
				String createDate = strList[1];
				String barSeq = strList[2]; 
				String lineSeq = strList[3];
				String erpKey = strList[4];
				String season = strList[5];
				String orderDegree = strList[6];
				String customerCd = strList[7];
				String publishLocation = strList[8];
				String publishRegDate = strList[9];
				String publishDegree = strList[10];
				String rfidSeq = strList[11];
				
				String compareRfidTag = erpKey + season + orderDegree + customerCd + publishLocation + publishRegDate + publishDegree + rfidSeq;
				
				if(rfidTag.length() != 32 && compareRfidTag.length() != 32) {
					continue;
				}
				
				totalAmount ++;
				
				if(!rfidTag.equals(compareRfidTag)) {
					failAmount ++;
					sb.append("RFID Tag와 다른 항목의 값이 일치하지 않습니다. : " + lineStr);
					sb.append(System.getProperty("line.separator"));
					
					continue;
				}
				
				RfidTagMaster rfidTagMaster = rfidTagMasterRepository.findByRfidTag(rfidTag);
				
				if(rfidTagMaster != null) {
					failAmount ++;
					sb.append("재발행 RFID Tag값이 이미 존재합니다. : "  + lineStr);
					sb.append(System.getProperty("line.separator"));
					
					continue;
				}
				
				RfidTagMaster tempRfidTag = rfidTagMasterRepository.findTopByErpKeyAndSeasonAndOrderDegreeAndCustomerCdAndRfidSeq(erpKey, season, orderDegree, customerCd, rfidSeq);
				
				if(tempRfidTag == null) {
					failAmount ++;
					sb.append("원본을 알 수 없는 RFID Tag값입니다. : " + lineStr);
					sb.append(System.getProperty("line.separator"));
					
					continue;
				}
				
				RfidTagMaster insertRfidTag = new RfidTagMaster();
				insertRfidTag.CopyData(tempRfidTag);
				insertRfidTag.setPublishLocation("1");
				insertRfidTag.setPublishRegDate(publishRegDate);
				insertRfidTag.setPublishDegree(publishDegree);
				insertRfidTag.setRegDate(new Date());
				insertRfidTag.setStat("4");
				insertRfidTag.setRfidTag(erpKey + season + orderDegree + customerCd + "1" + publishRegDate + publishDegree + rfidSeq);
				
				rfidTagList.add(insertRfidTag);
				
				bartagSeqList.add(tempRfidTag.getBartagSeq());
				
				successAmount ++;
            }
            
            if(sb.length() > 0) {
            	triggerDetail.setResultMessage(sb.toString());
            }
            triggerDetail.setTotalAmount(Long.valueOf(totalAmount));
            triggerDetail.setSuccessAmount(Long.valueOf(successAmount));
            triggerDetail.setFailAmount(Long.valueOf(failAmount));
            
            detailSet.add(triggerDetail);
		}
		
		rfidTagMasterRepository.save(rfidTagList);
//		rfidTagMasterRepository.flush();
		
		HashSet<Long> distinctData = new HashSet<Long>(bartagSeqList);
		bartagSeqList = new ArrayList<Long>(distinctData);
		
		// 바택 발행에 바택 발행 대기 수량 업데이트
		for(Long bartagSeq : bartagSeqList){
			
			BartagMaster bartag = bartagMasterRepository.findOne(bartagSeq);
			
			// 이전 바택 수량 정보 넣음
			BartagMaster tempBartag = bartagLogService.beforeAmountSetting(bartag);
			bartag = bartagLogService.currentAmountSetting(bartag);
				
			bartagMasterRepository.save(bartag);
				
			// 바택 수량 로그 업데이트
			bartagLogService.save(tempBartag, bartag, trigger.getRegUserInfo().getUserSeq(), startDate, "4");
			
			// 재발행 프로세서 진행
			customerList.add(bartagService.bartagReissueProcess(bartag, trigger.getRegUserInfo()));
		}
		
		if(customerList.size() > 0){
			
			HashSet<String> distinctCustomerList = new HashSet<String>(customerList);
			customerList = new ArrayList<String>(distinctCustomerList);
			
			for(String customerCd : customerList){
				
				// 생산 입고예정 업체 대상 이메일 전송
				UserInfo emailInfo = userInfoRepository.findTop1ByCompanyInfoCustomerCode(customerCd);
				
				if(emailInfo != null){
					for(UserEmailInfo email : emailInfo.getUserEmailInfo()){
						mailService.sendMail(email.getEmail(), "RFID 재발행 태그입고예정", "RFID 재발행 태그입고예정 정보가 업데이트 되었습니다.<br /><a href='http://spyderrfid.co.kr/'>U의류관리시스템</a>에 접속하여 RFID 재발행 태그입고정보를 확인해주세요.", "4");
					}
					
					// 알림 추가
					userNotiService.save("RFID 재발행 태그입고예정 정보가 업데이트 되었습니다.", emailInfo, "production", Long.valueOf(0));
				}
				
			}
		}
		
		return detailSet;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Long countByBartagSeqAndStat(Long bartagSeq, String stat) throws Exception{
		return rfidTagMasterRepository.countByBartagSeqAndStat(bartagSeq, stat);
	}
	
	public boolean isStringDouble(String s) {
	    try {
	        Double.parseDouble(s);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> getRfidTagDetail(String rfidTag) throws Exception {
		
		Map<String, Object> obj = new HashMap<String, Object>();
		
		RfidTagMaster rfidTagMaster = rfidTagMasterRepository.findByRfidTag(rfidTag);
		obj.put("detail", rfidTagMaster);
		obj.put("bartagDetail", bartagMasterRepository.findOne(rfidTagMaster.getBartagSeq()));
		obj.put("statusDetail", rfidTagStatusRepository.findByRfidTag(rfidTag));
		//바택정보 - 태그상세에 태그이력목록 미노출(팝업의 팝업 페이지의$scope 범위 차이 때문인듯... 다시 지정해줌 - Cesil)
		obj.put("historyDetail", rfidTagHistoryRepository.findByRfidTag(rfidTag));
		return obj;
	}

	@Transactional(readOnly = true)
	@Override
	public CountModel count(long bartagSeq) throws Exception {
		
		NamedParameterJdbcTemplate nameTemplate = new NamedParameterJdbcTemplate(mainDataSourceConfig.mainDataSource()); 
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("bartagSeq", bartagSeq);
		
		StringBuffer query = new StringBuffer();
		query.append("SELECT COUNT(CASE WHEN rfid_tag_master.stat = '1' THEN stat END) stat1_amount, ");
		query.append("COUNT(CASE WHEN rfid_tag_master.stat = '2' THEN stat END) stat2_amount, ");
		query.append("COUNT(CASE WHEN rfid_tag_master.stat = '3' THEN stat END) stat3_amount, ");
		query.append("COUNT(CASE WHEN rfid_tag_master.stat = '4' THEN stat END) stat4_amount, ");
		query.append("COUNT(CASE WHEN rfid_tag_master.stat = '5' THEN stat END) stat5_amount, ");
		query.append("COUNT(CASE WHEN rfid_tag_master.stat = '6' THEN stat END) stat6_amount, ");
		query.append("COUNT(CASE WHEN rfid_tag_master.stat = '7' THEN stat END) stat7_amount ");
		query.append("FROM rfid_tag_master ");
		query.append("WHERE bartag_seq = :bartagSeq ");
		 
		return nameTemplate.queryForObject(query.toString(), params, new RfidCountRowMapper());
	}

	@Transactional
	@Override
	public void textUploadValue() throws Exception {
		
		ArrayList<String> fileList = new ArrayList<String>();
		ArrayList<TextUploadValid> textUploadList = new ArrayList<TextUploadValid>();
		
		fileList.add("20180416-215-002.txt");
		fileList.add("20180419-230-001.txt");
		fileList.add("20180511-215-001.txt");
		fileList.add("20180517-221-001.txt");
		fileList.add("20180523-246-002.txt");
		
		int count = 0;
		
		for(String fileOriName : fileList){	
			
			Path file = rootLocation.resolve(fileOriName);
			
			Stream<String> lines = Files.lines(file, StandardCharsets.ISO_8859_1);
			
            for( String a : (Iterable<String>) lines::iterator ){
            	
            	String[] strList = StringUtils.split(a, ",");
            	
            	String rfidTag = strList[0];
				String createDate = strList[1];
				String barSeq = strList[2]; 
				String lineSeq = strList[3];
				String erpKey = strList[4];
				String season = strList[5];
				String orderDegree = strList[6];
				String customerCd = strList[7];
				String publishLocation = strList[8];
				String publishRegDate = strList[9];
				String publishDegree = strList[10];
				String rfidSeq = strList[11];
				
				String compareRfidTag = erpKey + season + orderDegree + customerCd + publishLocation + publishRegDate + publishDegree + rfidSeq;
				
				if(rfidTag.equals(compareRfidTag)) {
					
					count ++;
					
					BartagMaster bartag = bartagMasterRepository.findByCreateDateAndSeqAndLineSeq(createDate.replaceAll("-", ""), Long.valueOf(barSeq), Long.valueOf(lineSeq));
					
					TextUploadValid vaild = new TextUploadValid();
					
					if(bartag != null){
						vaild.setBartagSeq(bartag.getBartagSeq());
					} else {
						vaild.setBartagSeq(Long.valueOf(0));
					}
					
					vaild.setCreateDate(createDate);
					vaild.setSeq(Long.valueOf(barSeq));
					vaild.setLineSeq(Long.valueOf(lineSeq));
					
					boolean flag = true;
					
					for(int i=0; i<textUploadList.size(); i++){
						TextUploadValid val = textUploadList.get(i);
						if(vaild.getCreateDate().equals(val.getCreateDate()) && 
						   vaild.getSeq().equals(val.getSeq()) &&
						   vaild.getLineSeq().equals(val.getLineSeq())){
							
							val.setCount(val.getCount() + 1);
							textUploadList.set(i, val);
							
							flag = false;
							break;
						}
					}
					
					if(flag){
						vaild.setCount(1);
						vaild.setAmount(bartag.getAmount());
						textUploadList.add(vaild);
					}
					
					System.out.println("count: " + count);
				} 
            }
		}
		
		if(textUploadList.size() > 0){
			for(TextUploadValid val : textUploadList){
				System.out.println(val.toString());
			}
		}
	}
	
	@Transactional
	@Override
	public void textLoadTestList(String publishDegree, String publishRegDate, long userSeq, List<BartagMaster> bartagList) throws Exception {
		
		Date startDate = new Date();
		
		ArrayList<BartagMaster> tempBartagList = new ArrayList<BartagMaster>();
	    ArrayList<String> customerList = new ArrayList<String>();
	    ArrayList<RfidTagMaster> tempRfidTagList = new ArrayList<RfidTagMaster>();
	    
	    UserInfo userInfo = userInfoRepository.findOne(userSeq);
		
		for(BartagMaster bartag : bartagList){
			
			List<RfidTagMaster> rfidTagList = rfidTagMasterRepository.findByBartagSeqAndStat(bartag.getBartagSeq(), "1");
			
			if(rfidTagList.size() > 0){
				
				for(RfidTagMaster tag : rfidTagList){
					
					tag.setPublishDegree(publishDegree);
					tag.setPublishRegDate(publishRegDate);
					
					String rfidTag = tag.getErpKey() + tag.getSeason() + tag.getOrderDegree() + tag.getCustomerCd() + tag.getPublishLocation() + tag.getPublishRegDate() + tag.getPublishDegree() + tag.getRfidSeq();
					
					tag.setRfidTag(rfidTag);
					tag.setStat("2");
					tag.setUpdDate(new Date());
					tag.setUpdUserInfo(userInfo);
					
					tempRfidTagList.add(tag);
				}
				
				boolean flag = true;
				
				for(BartagMaster tempBartag : tempBartagList){
					if(bartag.getBartagSeq() == tempBartag.getBartagSeq()){
						flag = false;
						break;
					}
				}
				
				if(flag){
					tempBartagList.add(bartag);
				}
			}
		}
		
		rfidTagMasterRepository.save(tempRfidTagList);
//		rfidTagMasterRepository.flush();
		
		// 바택 발행에 바택 발행 대기 수량 업데이트
		for(BartagMaster bartag : tempBartagList){
			
			// 이전 바택 수량 정보 넣음
			BartagMaster tempBartag = bartagLogService.beforeAmountSetting(bartag);
			bartag = bartagLogService.currentAmountSetting(bartag);
			
			// 바택 발행 미완료 업데이트
			bartag.setStat("4");
			
			bartag = bartagMasterRepository.save(bartag);
			
			// 바택 수량 로그 업데이트
			bartagLogService.save(tempBartag, bartag, userInfo.getUserSeq(), startDate, "2");
			
			if(bartag.getStat1Amount() == 0){
				customerList.add(bartagService.bartagCompleteProcessMod(bartag, userInfo));
			}
		}
		
		if(customerList.size() > 0){
			
			HashSet<String> distinctCustomerList = new HashSet<String>(customerList);
			customerList = new ArrayList<String>(distinctCustomerList);
			
			for(String customerCd : customerList){
				
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
		}
	}


	@Transactional
	@Override
	public void textLoadTestAll(String publishDegree, String publishRegDate, long userSeq) throws Exception{
		
		Date startDate = new Date();
		
		ArrayList<BartagMaster> tempBartagList = new ArrayList<BartagMaster>();
	    ArrayList<String> customerList = new ArrayList<String>();
	    ArrayList<RfidTagMaster> tempRfidTagList = new ArrayList<RfidTagMaster>();
	    
	    List<BartagMaster> bartagList = bartagMasterRepository.findByStat("3");
	    
	    UserInfo userInfo = userInfoRepository.findOne(userSeq);
		
		for(BartagMaster bartag : bartagList){
			
			List<RfidTagMaster> rfidTagList = rfidTagMasterRepository.findByBartagSeqAndStat(bartag.getBartagSeq(), "1");
			
			if(rfidTagList.size() > 0){
				
				for(RfidTagMaster tag : rfidTagList){
					
					tag.setPublishDegree(publishDegree);
					tag.setPublishRegDate(publishRegDate);
					
					String rfidTag = tag.getErpKey() + tag.getSeason() + tag.getOrderDegree() + tag.getCustomerCd() + tag.getPublishLocation() + tag.getPublishRegDate() + tag.getPublishDegree() + tag.getRfidSeq();
					
					tag.setRfidTag(rfidTag);
					tag.setStat("2");
					tag.setUpdDate(new Date());
					tag.setUpdUserInfo(userInfo);
					
					tempRfidTagList.add(tag);
//					rfidTagMasterRepository.save(tag);
				}
				
				boolean flag = true;
				
				for(BartagMaster tempBartag : tempBartagList){
					if(bartag.getBartagSeq() == tempBartag.getBartagSeq()){
						flag = false;
						break;
					}
				}
				
				if(flag){
					tempBartagList.add(bartag);
				}
			}
		}
		
		rfidTagMasterRepository.save(tempRfidTagList);
//		rfidTagMasterRepository.flush();
		
		// 바택 발행에 바택 발행 대기 수량 업데이트
		for(BartagMaster bartag : tempBartagList){
			
			// 이전 바택 수량 정보 넣음
			BartagMaster tempBartag = bartagLogService.beforeAmountSetting(bartag);
			bartag = bartagLogService.currentAmountSetting(bartag);
			
			// 바택 발행 미완료 업데이트
			bartag.setStat("4");
			
			bartag = bartagMasterRepository.save(bartag);
			
			// 바택 수량 로그 업데이트
			bartagLogService.save(tempBartag, bartag, userInfo.getUserSeq(), startDate, "2");
			
			if(bartag.getStat1Amount() == 0){
				customerList.add(bartagService.bartagCompleteProcessMod(bartag, userInfo));
			}
		}
		
		if(customerList.size() > 0){
			
			HashSet<String> distinctCustomerList = new HashSet<String>(customerList);
			customerList = new ArrayList<String>(distinctCustomerList);
			
			for(String customerCd : customerList){
				
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
		}
	}
	
	@Transactional(readOnly = true)
	@Override
	public Long maxSeq() throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "SELECT MAX(rfid_tag_seq) AS table_seq FROM rfid_tag_master";
		
		return template.queryForObject(query, Long.class);
	}

	@Transactional
	@Override
	public void disuseRfidTag(Long userSeq, Long rfidTagReissueSeq) throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		String query = "UPDATE rtm " + 
						  "SET rtm.stat = '7', " +
						  	  "rtm.upd_date = getdate(), " +
						  	  "rtm.upd_user_seq = ? " +
						 "FROM rfid_tag_master rtm " +
				   "INNER JOIN rfid_tag_reissue_request_detail rtrr " +
				   		   "ON rtm.rfid_tag = rtrr.rfid_tag " +
				   		  "AND rtrr.rfid_tag_reissue_request_seq = ?";
		
    	template.update(query, userSeq, rfidTagReissueSeq);
	}
}
