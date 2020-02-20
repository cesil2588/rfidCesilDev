package com.systemk.spyder.Repository.Main.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.StoreStorageRfidTag;

public class StoreStorageRfidTagSpecification {

	public static Specification<StoreStorageRfidTag> storeStorageSeqEqual(final Long storeStorageSeq) {
        return new Specification<StoreStorageRfidTag>() {
            @Override
            public Predicate toPredicate(Root<StoreStorageRfidTag> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("storeStorageSeq"), storeStorageSeq);
            }
        };
    }
	
	public static Specification<StoreStorageRfidTag> customerCdEqual(final String customerCd) {
        return new Specification<StoreStorageRfidTag>() {
            @Override
            public Predicate toPredicate(Root<StoreStorageRfidTag> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("customerCd"), customerCd);
            }
        };
    }
	
	public static Specification<StoreStorageRfidTag> statEqual(final String stat) {
        return new Specification<StoreStorageRfidTag>() {
            @Override
            public Predicate toPredicate(Root<StoreStorageRfidTag> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("stat"), stat);
            }
        };
    }
	
	public static Specification<StoreStorageRfidTag> startEndRfidSeqBetween(final String startRfidSeq, final String endRfidSeq) {
        return new Specification<StoreStorageRfidTag>() {
            @Override
            public Predicate toPredicate(Root<StoreStorageRfidTag> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("rfidSeq"), startRfidSeq, endRfidSeq);
            }
        };
    }
	
	public static Specification<StoreStorageRfidTag> rfidTagContaining(final String rfidTag) {
        return new Specification<StoreStorageRfidTag>() {
            @Override
            public Predicate toPredicate(Root<StoreStorageRfidTag> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("rfidTag"), "%" + rfidTag + "%");
            }
        };
    }
}
