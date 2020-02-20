package com.systemk.spyder.Service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.systemk.spyder.Entity.Main.BoxWorkGroup;

public interface BoxWorkGroupService {

	public Page<BoxWorkGroup> findAll(String startDate, String endDate, Long startCompanySeq, Long endCompanySeq, String stat, String type, Pageable pageable, String search) throws Exception;
	
	public boolean saveAll(List<BoxWorkGroup> boxWorkGroupList) throws Exception;
	
	public Map<String, Object> updateBoxGroupStat(List<BoxWorkGroup> boxWorkGroupList) throws Exception;
}
