package com.systemk.spyder.Repository.Main.Specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.ProductionStorage;

public class ProductionStorageSpecification {

	public static Specification<ProductionStorage> customerCodeEqual(final String customerCd) {
        return new Specification<ProductionStorage>() {
            @Override
            public Predicate toPredicate(Root<ProductionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("bartagMaster").get("productionCompanyInfo").get("customerCode"), customerCd);
            }
        };
    }
	
	public static Specification<ProductionStorage> companySeqEqual(final Long companySeq) {
        return new Specification<ProductionStorage>() {
            @Override
            public Predicate toPredicate(Root<ProductionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("bartagMaster").get("productionCompanyInfo").get("companySeq"), companySeq);
            }
        };
    }
	
	public static Specification<ProductionStorage> regDateBetween(final Date startDate, final Date endDate) {
        return new Specification<ProductionStorage>() {
            @Override
            public Predicate toPredicate(Root<ProductionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("regDate"), startDate, endDate);
            }
        };
    }
	
	public static Specification<ProductionStorage> productYyEqual(final String productYy) {
        return new Specification<ProductionStorage>() {
            @Override
            public Predicate toPredicate(Root<ProductionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("bartagMaster").get("productYy"), productYy);
            }
        };
    }
	
	public static Specification<ProductionStorage> productSeasonEqual(final String productSeason) {
        return new Specification<ProductionStorage>() {
            @Override
            public Predicate toPredicate(Root<ProductionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("bartagMaster").get("productSeason"), productSeason);
            }
        };
    }
	
	public static Specification<ProductionStorage> erpKeyContain(final String erpKey) {
        return new Specification<ProductionStorage>() {
            @Override
            public Predicate toPredicate(Root<ProductionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("bartagMaster").get("erpKey"), "%" + erpKey + "%");
            }
        };
    }
	
	public static Specification<ProductionStorage> erpKeyEqual(final String erpKey) {
        return new Specification<ProductionStorage>() {
            @Override
            public Predicate toPredicate(Root<ProductionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("bartagMaster").get("erpKey"), erpKey);
            }
        };
    }
	
	public static Specification<ProductionStorage> styleContain(final String style) {
        return new Specification<ProductionStorage>() {
            @Override
            public Predicate toPredicate(Root<ProductionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("bartagMaster").get("style"), "%" + style + "%");
            }
        };
    }
	
	public static Specification<ProductionStorage> styleEqual(final String style) {
        return new Specification<ProductionStorage>() {
            @Override
            public Predicate toPredicate(Root<ProductionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("bartagMaster").get("style"), style);
            }
        };
    }
	
	public static Specification<ProductionStorage> colorContain(final String color) {
        return new Specification<ProductionStorage>() {
            @Override
            public Predicate toPredicate(Root<ProductionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("bartagMaster").get("color"), "%" + color + "%");
            }
        };
    }
	
	public static Specification<ProductionStorage> colorEqual(final String color) {
        return new Specification<ProductionStorage>() {
            @Override
            public Predicate toPredicate(Root<ProductionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("bartagMaster").get("color"), color);
            }
        };
    }
	
	public static Specification<ProductionStorage> sizeContain(final String size) {
        return new Specification<ProductionStorage>() {
            @Override
            public Predicate toPredicate(Root<ProductionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("bartagMaster").get("size"), "%" + size + "%");
            }
        };
    }
	
	public static Specification<ProductionStorage> sizeEqual(final String size) {
        return new Specification<ProductionStorage>() {
            @Override
            public Predicate toPredicate(Root<ProductionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("bartagMaster").get("size"), size);
            }
        };
    }

	public static Specification<ProductionStorage> detailProductionCompanyNameContain(final String detailProductionCompanyName) {
        return new Specification<ProductionStorage>() {
            @Override
            public Predicate toPredicate(Root<ProductionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("bartagMaster").get("detailProductionCompanyName"), "%" + detailProductionCompanyName + "%");
            }
        };
    }
	
	public static Specification<ProductionStorage> nonCheckAmountGreaterThan(final Long checkAmount) {
        return new Specification<ProductionStorage>() {
            @Override
            public Predicate toPredicate(Root<ProductionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.greaterThan(root.get("nonCheckAmount"), checkAmount);
            }
        };
    }
	
	public static Specification<ProductionStorage> stockAmountGreaterThan(final Long stockAmount) {
        return new Specification<ProductionStorage>() {
            @Override
            public Predicate toPredicate(Root<ProductionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.greaterThan(root.get("stockAmount"), stockAmount);
            }
        };
    }
	
	public static Specification<ProductionStorage> statNotEqual(final String stat) {
        return new Specification<ProductionStorage>() {
            @Override
            public Predicate toPredicate(Root<ProductionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.notEqual(root.get("bartagMaster").get("stat"), stat);
            }
        };
    }
}
