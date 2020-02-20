package com.systemk.spyder.Service.Impl;

import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Dto.Response.RfidTagHistoryResult;
import com.systemk.spyder.Entity.Main.RfidTagHistory;
import com.systemk.spyder.Repository.Main.RfidTagHistoryRepository;
import com.systemk.spyder.Service.RfidTagHistoryService;
import com.systemk.spyder.Util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RfidTagHistoryServiceImpl implements RfidTagHistoryService{

	@Autowired
	private RfidTagHistoryRepository rfidTagHistoryRepository;

	@Transactional(readOnly = true)
	@Override
	public Page<RfidTagHistory> findAll(String barcode, Pageable pageable) throws Exception {
		return rfidTagHistoryRepository.findByBarcode(barcode, pageable);
	}

	@Transactional(readOnly = true)
	@Override
	public List<RfidTagHistory> findByRfidTag(String rfidTag) throws Exception {
		return rfidTagHistoryRepository.findByRfidTag(rfidTag);		
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> findByHistory(String value, String flag) throws Exception {

		List<RfidTagHistory> rfidTagHistoryList = new ArrayList<>();

		// RFID 태그
		if(flag.equals("rfidTag")){
			rfidTagHistoryList = rfidTagHistoryRepository.findByRfidTag(value);

		// RFID 바코드 & 수기 입력
		} else {
			rfidTagHistoryList = rfidTagHistoryRepository.findByBarcode(value);
		}

		// 검색된 목록이 없을시
		if(rfidTagHistoryList.size() == 0){
			return ResultUtil.setCommonResult("E", ApiResultConstans.NOT_FIND_DATA_MESSAGE);
		}

		// TODO DTO로 변경하기!!
		return ResultUtil.setCommonResult("S", ApiResultConstans.SUCCESS_MESSAGE,
				rfidTagHistoryList.stream()
									.map(obj -> new RfidTagHistoryResult(obj))
									.collect(Collectors.toList())
		);
	}
}
