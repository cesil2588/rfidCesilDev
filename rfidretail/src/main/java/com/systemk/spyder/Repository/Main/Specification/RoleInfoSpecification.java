package com.systemk.spyder.Repository.Main.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.RoleInfo;

public class RoleInfoSpecification {

	public static Specification<RoleInfo> roleEqual(final String role) {
        return new Specification<RoleInfo>() {
            @Override
            public Predicate toPredicate(Root<RoleInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("role"), role);
            }
        };
    }
	
	public static Specification<RoleInfo> useYnEqual (final String useYn) {
        return new Specification<RoleInfo>() {
            @Override
            public Predicate toPredicate(Root<RoleInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("useYn"), useYn);
            }
        };
    }
	
	public static Specification<RoleInfo> roleNameContain (final String search) {
        return new Specification<RoleInfo>() {
          @Override
          public Predicate toPredicate(Root<RoleInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              return cb.like(root.get("roleName"), "%" + search + "%");
          }
        };
    }
}
