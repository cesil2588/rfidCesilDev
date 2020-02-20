package com.systemk.spyder.Repository.Main.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.ErpStoreSchedule;

public class ErpStoreScheduleSpecification {

	public static Specification<ErpStoreSchedule> completeDateEqual(final String completeDate) {
        return new Specification<ErpStoreSchedule>() {
            @Override
            public Predicate toPredicate(Root<ErpStoreSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("completeDate"), completeDate);
            }
        };
    }
	
	public static Specification<ErpStoreSchedule> completeSeqEqual(final String completeSeq) {
        return new Specification<ErpStoreSchedule>() {
            @Override
            public Predicate toPredicate(Root<ErpStoreSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("completeSeq"), completeSeq);
            }
        };
    }
	
	public static Specification<ErpStoreSchedule> completeTypeEqual(final String completeType) {
        return new Specification<ErpStoreSchedule>() {
            @Override
            public Predicate toPredicate(Root<ErpStoreSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("completeType"), completeType);
            }
        };
    }
	
	public static Specification<ErpStoreSchedule> completeIfSeqEqual(final String completeIfSeq) {
        return new Specification<ErpStoreSchedule>() {
            @Override
            public Predicate toPredicate(Root<ErpStoreSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("completeIfSeq"), completeIfSeq);
            }
        };
    }
	
	public static Specification<ErpStoreSchedule> startCompanyCustomerCdEqual(final String customerCd) {
        return new Specification<ErpStoreSchedule>() {
            @Override
            public Predicate toPredicate(Root<ErpStoreSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("startCompanyInfo").get("customerCode"), customerCd);
            }
        };
    }
	
	public static Specification<ErpStoreSchedule> startCompanyCornerCdEqual(final String cornerCd) {
        return new Specification<ErpStoreSchedule>() {
            @Override
            public Predicate toPredicate(Root<ErpStoreSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("startCompanyInfo").get("cornerCode"), cornerCd);
            }
        };
    }
	
	public static Specification<ErpStoreSchedule> startCompanySeqEqual(final Long companySeq) {
        return new Specification<ErpStoreSchedule>() {
            @Override
            public Predicate toPredicate(Root<ErpStoreSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("startCompanyInfo").get("companySeq"), companySeq);
            }
        };
    }
	
	public static Specification<ErpStoreSchedule> endCompanyCustomerCdEqual(final String customerCd) {
        return new Specification<ErpStoreSchedule>() {
            @Override
            public Predicate toPredicate(Root<ErpStoreSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("endCompanyInfo").get("customerCode"), customerCd);
            }
        };
    }
	
	public static Specification<ErpStoreSchedule> endCompanyCornerCdEqual(final String cornerCd) {
        return new Specification<ErpStoreSchedule>() {
            @Override
            public Predicate toPredicate(Root<ErpStoreSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("endCompanyInfo").get("cornerCode"), cornerCd);
            }
        };
    }
	
	public static Specification<ErpStoreSchedule> endCompanySeqEqual(final Long companySeq) {
        return new Specification<ErpStoreSchedule>() {
            @Override
            public Predicate toPredicate(Root<ErpStoreSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("endCompanyInfo").get("companySeq"), companySeq);
            }
        };
    }
	
	public static Specification<ErpStoreSchedule> styleEqual(final String style) {
        return new Specification<ErpStoreSchedule>() {
            @Override
            public Predicate toPredicate(Root<ErpStoreSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("style"), style);
            }
        };
    }
	
	public static Specification<ErpStoreSchedule> anotherStyleEqual(final String color, final String size) {
        return new Specification<ErpStoreSchedule>() {
            @Override
            public Predicate toPredicate(Root<ErpStoreSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("anotherStyle"), color + size);
            }
        };
    }
	
	public static Specification<ErpStoreSchedule> colorEqual(final String color) {
        return new Specification<ErpStoreSchedule>() {
            @Override
            public Predicate toPredicate(Root<ErpStoreSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("color"), color);
            }
        };
    }
	
	public static Specification<ErpStoreSchedule> sizeEqual(final String size) {
        return new Specification<ErpStoreSchedule>() {
            @Override
            public Predicate toPredicate(Root<ErpStoreSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("size"), size);
            }
        };
    }
	
	public static Specification<ErpStoreSchedule> referenceNoEqual(final String referenceNo) {
        return new Specification<ErpStoreSchedule>() {
            @Override
            public Predicate toPredicate(Root<ErpStoreSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("referenceNo"), referenceNo);
            }
        };
    }
}
