package com.systemk.spyder.Repository.Main.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.ProductionStorageRfidTag;
import com.systemk.spyder.Entity.Main.RfidTagMaster;

public class ProductionStorageRfidTagSpecification {

	public static Specification<ProductionStorageRfidTag> productionStorageSeqEqual(final Long productionStorageSeq) {
        return new Specification<ProductionStorageRfidTag>() {
            @Override
            public Predicate toPredicate(Root<ProductionStorageRfidTag> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("productionStorageSeq"), productionStorageSeq);
            }
        };
    }
	
	public static Specification<ProductionStorageRfidTag> customerCdEqual(final String customerCd) {
        return new Specification<ProductionStorageRfidTag>() {
            @Override
            public Predicate toPredicate(Root<ProductionStorageRfidTag> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("customerCd"), customerCd);
            }
        };
    }
	
	public static Specification<ProductionStorageRfidTag> statEqual(final String stat) {
        return new Specification<ProductionStorageRfidTag>() {
            @Override
            public Predicate toPredicate(Root<ProductionStorageRfidTag> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("stat"), stat);
            }
        };
    }
	
	public static Specification<ProductionStorageRfidTag> erpKeyEqual(final String erpKey) {
        return new Specification<ProductionStorageRfidTag>() {
            @Override
            public Predicate toPredicate(Root<ProductionStorageRfidTag> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("erpKey"), erpKey);
            }
        };
    }
	
	public static Specification<ProductionStorageRfidTag> startEndRfidSeqBetween(final String startRfidSeq, final String endRfidSeq) {
        return new Specification<ProductionStorageRfidTag>() {
            @Override
            public Predicate toPredicate(Root<ProductionStorageRfidTag> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("rfidSeq"), startRfidSeq, endRfidSeq);
            }
        };
    }
	
	public static Specification<ProductionStorageRfidTag> barcodeContaining(final String barcode) {
        return new Specification<ProductionStorageRfidTag>() {
            @Override
            public Predicate toPredicate(Root<ProductionStorageRfidTag> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("barcode"), "%" + barcode + "%");
            }
        };
    }
	
	public static Specification<ProductionStorageRfidTag> rfidTagContaining(final String rfidTag) {
        return new Specification<ProductionStorageRfidTag>() {
            @Override
            public Predicate toPredicate(Root<ProductionStorageRfidTag> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("rfidTag"), "%" + rfidTag + "%");
            }
        };
    }
}
