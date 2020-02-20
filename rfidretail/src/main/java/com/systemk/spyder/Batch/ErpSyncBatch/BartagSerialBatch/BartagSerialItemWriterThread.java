package com.systemk.spyder.Batch.ErpSyncBatch.BartagSerialBatch;

import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.systemk.spyder.Entity.Main.BartagMaster;
import com.systemk.spyder.Entity.Main.CompanyInfo;
import com.systemk.spyder.Entity.Main.RfidTagMaster;
import com.systemk.spyder.Repository.Main.CompanyInfoRepository;
import com.systemk.spyder.Repository.Main.RfidTagMasterRepository;
import com.systemk.spyder.Util.StringUtil;

public class BartagSerialItemWriterThread {
	
	private ExecutorService executorService;
	
	private CompanyInfoRepository companyInfoRepository;
	
	private RfidTagMasterRepository rfidTagMasterRepository;

	public BartagSerialItemWriterThread(CompanyInfoRepository companyInfoRepository, 
									 	RfidTagMasterRepository rfidTagMasterRepository){
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		this.companyInfoRepository = companyInfoRepository;
		this.rfidTagMasterRepository = rfidTagMasterRepository;
	}
	
	private CompletionHandler<Integer, Void> callback = new CompletionHandler<Integer, Void>() {
		@Override
		public void completed(Integer result, Void attachment) {
			System.out.println("Thread succuss !!!!");
		}
		
		@Override
		public void failed(Throwable exc, Void attachment) {
			System.out.println("Thread fail !!!!");
		}

	};

	public void doWork(final BartagMaster bartagMaster) {

		Runnable task = new Runnable() {
			@Override
			public void run() {
				
				try {
					
					List<RfidTagMaster> rfidTagList = new ArrayList<RfidTagMaster>();
					
					CompanyInfo companyInfo = companyInfoRepository.findByCustomerCode(bartagMaster.getProductionCompanyInfo().getCustomerCode());
			    	
					int start = Integer.parseInt(bartagMaster.getStartRfidSeq());
			    	int endAmount = Integer.parseInt(bartagMaster.getEndRfidSeq());
			    	
					for(int i=start; i<=endAmount; i++){
						
						RfidTagMaster rfidTag = new RfidTagMaster();
						
						rfidTag.setBartagSeq(bartagMaster.getBartagSeq());
						rfidTag.setErpKey(bartagMaster.getErpKey());
						rfidTag.setSeason(bartagMaster.getProductRfidYySeason());
						rfidTag.setOrderDegree(StringUtil.convertCipher("2", bartagMaster.getOrderDegree()));
						rfidTag.setCustomerCd(companyInfo.getCode());
						rfidTag.setPublishLocation("1");
						rfidTag.setRfidSeq(StringUtil.convertCipher("5", i));
						rfidTag.setStat("1");
						rfidTag.setRegDate(new Date());
						rfidTag.setCreateDate(bartagMaster.getCreateDate());
						rfidTag.setSeq(bartagMaster.getSeq());
						rfidTag.setLineSeq(bartagMaster.getLineSeq());
						
						rfidTagList.add(rfidTag);
					}
					
					rfidTagMasterRepository.save(rfidTagList);
					
				} catch (Exception e) {
					callback.failed(e, null);
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					callback.completed(null, null);
				}

			}
		};

		// 스레드풀에게 작업 요청
		executorService.submit(task);

	}

	public void finish() {
		executorService.shutdown(); // 스레드풀 종료
	}
	
}
