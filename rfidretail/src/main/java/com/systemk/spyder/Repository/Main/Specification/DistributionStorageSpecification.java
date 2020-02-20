package com.systemk.spyder.Repository.Main.Specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.DistributionStorage;
import com.systemk.spyder.Entity.Main.ProductionStorage;
import com.systemk.spyder.Entity.Main.StoreStorage;

public class DistributionStorageSpecification {

	public static Specification<DistributionStorage> productionCompanySeqEqual(final Long companySeq) {
        return new Specification<DistributionStorage>() {
            @Override
            public Predicate toPredicate(Root<DistributionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("productionStorage").get("companyInfo").get("companySeq"), companySeq);
            }
        };
    }
	
	public static Specification<DistributionStorage> companySeqEqual(final Long companySeq) {
        return new Specification<DistributionStorage>() {
            @Override
            public Predicate toPredicate(Root<DistributionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("distributionCompanyInfo").get("companySeq"), companySeq);
            }
        };
    }
	
	public static Specification<DistributionStorage> customerCodeEqual(final String customerCode) {
        return new Specification<DistributionStorage>() {
            @Override
            public Predicate toPredicate(Root<DistributionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("distributionCompanyInfo").get("customerCode"), customerCode);
            }
        };
    }
	
	public static Specification<DistributionStorage> regDateBetween(final Date startDate, final Date endDate) {
        return new Specification<DistributionStorage>() {
            @Override
            public Predicate toPredicate(Root<DistributionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("regDate"), startDate, endDate);
            }
        };
    }
	
	public static Specification<DistributionStorage> productYyEqual(final String productYy) {
        return new Specification<DistributionStorage>() {
            @Override
            public Predicate toPredicate(Root<DistributionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("productionStorage").get("bartagMaster").get("productYy"), productYy);
            }
        };
    }
	
	public static Specification<DistributionStorage> productSeasonEqual(final String productSeason) {
        return new Specification<DistributionStorage>() {
            @Override
            public Predicate toPredicate(Root<DistributionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("productionStorage").get("bartagMaster").get("productSeason"), productSeason);
            }
        };
    }
	
	public static Specification<DistributionStorage> erpKeyContain(final String erpKey) {
        return new Specification<DistributionStorage>() {
            @Override
            public Predicate toPredicate(Root<DistributionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("productionStorage").get("bartagMaster").get("erpKey"), "%" + erpKey + "%");
            }
        };
    }
	
	public static Specification<DistributionStorage> erpKeyEqual(final String erpKey) {
        return new Specification<DistributionStorage>() {
            @Override
            public Predicate toPredicate(Root<DistributionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("productionStorage").get("bartagMaster").get("erpKey"), erpKey);
            }
        };
    }
	
	public static Specification<DistributionStorage> styleContain(final String style) {
        return new Specification<DistributionStorage>() {
            @Override
            public Predicate toPredicate(Root<DistributionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("productionStorage").get("bartagMaster").get("style"), "%" + style + "%");
            }
        };
    }
	
	public static Specification<DistributionStorage> styleEqual(final String style) {
        return new Specification<DistributionStorage>() {
            @Override
            public Predicate toPredicate(Root<DistributionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("productionStorage").get("bartagMaster").get("style"), style);
            }
        };
    }
	
	public static Specification<DistributionStorage> colorContain(final String color) {
        return new Specification<DistributionStorage>() {
            @Override
            public Predicate toPredicate(Root<DistributionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("productionStorage").get("bartagMaster").get("color"), "%" + color + "%");
            }
        };
    }
	
	public static Specification<DistributionStorage> colorEqual(final String color) {
        return new Specification<DistributionStorage>() {
            @Override
            public Predicate toPredicate(Root<DistributionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("productionStorage").get("bartagMaster").get("color"), color);
            }
        };
    }
	
	public static Specification<DistributionStorage> sizeContain(final String size) {
        return new Specification<DistributionStorage>() {
            @Override
            public Predicate toPredicate(Root<DistributionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("productionStorage").get("bartagMaster").get("size"), "%" + size + "%");
            }
        };
    }
	
	public static Specification<DistributionStorage> sizeEqual(final String size) {
        return new Specification<DistributionStorage>() {
            @Override
            public Predicate toPredicate(Root<DistributionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("productionStorage").get("bartagMaster").get("size"), size);
            }
        };
    }

	public static Specification<DistributionStorage> stockAmountGreaterThan(final Long stockAmount) {
        return new Specification<DistributionStorage>() {
            @Override
            public Predicate toPredicate(Root<DistributionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.greaterThan(root.get("stat2Amount"), stockAmount);
            }
        };
    }
	
	public static Specification<DistributionStorage> totalAmountGreaterThan(final Long amount) {
        return new Specification<DistributionStorage>() {
            @Override
            public Predicate toPredicate(Root<DistributionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.greaterThan(root.get("totalAmount"), amount);
            }
        };
    }
	
	public static Specification<DistributionStorage> statNotEqual(final String stat) {
        return new Specification<DistributionStorage>() {
            @Override
            public Predicate toPredicate(Root<DistributionStorage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.notEqual(root.get("productionStorage").get("bartagMaster").get("stat"), stat);
            }
        };
    }
}
