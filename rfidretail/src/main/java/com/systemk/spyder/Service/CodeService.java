package com.systemk.spyder.Service;

import com.systemk.spyder.Entity.Main.CodeInfo;

public interface CodeService {
	
	public CodeInfo save(CodeInfo codeInfo) throws Exception;

	public boolean update(CodeInfo codeInfo) throws Exception;
	
	public boolean delete(CodeInfo codeInfo) throws Exception;
	
	public boolean deleteCodeByParentCode(long parentCodeSeq) throws Exception;
	
}
