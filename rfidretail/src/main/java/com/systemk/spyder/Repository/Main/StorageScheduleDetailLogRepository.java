package com.systemk.spyder.Repository.Main;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systemk.spyder.Entity.Main.StorageScheduleDetailLog;

public interface StorageScheduleDetailLogRepository extends JpaRepository<StorageScheduleDetailLog, Long>{

	public List<StorageScheduleDetailLog> findByBarcode(String barcode); 
}
