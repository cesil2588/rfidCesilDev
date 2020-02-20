package com.systemk.spyder.Repository.Main.Specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.RfidTagMaster;

public class RfidTagMasterSpecification {

	public static Specification<RfidTagMaster> bartagSeqEqual(final Long bartagSeq) {
        return new Specification<RfidTagMaster>() {
            @Override
            public Predicate toPredicate(Root<RfidTagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("bartagSeq"), bartagSeq);
            }
        };
    }
	
	public static Specification<RfidTagMaster> statEqual(final String stat) {
        return new Specification<RfidTagMaster>() {
            @Override
            public Predicate toPredicate(Root<RfidTagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("stat"), stat);
            }
        };
    }
	
	public static Specification<RfidTagMaster> publishLocationContaining(final String publishLocation) {
        return new Specification<RfidTagMaster>() {
            @Override
            public Predicate toPredicate(Root<RfidTagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("publishLocation"), "%" + publishLocation + "%");
            }
        };
    }
	
	public static Specification<RfidTagMaster> publishRegDateContaining(final String publishRegDate) {
        return new Specification<RfidTagMaster>() {
            @Override
            public Predicate toPredicate(Root<RfidTagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("publishRegDate"), "%" + publishRegDate + "%");
            }
        };
    }
	
	public static Specification<RfidTagMaster> publishDegreeContaining(final String publishDegree) {
        return new Specification<RfidTagMaster>() {
            @Override
            public Predicate toPredicate(Root<RfidTagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("publishDegree"), "%" + publishDegree + "%");
            }
        };
    }
	
	public static Specification<RfidTagMaster> rfidSeqContaining(final String rfidSeq) {
        return new Specification<RfidTagMaster>() {
            @Override
            public Predicate toPredicate(Root<RfidTagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("rfidSeq"), "%" + rfidSeq + "%");
            }
        };
    }
	
	public static Specification<RfidTagMaster> statGreaterThan(final String stat) {
        return new Specification<RfidTagMaster>() {
            @Override
            public Predicate toPredicate(Root<RfidTagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.greaterThan(root.get("stat"), stat);
            }
        };
    }
}
