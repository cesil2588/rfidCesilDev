package com.systemk.spyder.Service;

import com.systemk.spyder.Entity.Main.ParentCodeInfo;

public interface ParentCodeService {
	
	public ParentCodeInfo save(ParentCodeInfo parentCodeInfo) throws Exception;

	public boolean update(ParentCodeInfo parentCodeInfo) throws Exception;
	
	public boolean delete(ParentCodeInfo parentCodeInfo) throws Exception;
	
	public String findAll() throws Exception;
}
