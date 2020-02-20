package com.systemk.spyder.Service.Impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Entity.Main.ParentCodeInfo;
import com.systemk.spyder.Repository.Main.ParentCodeInfoRepository;
import com.systemk.spyder.Service.CodeService;
import com.systemk.spyder.Service.ParentCodeService;
import com.systemk.spyder.Service.RedisService;
import com.systemk.spyder.Util.StringUtil;

@Service
public class ParentCodeServiceImpl implements ParentCodeService{

	@Autowired
	private JdbcTemplate template;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private ParentCodeInfoRepository parentCodeInfoRepository;
	
	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;
	
	@Transactional
	@Override
	public ParentCodeInfo save(ParentCodeInfo parentCodeInfo) throws Exception{
		ParentCodeInfo returnParentCodeInfo = parentCodeInfoRepository.save(parentCodeInfo);
		redisService.save("codeList", returnParentCodeInfo.getParentCodeSeq(), StringUtil.convertJsonString(returnParentCodeInfo));
		return returnParentCodeInfo;
	}

	@Transactional
	@Override
	public boolean update(ParentCodeInfo parentCodeInfo) throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		boolean success = false;
		String query = "UPDATE dbo.parent_code_info SET name = ?, code_value = ?, use_yn = ?, sort = ?, upd_date = getDate(), upd_user_seq = ? WHERE parent_code_seq = ?";
		template.update(query, 
				parentCodeInfo.getName(), 
				parentCodeInfo.getCodeValue(), 
				parentCodeInfo.getUseYn(), 
				parentCodeInfo.getSort(),
				parentCodeInfo.getUpdUserSeq(),
				parentCodeInfo.getParentCodeSeq());
		
		redisService.save("codeList", parentCodeInfo.getParentCodeSeq(), StringUtil.convertJsonString(parentCodeInfo));
		
		success = true;
        return success;
	}

	@Transactional
	@Override
	public boolean delete(ParentCodeInfo parentCodeInfo) throws Exception {

		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		boolean success = false;
		String query = "DELETE FROM dbo.parent_code_info WHERE parent_code_seq = ?";
		if(codeService.deleteCodeByParentCode(parentCodeInfo.getParentCodeSeq())){
			template.update(query, parentCodeInfo.getParentCodeSeq());
			redisService.delete("codeList", parentCodeInfo.getParentCodeSeq());
	    	success = true;
		}
		return success;
	}
	
	@Transactional
	@Override
	public String findAll() throws Exception {
		
		Map<Long, String> mapList = redisService.findAll("codeList");
		
		if(mapList.values().isEmpty()){
			for (ParentCodeInfo parendCode : parentCodeInfoRepository.findAllByOrderBySortAsc()) {
				redisService.save("codeList", parendCode.getParentCodeSeq(), StringUtil.convertJsonString(parendCode));
			}
			mapList = redisService.findAll("codeList");
		}
		 
		return mapList.values().toString();

	}
}
