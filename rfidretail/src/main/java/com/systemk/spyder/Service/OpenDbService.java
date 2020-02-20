package com.systemk.spyder.Service;

import java.util.List;

import com.systemk.spyder.Dto.Request.ReleaseBean;
import com.systemk.spyder.Entity.Main.BoxInfo;
import com.systemk.spyder.Entity.Main.StorageScheduleLog;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Entity.OpenDb.OpenDbScheduleDetail;
import com.systemk.spyder.Entity.OpenDb.OpenDbScheduleHeader;
import com.systemk.spyder.Service.CustomBean.StorageScheduleDetailLogModel;
import com.systemk.spyder.Service.CustomBean.StyleModel;

public interface OpenDbService {

	public void saveOpenDbSchedule(List<BoxInfo> boxInfoList, UserInfo userInfo, ReleaseBean releaseBean, Long workLine, String flag) throws Exception;

	public OpenDbScheduleHeader saveOpenDbScheduleHeader(OpenDbScheduleHeader openDbHeader) throws Exception;

	public OpenDbScheduleDetail saveOpenDbScheduleDetail(OpenDbScheduleDetail openDbDetail) throws Exception;

	public OpenDbScheduleHeader setOpenDbScheduleHeader(BoxInfo boxInfo, String nowDate, String arrivalDate, String flag) throws Exception;

	public OpenDbScheduleDetail setOpenDbScheduleDetail(BoxInfo boxInfo, int lineNo, StyleModel style, String nowDate, String arrivalDate, String invoiceNum, String flag) throws Exception;

	public OpenDbScheduleDetail setOpenDbScheduleDetail(BoxInfo boxInfo, StorageScheduleDetailLogModel detailLog, String nowDate, String arrivalDate, String flag) throws Exception;

	public OpenDbScheduleHeader disuseOpenDbScheduleHeader(StorageScheduleLog storageScheduleLog) throws Exception;
}
