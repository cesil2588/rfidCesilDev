package com.systemk.spyder.Batch.ErpOrderBatch.MoveOrderListSaveBatch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.External.RfidSd06If;
import com.systemk.spyder.Entity.Main.ErpStoreMove;

@Component
public class MoveOrderListItemProcessor implements ItemProcessor<RfidSd06If, ErpStoreMove> {


	public MoveOrderListItemProcessor(){
	}

    @Override
    public ErpStoreMove process(RfidSd06If rfidSd06If) throws Exception {

    	ErpStoreMove erpStoreMove = new ErpStoreMove();
    	
    	erpStoreMove.setOrderRegDate(rfidSd06If.getKey().getSd06Iddt());
    	erpStoreMove.setOrderSeq(rfidSd06If.getKey().getSd06Idsq());
    	erpStoreMove.setOrderSerial(rfidSd06If.getKey().getSd06Idsr());
    	erpStoreMove.setFromCustomerCode(rfidSd06If.getSd06Frcd());
    	erpStoreMove.setFromCornerCode(rfidSd06If.getSd06Frco());
    	erpStoreMove.setToCustomerCode(rfidSd06If.getSd06Tocd());
    	erpStoreMove.setToCornerCode(rfidSd06If.getSd06Toco());
    	erpStoreMove.setOrderAmount(rfidSd06If.getSd06Idqt());
    	erpStoreMove.setExecuteAmount(0L);
    	erpStoreMove.setConfirmAmount(0L);
    	erpStoreMove.setMoveType("1");
    	erpStoreMove.setFromCompleteYn("N");
    	erpStoreMove.setToCompleteYn("N");
    	erpStoreMove.setSku(rfidSd06If.getSd06Styl()+rfidSd06If.getSd06Stcd());
    	//rfid여부 ERP 확인후 추가
    	
        return erpStoreMove;
    }

	
}

