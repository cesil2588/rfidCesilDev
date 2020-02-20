package com.systemk.spyder.Repository.External;

import com.systemk.spyder.Entity.External.Key.RfidMd14RtIfKey;
import com.systemk.spyder.Entity.External.RfidMd14RtIf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;

public interface RfidMd14RtIfRepository extends JpaRepository<RfidMd14RtIf, RfidMd14RtIfKey> {

    @Modifying
    @Transactional
    @Query(value = "delete from rfid_md14rt_if " +
                    "where md14rt_mjcd = :customerCode " +
                      "and md14rt_corn   = :cornerCode " +
                      "and md14rt_bsdt   = :createDate ", nativeQuery = true)
    int batchDelete(@Param("customerCode") String customerCode,
                    @Param("cornerCode") String cornerCode,
                    @Param("createDate") String createDate);
}
