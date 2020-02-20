package com.systemk.spyder.Repository.Main;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systemk.spyder.Entity.Main.BartagCreateDateCategory;

public interface BartagCreateDateCategoryRepository extends JpaRepository<BartagCreateDateCategory, Long>{

	public BartagCreateDateCategory findByCreateDate(String createDate);
	
	public List<BartagCreateDateCategory> findAllByOrderByCreateDateAsc();
}
