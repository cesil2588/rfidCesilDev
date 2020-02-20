package com.systemk.spyder.Repository.Main;

import org.springframework.data.jpa.repository.JpaRepository;
import com.systemk.spyder.Entity.Main.MenuMapping;

public interface MenuMappingRepository extends JpaRepository<MenuMapping, Long> {
	public void deleteByRoleInfoRole(String role);
}
