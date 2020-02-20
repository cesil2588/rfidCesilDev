package com.systemk.spyder.Service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Entity.Main.BartagMaster;
import com.systemk.spyder.Entity.Main.RfidTagHistory;
import com.systemk.spyder.Entity.Main.RfidTagStatus;
import com.systemk.spyder.Repository.Main.BartagMasterRepository;
import com.systemk.spyder.Repository.Main.RfidTagHistoryRepository;
import com.systemk.spyder.Repository.Main.RfidTagStatusRepository;
import com.systemk.spyder.Service.RfidTagStatusService;

@Service
public class RfidTagStatusServiceImpl implements RfidTagStatusService{

	@Autowired
	private RfidTagStatusRepository rfidTagStatusRepository;

	@Autowired
	private BartagMasterRepository bartagMasterRepository;

	@Autowired
	private RfidTagHistoryRepository rfidTagHistoryRepository;

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> findByRfidTag(String rfidTag) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		if(rfidTag == null || rfidTag.equals("")){
			obj.put("resultCode", ApiResultConstans.BAD_PARAMETER);
			obj.put("resultMessage", ApiResultConstans.BAD_PARAMETER_MESSAGE);
			return obj;
		}

		RfidTagStatus rfidTagStatus = rfidTagStatusRepository.findByRfidTag(rfidTag);

		if(rfidTagStatus == null){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_RFID_TAG);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_RFID_TAG_MESSAGE);
			return obj;
		}

		if(rfidTagStatus.getStat().equals("2")){
			obj.put("resultCode", ApiResultConstans.DISUSE_RFID_TAG);
			obj.put("resultMessage", ApiResultConstans.DISUSE_RFID_TAG_MESSAGE);
			return obj;
		}

		List<RfidTagHistory> rfidTagHistoryList = rfidTagHistoryRepository.findByRfidTag(rfidTag);

		obj.put("rfidTagHistoryList", rfidTagHistoryList);
		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> findByTagBarcode(String barcode) throws Exception {

		Map<String, Object> obj = new HashMap<String, Object>();

		if(barcode.length() != 15){
			obj.put("resultCode", ApiResultConstans.NOT_VALID_RFID_TAG_BARCODE);
			obj.put("resultMessage", ApiResultConstans.NOT_VALID_RFID_TAG_BARCODE_MESSAGE);
			return obj;
		}

		Map<String, Object> tagObj = new HashMap<String, Object>();

		List<RfidTagStatus> rfidTagStatusList = rfidTagStatusRepository.findByBarcode(barcode);

		boolean aliveCheck = false;

		if(rfidTagStatusList.size() == 0){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_RFID_TAG);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_RFID_TAG_MESSAGE);
			return obj;
		}

		for(RfidTagStatus rfidTagStatus : rfidTagStatusList){
			if(rfidTagStatus.getStat().equals("1")){

				tagObj.put("rfidTag", rfidTagStatus.getRfidTag());
				aliveCheck = true;
				break;
			}
		}

		if(!aliveCheck){
			obj.put("resultCode", ApiResultConstans.DISUSE_RFID_TAG);
			obj.put("resultMessage", ApiResultConstans.DISUSE_RFID_TAG_MESSAGE);
			return obj;
		}

		String erpKey = barcode.substring(0, 10);
		int rfidSeq = Integer.parseInt(barcode.substring(10, 15));

		List<BartagMaster> bartagList = bartagMasterRepository.findByErpKey(erpKey);

		boolean foundCheck = false;

		for(BartagMaster bartag : bartagList){
			int tempStartRfidSeq = Integer.parseInt(bartag.getStartRfidSeq());
			int tempEndRfidSeq = Integer.parseInt(bartag.getEndRfidSeq());

			if(tempStartRfidSeq <= rfidSeq && tempEndRfidSeq >= rfidSeq){

				tagObj.put("style", bartag.getStyle());
				tagObj.put("color", bartag.getColor());
				tagObj.put("size", bartag.getSize());
				tagObj.put("rfidSeq", barcode.substring(10, 15));

				obj.put("tagInfo", tagObj);

				foundCheck = true;
				break;
			}
		}

		if(!foundCheck){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_BARTAG);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_BARTAG_MESSAGE);
			return obj;
		}

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);

		return obj;
	}

}
