package com.systemk.spyder.Repository.Main.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.DistributionStorageRfidTag;

public class DistributionStorageRfidTagSpecification {

	public static Specification<DistributionStorageRfidTag> distributionStorageSeqEqual(final Long distributionStorageSeq) {
        return new Specification<DistributionStorageRfidTag>() {
            @Override
            public Predicate toPredicate(Root<DistributionStorageRfidTag> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("distributionStorageSeq"), distributionStorageSeq);
            }
        };
    }
	
	public static Specification<DistributionStorageRfidTag> customerCdEqual(final String customerCd) {
        return new Specification<DistributionStorageRfidTag>() {
            @Override
            public Predicate toPredicate(Root<DistributionStorageRfidTag> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("customerCd"), customerCd);
            }
        };
    }
	
	public static Specification<DistributionStorageRfidTag> statEqual(final String stat) {
        return new Specification<DistributionStorageRfidTag>() {
            @Override
            public Predicate toPredicate(Root<DistributionStorageRfidTag> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("stat"), stat);
            }
        };
    }
	
	public static Specification<DistributionStorageRfidTag> startEndRfidSeqBetween(final String startRfidSeq, final String endRfidSeq) {
        return new Specification<DistributionStorageRfidTag>() {
            @Override
            public Predicate toPredicate(Root<DistributionStorageRfidTag> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("rfidSeq"), startRfidSeq, endRfidSeq);
            }
        };
    }
	
	public static Specification<DistributionStorageRfidTag> rfidTagContaining(final String rfidTag) {
        return new Specification<DistributionStorageRfidTag>() {
            @Override
            public Predicate toPredicate(Root<DistributionStorageRfidTag> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("rfidTag"), "%" + rfidTag + "%");
            }
        };
    }
}
