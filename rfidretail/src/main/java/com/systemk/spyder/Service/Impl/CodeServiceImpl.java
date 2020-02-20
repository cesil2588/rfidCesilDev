package com.systemk.spyder.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemk.spyder.Config.MultiDataBase.MainDataSourceConfig;
import com.systemk.spyder.Entity.Main.CodeInfo;
import com.systemk.spyder.Repository.Main.CodeInfoRepository;
import com.systemk.spyder.Service.CodeService;

@Service
public class CodeServiceImpl implements CodeService{

	@Autowired
	private JdbcTemplate template;
	
	@Autowired
	private CodeInfoRepository codeInfoRepository;
	
	@Autowired
	private MainDataSourceConfig mainDataSourceConfig;
	
	@Transactional
	@Override
	public CodeInfo save(CodeInfo codeInfo) throws Exception {
		CodeInfo returnCodeInfo = codeInfoRepository.save(codeInfo);
		
		return returnCodeInfo;
	}
	
	@Transactional
	@Override
	public boolean update(CodeInfo codeInfo) throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		boolean success = false;
		String query = "UPDATE dbo.code_info SET name = ?, code_value = ?, use_yn = ?, sort = ?, upd_date = getDate(), upd_user_seq = ? WHERE code_seq = ?";
		template.update(query, 
				codeInfo.getName(), 
				codeInfo.getCodeValue(),
				codeInfo.getUseYn(), 
				codeInfo.getSort(),
				codeInfo.getUpdUserSeq(),
				codeInfo.getCodeSeq());
		
		success = true;
        return success;
	}

	@Transactional
	@Override
	public boolean delete(CodeInfo codeInfo) throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		boolean success = false;
		String query = "DELETE FROM dbo.code_info WHERE code_seq = ?";
    	template.update(query, codeInfo.getCodeSeq());
    	
    	success = true;
		return success;
	}

	@Transactional
	@Override
	public boolean deleteCodeByParentCode(long parentCodeSeq) throws Exception {
		
		template.setDataSource(mainDataSourceConfig.mainDataSource());
		
		boolean success = false;
		String query = "DELETE FROM dbo.code_info WHERE parent_code_seq = ?";
    	template.update(query, parentCodeSeq);
    	success = true;
		return success;
	}


}
