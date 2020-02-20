package com.systemk.spyder.Repository.External;

import com.systemk.spyder.Entity.External.Key.RfidMd14IfKey;
import com.systemk.spyder.Entity.External.RfidMd14If;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;

public interface RfidMd14IfRepository extends JpaRepository<RfidMd14If, RfidMd14IfKey>{

    @Modifying
    @Transactional
    @Query(value = "update rfid_md14_if set md14_tryn = 'Y', md14_trdt = :updDate " +
                    "where md14_mjcd = :md14Mjcd " +
                    "and md14_corn   = :md14Corn " +
                    "and md14_bsdt   = :md14Bsdt " +
                    "and md14_engb   = :md14Engb " +
                    "and md14_styl   = :md14Styl " +
                    "and md14_stcd   = :md14Stcd "
                    , nativeQuery = true)
    public int batchUpdate(@Param("updDate") Date updDate,
                           @Param("md14Mjcd") String customerCode,
                           @Param("md14Corn") String cornerCode,
                           @Param("md14Bsdt") String createDate,
                           @Param("md14Engb") String erpRegType,
                           @Param("md14Styl") String style,
                           @Param("md14Stcd") String anotherStyle);
}








