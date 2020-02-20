package com.systemk.spyder.Repository.Main.Specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.TempDistributionReleaseBox;
import com.systemk.spyder.Entity.Main.TempDistributionReleaseStyle;

public class TempDistributionReleaseBoxSpecification {
	
	public static Specification<TempDistributionReleaseBox> referenceNoEqual(final String referenceNo) {
		
        return new Specification<TempDistributionReleaseBox>() {
            @Override
            public Predicate toPredicate(Root<TempDistributionReleaseBox> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	
            	List<Predicate> predicates = new ArrayList<Predicate>();
            	
            	Join<TempDistributionReleaseBox, TempDistributionReleaseStyle> joins = root.join("styleList", JoinType.INNER);
            	
            	predicates.add(cb.equal(joins.get("referenceNo"), referenceNo));
            	
            	query.distinct(true);
            	
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
