package com.systemk.spyder.Repository.Main.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.ReleaseScheduleDetailLog;
import com.systemk.spyder.Entity.Main.ReleaseScheduleLog;

public class ReleaseScheduleLogSpecification {
	
	public static Specification<ReleaseScheduleLog> createDateEqual(final String createDate) {
        return new Specification<ReleaseScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<ReleaseScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("createDate"), createDate);
            }
        };
    }
	
	public static Specification<ReleaseScheduleLog> workLineEqual(final String workLine) {
        return new Specification<ReleaseScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<ReleaseScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("workLine"), workLine);
            }
        };
    }
	
	public static Specification<ReleaseScheduleLog> createDateBetween(final Date startDate, final Date endDate) {
        return new Specification<ReleaseScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<ReleaseScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("createDate"), startDate, endDate);
            }
        };
    }
	
	public static Specification<ReleaseScheduleLog> startCompanySeqEqual(final Long companySeq) {
        return new Specification<ReleaseScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<ReleaseScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("boxInfo").get("startCompanyInfo").get("companySeq"), companySeq);
            }
        };
    }
	
	public static Specification<ReleaseScheduleLog> endCompanySeqEqual(final Long companySeq) {
        return new Specification<ReleaseScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<ReleaseScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("boxInfo").get("endCompanyInfo").get("companySeq"), companySeq);
            }
        };
    }
	
	public static Specification<ReleaseScheduleLog> releaseYnEqual(final String completeYn) {
        return new Specification<ReleaseScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<ReleaseScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("releaseYn"), completeYn);
            }
        };
    }
	
	
	public static Specification<ReleaseScheduleLog> completeYnEqual(final String completeYn) {
        return new Specification<ReleaseScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<ReleaseScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("completeYn"), completeYn);
            }
        };
    }

	public static Specification<ReleaseScheduleLog> styleSearchEqual(final String style, final String color, final String size) {
        return new Specification<ReleaseScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<ReleaseScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	
            	List<Predicate> predicates = new ArrayList<Predicate>();
            	
            	Join<ReleaseScheduleLog, ReleaseScheduleDetailLog> joins = root.join("releaseScheduleDetailLog", JoinType.INNER);
            	
            	if(!style.equals("")){
            		predicates.add(cb.equal(joins.get("style"), style));
            	} 
            	
            	if(!color.equals("")){
            		predicates.add(cb.equal(joins.get("color"), color));
            	}
            	
            	if(!size.equals("")){
            		predicates.add(cb.equal(joins.get("size"), size));
            	}
            	
            	query.distinct(true);
            	
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
	
	public static Specification<ReleaseScheduleLog> barcodeBetween(final String startBarcode, final String endBarcode) {
        return new Specification<ReleaseScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<ReleaseScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("boxInfo").get("barcode"), startBarcode, endBarcode);
            }
        };
    }
	
	public static Specification<ReleaseScheduleLog> barcodeContain(final String barcode) {
        return new Specification<ReleaseScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<ReleaseScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("boxInfo").get("barcode"), "%" + barcode + "%");
            }
        };
    }
}
