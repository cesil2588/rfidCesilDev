package com.systemk.spyder.Batch.ErpScheduleSyncBatch.StoreInventoryBatch;

import com.systemk.spyder.Entity.Main.InventoryScheduleHeader;
import com.systemk.spyder.Entity.Main.InventoryScheduleStyle;
import com.systemk.spyder.Entity.Main.ReleaseScheduleDetailLog;
import com.systemk.spyder.Entity.Main.ReleaseScheduleLog;
import com.systemk.spyder.Repository.External.RfidLe10IfRepository;
import com.systemk.spyder.Repository.External.RfidMd14IfRepository;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
public class StoreInventoryItemWriterListener implements ItemWriteListener<InventoryScheduleHeader>{

	private RfidMd14IfRepository rfidMd14IfRepository;

	public StoreInventoryItemWriterListener(RfidMd14IfRepository rfidMd14IfRepository){
		this.rfidMd14IfRepository = rfidMd14IfRepository;
	}

	@Override
	public void beforeWrite(List<? extends InventoryScheduleHeader> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterWrite(List<? extends InventoryScheduleHeader> items) {

		for(InventoryScheduleHeader header : items){
			for(InventoryScheduleStyle style : header.getStyleList()) {
				rfidMd14IfRepository.batchUpdate(new Date(),
												header.getCompanyInfo().getCustomerCode(),
												header.getCompanyInfo().getCornerCode(),
												header.getCreateDate(),
												header.getErpRegType(),
												style.getStyle(),
												style.getColor() + style.getSize());
			}

		}

	}

	@Override
	public void onWriteError(Exception exception, List<? extends InventoryScheduleHeader> items) {
		// TODO Auto-generated method stub

	}
}

