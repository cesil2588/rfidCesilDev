package com.systemk.spyder.Repository.OpenDb;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systemk.spyder.Entity.OpenDb.OpenDbScheduleHeader;
import com.systemk.spyder.Entity.OpenDb.Key.OpenDbScheduleHeaderKey;

public interface OpenDbScheduleHeaderRepository extends JpaRepository<OpenDbScheduleHeader, OpenDbScheduleHeaderKey>{

}
