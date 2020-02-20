package com.systemk.spyder.Repository.Main;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systemk.spyder.Entity.Main.ParentMenu;

public interface ParentMenuRepository extends JpaRepository<ParentMenu, Long>{
	public List<ParentMenu> findByUseYn(String useYn);
}
