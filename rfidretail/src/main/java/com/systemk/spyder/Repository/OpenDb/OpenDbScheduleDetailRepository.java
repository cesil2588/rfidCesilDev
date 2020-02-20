package com.systemk.spyder.Repository.OpenDb;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systemk.spyder.Entity.OpenDb.OpenDbScheduleDetail;
import com.systemk.spyder.Entity.OpenDb.Key.OpenDbScheduleDetailKey;

public interface OpenDbScheduleDetailRepository extends JpaRepository<OpenDbScheduleDetail, OpenDbScheduleDetailKey>{

}
