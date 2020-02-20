package com.systemk.spyder.Service;

import java.util.List;

import com.systemk.spyder.Service.CustomBean.MobileInspectionRfidTag;
import com.systemk.spyder.Service.CustomBean.MobileInspectionStyle;
import com.systemk.spyder.Service.CustomBean.MobileReleaseBox;
import com.systemk.spyder.Service.CustomBean.MobileReleaseRfidTag;
import com.systemk.spyder.Service.CustomBean.MobileReleaseStyle;

public interface MobileService {
	 
	public List<MobileInspectionStyle> inspectionStyleList(Long companySeq) throws Exception;

	public List<MobileInspectionRfidTag> inspectionRfidTagList(String stat, Long companySeq) throws Exception;
	
	public List<MobileReleaseStyle> releaseStyleList(Long companySeq) throws Exception;

	public List<MobileReleaseRfidTag> releaseRfidTagList(String stat, Long companySeq) throws Exception;
	
	public List<MobileReleaseBox> releaseBoxList(Long companySeq) throws Exception;
}
