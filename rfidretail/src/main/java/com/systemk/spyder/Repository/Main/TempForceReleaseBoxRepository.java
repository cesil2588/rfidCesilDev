package com.systemk.spyder.Repository.Main;

import com.systemk.spyder.Service.CustomBean.ForceBoxGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import com.systemk.spyder.Entity.Main.TempForceReleaseBox;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TempForceReleaseBoxRepository extends JpaRepository<TempForceReleaseBox, Long>{

    @Query(value = "select new com.systemk.spyder.Service.CustomBean.ForceBoxGroup(f.style, f.color, f.size, count(f)) " +
                    "from TempForceReleaseBox f group by f.style, f.color, f.size ")
    public List<ForceBoxGroup> findByStyleGroupBy();
}
