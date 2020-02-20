package com.systemk.spyder.Batch.ErpSyncBatch.ProductBatch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.systemk.spyder.Entity.Main.ProductMaster;
import com.systemk.spyder.Repository.Main.ProductMasterRepository;
import com.systemk.spyder.Service.RedisService;
import com.systemk.spyder.Util.StringUtil;

public class ProductItemWriter implements ItemWriter<ProductMaster> {

	private static final Logger log = LoggerFactory.getLogger(ProductItemWriter.class);
	
	private ProductMasterRepository productMasterRepository;
	
	private RedisService redisService;
	
	public ProductItemWriter(ProductMasterRepository productMasterRepository,
							 RedisService redisService){
		this.productMasterRepository = productMasterRepository;
		this.redisService = redisService;
	}
	
	@Override
	public void write(List<? extends ProductMaster> productList) throws Exception {
		
		List<ProductMaster> productMasterList = new ArrayList<ProductMaster>();
		
		for(ProductMaster productMaster : productList){
			// 등록
			if(productMaster.getStat().equals("C")){
				ProductMaster tempProductMaster = productMasterRepository.findByCreateDateAndCreateSeqAndCreateNo(productMaster.getCreateDate(), productMaster.getCreateSeq(), productMaster.getCreateNo());
				if(tempProductMaster == null){
					productMasterList.add(productMaster);
				}
			
			// 수정, 삭제
			} else if(productMaster.getStat().equals("U") || productMaster.getStat().equals("D")){
				ProductMaster tempProductMaster = productMasterRepository.findByStyleAndColorAndSize(productMaster.getStyle(), productMaster.getColor(), productMaster.getSize());
				productMaster.setProductSeq(tempProductMaster.getProductSeq());
				productMaster.setUpdDate(new Date());
				productMasterList.add(productMaster);
			} 
		}
		
		productMasterList = productMasterRepository.save(productMasterList);
		productMasterRepository.flush();
		
		for(ProductMaster productMaster : productMasterList){
			redisService.save("productList", productMaster.getErpKey(), StringUtil.convertJsonString(productMaster));
		}
		
		log.info("제품 마스터 배치 종료");
	}
}
