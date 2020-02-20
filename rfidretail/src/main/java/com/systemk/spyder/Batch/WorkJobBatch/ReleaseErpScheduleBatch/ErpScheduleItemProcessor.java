package com.systemk.spyder.Batch.WorkJobBatch.ReleaseErpScheduleBatch;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.systemk.spyder.Entity.Main.ErpStoreSchedule;
import com.systemk.spyder.Entity.Main.TempDistributionReleaseBox;
import com.systemk.spyder.Entity.Main.TempDistributionReleaseStyle;
import com.systemk.spyder.Repository.Main.TempDistributionReleaseBoxRepository;

@Component
public class ErpScheduleItemProcessor implements ItemProcessor<ErpStoreSchedule, ErpStoreSchedule> {

private static final Logger log = LoggerFactory.getLogger(ErpScheduleItemProcessor.class);
	
	private TempDistributionReleaseBoxRepository tempDistributionReleaseBoxRepository;
	
	public ErpScheduleItemProcessor(TempDistributionReleaseBoxRepository tempDistributionReleaseBoxRepository){
		this.tempDistributionReleaseBoxRepository = tempDistributionReleaseBoxRepository;
	}
	
	@Override 
    public ErpStoreSchedule process(ErpStoreSchedule schedule) throws Exception {
		
		List<TempDistributionReleaseBox> tempReleaseBoxList = tempDistributionReleaseBoxRepository.findByCompleteBox(schedule.getReferenceNo(), schedule.getStyle(), schedule.getColor(), schedule.getSize());
		
		boolean completeCheckFlag = true;
		
		for(TempDistributionReleaseBox tempReleaseBox : tempReleaseBoxList) {
			
			loop : 
			for(TempDistributionReleaseStyle tempReleaseStyle : tempReleaseBox.getStyleList()) {
				if(schedule.getReferenceNo().equals(tempReleaseStyle.getReferenceNo()) &&
				   !(tempReleaseBox.getCompleteYn().equals("Y") && tempReleaseBox.getBatchYn().equals("Y") && tempReleaseBox.getWmsCompleteYn().equals("Y"))) {
					completeCheckFlag = false;
					break loop;
				}
			}

		}
		
		if(completeCheckFlag) {
			schedule.setCompleteCheckYn("Y");
			schedule.setCompleteCheckDate(new Date());
		} 
		
        return schedule;
    }
}

