package com.systemk.spyder.Service.Impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Entity.Main.AppDownloadLog;
import com.systemk.spyder.Entity.Main.AppInfo;
import com.systemk.spyder.Entity.Main.UserInfo;
import com.systemk.spyder.Repository.Main.AppDownloadLogRepository;
import com.systemk.spyder.Repository.Main.AppInfoRepository;
import com.systemk.spyder.Repository.Main.Specification.AppInfoSpecification;
import com.systemk.spyder.Security.LoginUser;
import com.systemk.spyder.Service.AppService;
import com.systemk.spyder.Util.CalendarUtil;
import com.systemk.spyder.Util.SecurityUtil;

@Service
public class AppServiceImpl implements AppService {

	@Autowired
	private AppInfoRepository appInfoRepository;

	@Autowired
	private JdbcTemplate template;

	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;

	@Autowired
	private AppDownloadLogRepository appDownloadLogRepository;

	@Autowired
    private Environment env;

	@Transactional(readOnly = true)
	@Override
	public Page<AppInfo> findAll(String startDate, String endDate, String type, String representYn, String search, String option, Pageable pageable) throws Exception {

		Date start = CalendarUtil.convertStartDate(startDate);
		Date end = CalendarUtil.convertEndDate(endDate);

		Page<AppInfo> page = null;
		Specifications<AppInfo> specifications = null;

		specifications = Specifications.where(AppInfoSpecification.useYnEqual("Y"));
		specifications = specifications.and(AppInfoSpecification.regDateBetween(start, end));

		if(!type.equals("all")){
			specifications = specifications.and(AppInfoSpecification.typeEqual(type));
		}

		if(!representYn.equals("all")){
			specifications = specifications.and(AppInfoSpecification.representYnEqual(representYn));
		}

		page = appInfoRepository.findAll(specifications, pageable);

		return page;
	}

	@Transactional
	@Override
	public AppInfo save(AppInfo appInfo) throws Exception {

		appInfo = appInfoRepository.save(appInfo);

		if(appInfo.getRepresentYn().equals("Y")){
			updateRepresentYn(appInfo);
		}
		return appInfo;
	}

	/**
	 * 대표번호 N으로 업데이트 처리
	 */
	@Transactional
	@Override
	public void updateRepresentYn(AppInfo appInfo) throws Exception {

		template.setDataSource(mainDataSourceConfig.mainDataSource());

		String query = "UPDATE dbo.app_info SET represent_yn = 'N' WHERE app_seq != ? AND type = ?";

		template.update(query, appInfo.getAppSeq(), appInfo.getType());
	}

	@Transactional(readOnly = true)
	@Override
	public Long countRepresentYn(String appType) throws Exception {
		return appInfoRepository.countByRepresentYnAndType("Y", appType);
	}

	@Transactional(readOnly = true)
	@Override
	public Long countByRepresentYnAndAppSeqNotIn(Long appSeq, String appType) throws Exception {
		return appInfoRepository.countByRepresentYnAndTypeAndAppSeqNotIn("Y", appType, appSeq);
	}

	@Transactional
	@Override
	public void saveLog(Long appSeq) throws Exception {

		LoginUser user = SecurityUtil.getCustomUser();

		if(user != null){
			UserInfo userInfo = new UserInfo();
			userInfo.setUserSeq(user.getUserSeq());

			AppInfo appInfo = appInfoRepository.findOne(appSeq);
			appInfo.setDownloadCount(appInfo.getDownloadCount() + 1);

			appInfoRepository.save(appInfo);

			AppDownloadLog appDownloadLog = new AppDownloadLog();

			appDownloadLog.setAppSeq(appSeq);
			appDownloadLog.setRegDate(new Date());
			appDownloadLog.setRegUserInfo(userInfo);

			appDownloadLogRepository.save(appDownloadLog);

		}
	}

	@Transactional(readOnly = true)
	@Override
	public AppInfo currentRepresentApp(String appType) throws Exception {
		return appInfoRepository.findByRepresentYnAndType("Y", appType);
	}

	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> versionCheck(String version, String appType) throws Exception {

		Map<String, Object> obj = new LinkedHashMap<String, Object>();

		if(version.equals("") || appType.equals("")) {
			obj.put("resultCode", ApiResultConstans.BAD_PARAMETER);
			obj.put("resultMessage", ApiResultConstans.BAD_PARAMETER_MESSAGE);
			return obj;
		}

		AppInfo appInfo = currentRepresentApp(appType);

		if(appInfo == null){
			obj.put("resultCode", ApiResultConstans.NOT_FIND_APP);
			obj.put("resultMessage", ApiResultConstans.NOT_FIND_APP_MESSAGE);
			return obj;
		}

		if(!version.equals(appInfo.getVersion())){

			obj.put("resultCode", ApiResultConstans.VERSION_UPDATE);
			obj.put("resultMessage", ApiResultConstans.VERSION_UPDATE_MESSAGE);
			obj.put("appDownloadUrl", env.getProperty("version.download.url"));
			return obj;
		}

		obj.put("resultCode", ApiResultConstans.SUCCESS);
		obj.put("resultMessage", ApiResultConstans.SUCCESS_MESSAGE);
		return obj;
	}
}
