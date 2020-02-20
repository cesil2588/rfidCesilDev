package com.systemk.spyder.Repository.Main.Specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.StoreStorage;

public class StoreStorageSpecification {

	public static Specification<StoreStorage> companySeqEqual(final Long companySeq) {
        return new Specification<StoreStorage>() {
            @Override
            public Predicate toPredicate(Root<StoreStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("companyInfo").get("companySeq"), companySeq);
            }
        };
    }
	
	public static Specification<StoreStorage> regDateBetween(final Date startDate, final Date endDate) {
        return new Specification<StoreStorage>() {
            @Override
            public Predicate toPredicate(Root<StoreStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("regDate"), startDate, endDate);
            }
        };
    }
	
	public static Specification<StoreStorage> productYyEqual(final String productYy) {
        return new Specification<StoreStorage>() {
            @Override
            public Predicate toPredicate(Root<StoreStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("distributionStorage").get("productionStorage").get("bartagMaster").get("productYy"), productYy);
            }
        };
    }
	
	public static Specification<StoreStorage> productSeasonEqual(final String productSeason) {
        return new Specification<StoreStorage>() {
            @Override
            public Predicate toPredicate(Root<StoreStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("distributionStorage").get("productionStorage").get("bartagMaster").get("productSeason"), productSeason);
            }
        };
    }
	
	public static Specification<StoreStorage> erpKeyContain(final String erpKey) {
        return new Specification<StoreStorage>() {
            @Override
            public Predicate toPredicate(Root<StoreStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("distributionStorage").get("productionStorage").get("bartagMaster").get("erpKey"), "%" + erpKey + "%");
            }
        };
    }
	
	public static Specification<StoreStorage> erpKeyEqual(final String erpKey) {
        return new Specification<StoreStorage>() {
            @Override
            public Predicate toPredicate(Root<StoreStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("distributionStorage").get("productionStorage").get("bartagMaster").get("erpKey"), erpKey);
            }
        };
    }
	
	public static Specification<StoreStorage> styleContain(final String style) {
        return new Specification<StoreStorage>() {
            @Override
            public Predicate toPredicate(Root<StoreStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("distributionStorage").get("productionStorage").get("bartagMaster").get("style"), "%" + style + "%");
            }
        };
    }
	
	public static Specification<StoreStorage> styleEqual(final String style) {
        return new Specification<StoreStorage>() {
            @Override
            public Predicate toPredicate(Root<StoreStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("distributionStorage").get("productionStorage").get("bartagMaster").get("style"), style);
            }
        };
    }
	
	public static Specification<StoreStorage> colorContain(final String color) {
        return new Specification<StoreStorage>() {
            @Override
            public Predicate toPredicate(Root<StoreStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("distributionStorage").get("productionStorage").get("bartagMaster").get("color"), "%" + color + "%");
            }
        };
    }
	
	public static Specification<StoreStorage> colorEqual(final String color) {
        return new Specification<StoreStorage>() {
            @Override
            public Predicate toPredicate(Root<StoreStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("distributionStorage").get("productionStorage").get("bartagMaster").get("color"), color);
            }
        };
    }
	
	public static Specification<StoreStorage> sizeContain(final String size) {
        return new Specification<StoreStorage>() {
            @Override
            public Predicate toPredicate(Root<StoreStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("distributionStorage").get("productionStorage").get("bartagMaster").get("size"), "%" + size + "%");
            }
        };
    }
	
	public static Specification<StoreStorage> sizeEqual(final String size) {
        return new Specification<StoreStorage>() {
            @Override
            public Predicate toPredicate(Root<StoreStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("distributionStorage").get("productionStorage").get("bartagMaster").get("size"), size);
            }
        };
    }
	
	public static Specification<StoreStorage> orderDegreeEqual(final String orderDegree) {
        return new Specification<StoreStorage>() {
            @Override
            public Predicate toPredicate(Root<StoreStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("distributionStorage").get("productionStorage").get("bartagMaster").get("orderDegree"), orderDegree);
            }
        };
    }
	
	public static Specification<StoreStorage> distributionStorageSeqEqual(final Long distributionStorageSeq) {
        return new Specification<StoreStorage>() {
            @Override
            public Predicate toPredicate(Root<StoreStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("distributionStorage").get("distributionStorageSeq"), distributionStorageSeq);
            }
        };
    }
	
	public static Specification<StoreStorage> additionOrderDegreeEqual(final String additionOrderDegree) {
        return new Specification<StoreStorage>() {
            @Override
            public Predicate toPredicate(Root<StoreStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("distributionStorage").get("productionStorage").get("bartagMaster").get("additionOrderDegree"), additionOrderDegree);
            }
        };
    }

	public static Specification<StoreStorage> stockAmountGreaterThan(final Long stockAmount) {
        return new Specification<StoreStorage>() {
            @Override
            public Predicate toPredicate(Root<StoreStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.greaterThan(root.get("stat2Amount"), stockAmount);
            }
        };
    }
	
	public static Specification<StoreStorage> totalAmountGreaterThan(final Long amount) {
        return new Specification<StoreStorage>() {
            @Override
            public Predicate toPredicate(Root<StoreStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.greaterThan(root.get("totalAmount"), amount);
            }
        };
    }
}
