package com.systemk.spyder.Repository.Main.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.ErpStoreReturnSchedule;

public class ErpStoreReturnScheduleSpecification {

	public static Specification<ErpStoreReturnSchedule> styleEqual(final String style) {
        return new Specification<ErpStoreReturnSchedule>() {
            @Override
            public Predicate toPredicate(Root<ErpStoreReturnSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("style"), style);
            }
        };
    }
	
	public static Specification<ErpStoreReturnSchedule> returnAnothercodeEqual(final String returnAnotherCode) {
        return new Specification<ErpStoreReturnSchedule>() {
            @Override
            public Predicate toPredicate(Root<ErpStoreReturnSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("returnAnotherCode"), returnAnotherCode);
            }
        };
    }

	
}
