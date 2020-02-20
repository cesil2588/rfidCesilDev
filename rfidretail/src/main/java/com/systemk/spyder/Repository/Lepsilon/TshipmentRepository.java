package com.systemk.spyder.Repository.Lepsilon;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systemk.spyder.Entity.Lepsilon.Tshipment;
import com.systemk.spyder.Entity.Lepsilon.Key.TshipmentKey;

public interface TshipmentRepository extends JpaRepository<Tshipment, TshipmentKey>{

	public Tshipment findByReferenceNo(String referenceNo);
	
	public boolean existsByReferenceNoAndStatus(String referenceNo, String status);
	
	public List<Tshipment> findByKeyWCodeAndEditWho(String wCode, String flag);
	
	public List<Tshipment> findByKeyWCode(String wCode);
	
	public List<Tshipment> findByKeyWCodeOrderByReferenceNoDesc(String wCode);
}
