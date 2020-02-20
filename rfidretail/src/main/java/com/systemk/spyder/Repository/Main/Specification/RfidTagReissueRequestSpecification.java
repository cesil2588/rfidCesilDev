package com.systemk.spyder.Repository.Main.Specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.ProductMaster;
import com.systemk.spyder.Entity.Main.RfidTagReissueRequest;

public class RfidTagReissueRequestSpecification {
	
	public static Specification<RfidTagReissueRequest> rfidTagReissueRequestSeqEqual(final Long seq) {
        return new Specification<RfidTagReissueRequest>() {
            @Override
            public Predicate toPredicate(Root<RfidTagReissueRequest> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("rfidTagReissueRequestSeq"), seq);
            }
        };
    }
	
	public static Specification<RfidTagReissueRequest> publishLocationEqual(final String publishLocation) {
        return new Specification<RfidTagReissueRequest>() {
            @Override
            public Predicate toPredicate(Root<RfidTagReissueRequest > root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("publishLocation"), publishLocation);
            }
        };
    }
	
	public static Specification<RfidTagReissueRequest> createDateEqual(final String createDate) {
        return new Specification<RfidTagReissueRequest>() {
            @Override
            public Predicate toPredicate(Root<RfidTagReissueRequest > root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("createDate"), createDate);
            }
        };
    }
	
	public static Specification<RfidTagReissueRequest> regDateBetween(final Date startDate, final Date endDate) {
        return new Specification<RfidTagReissueRequest>() {
            @Override
            public Predicate toPredicate(Root<RfidTagReissueRequest > root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("regDate"), startDate, endDate);
            }
        };
    }
	
	public static Specification<RfidTagReissueRequest> companySeqEqual(final Long companySeq) {
        return new Specification<RfidTagReissueRequest>() {
            @Override
            public Predicate toPredicate(Root<RfidTagReissueRequest> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("companyInfo").get("companySeq"), companySeq);
            }
        };
    }

    public static Specification<RfidTagReissueRequest> customerCodeEqual(final String customerCode) {
        return new Specification<RfidTagReissueRequest>() {
            @Override
            public Predicate toPredicate(Root<RfidTagReissueRequest> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("companyInfo").get("customerCode"), customerCode);
            }
        };
    }
	
	public static Specification<RfidTagReissueRequest> confirmYnEqual(final String confirmYn) {
        return new Specification<RfidTagReissueRequest>() {
            @Override
            public Predicate toPredicate(Root<RfidTagReissueRequest> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("confirmYn"), confirmYn);
            }
        };
    }
	
	public static Specification<RfidTagReissueRequest> completeYnEqual(final String completeYn) {
        return new Specification<RfidTagReissueRequest>() {
            @Override
            public Predicate toPredicate(Root<RfidTagReissueRequest> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("completeYn"), completeYn);
            }
        };
    }
	
	public static Specification<RfidTagReissueRequest> explanatoryContain(final String explanatory) {
        return new Specification<RfidTagReissueRequest>() {
            @Override
            public Predicate toPredicate(Root<RfidTagReissueRequest> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("explanatory"), "%" + explanatory + "%");
            }
        };
    }
}
