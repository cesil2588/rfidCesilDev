package com.systemk.spyder.Repository.External;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systemk.spyder.Entity.External.RfidTagIf;
import com.systemk.spyder.Entity.External.Key.RfidTagIfKey;

public interface RfidTagIfRepository extends JpaRepository<RfidTagIf, RfidTagIfKey>{

	public RfidTagIf findByKeyTagRfid(String tagId);

	public List<RfidTagIf> findByTagRfbcOrderByTagPrdtDesc(String tagRfbc);

}
