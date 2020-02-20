package com.systemk.spyder.Repository.Main.Specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.BartagMaster;

public class BartagMasterSpecification {

	public static Specification<BartagMaster> customerCodeEqual(final String customerCd) {
        return new Specification<BartagMaster>() {
            @Override
            public Predicate toPredicate(Root<BartagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("productionCompanyInfo").get("customerCode"), customerCd);
            }
        };
    }
	
	public static Specification<BartagMaster> companySeqEqual(final Long companySeq) {
        return new Specification<BartagMaster>() {
            @Override
            public Predicate toPredicate(Root<BartagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("productionCompanyInfo").get("companySeq"), companySeq);
            }
        };
    }
	
	public static Specification<BartagMaster> bartagEndDateIsNull() {
        return new Specification<BartagMaster>() {
            @Override
            public Predicate toPredicate(Root<BartagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.isNull(root.get("bartagEndDate"));
            }
        };
    }
	
	public static Specification<BartagMaster> bartagEndDateNotNull() {
        return new Specification<BartagMaster>() {
            @Override
            public Predicate toPredicate(Root<BartagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.isNotNull(root.get("bartagEndDate"));
            }
        };
    }
	
	public static Specification<BartagMaster> regDateBetween(final Date startDate, final Date endDate) {
        return new Specification<BartagMaster>() {
            @Override
            public Predicate toPredicate(Root<BartagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("regDate"), startDate, endDate);
            }
        };
    }
	
	public static Specification<BartagMaster> createDateBetween(final String startDate, final String endDate) {
        return new Specification<BartagMaster>() {
            @Override
            public Predicate toPredicate(Root<BartagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("createDate"), startDate, endDate);
            }
        };
    }
	
	public static Specification<BartagMaster> createDateEqual(final String createDate) {
        return new Specification<BartagMaster>() {
            @Override
            public Predicate toPredicate(Root<BartagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("createDate"), createDate);
            }
        };
    }
	
	public static Specification<BartagMaster> productYyContain(final String productYy) {
        return new Specification<BartagMaster>() {
            @Override
            public Predicate toPredicate(Root<BartagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("productYy"), "%" + productYy + "%");
            }
        };
    }
	
	public static Specification<BartagMaster> productYyEqual(final String productYy) {
        return new Specification<BartagMaster>() {
            @Override
            public Predicate toPredicate(Root<BartagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("productYy"), productYy);
            }
        };
    }
	
	public static Specification<BartagMaster> productSeasonContain(final String productSeason) {
        return new Specification<BartagMaster>() {
            @Override
            public Predicate toPredicate(Root<BartagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("productSeason"), "%" + productSeason + "%");
            }
        };
    }
	
	public static Specification<BartagMaster> productSeasonEqual(final String productSeason) {
        return new Specification<BartagMaster>() {
            @Override
            public Predicate toPredicate(Root<BartagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("productSeason"), productSeason);
            }
        };
    }
	
	public static Specification<BartagMaster> erpKeyContain(final String erpKey) {
        return new Specification<BartagMaster>() {
            @Override
            public Predicate toPredicate(Root<BartagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("erpKey"), "%" + erpKey + "%");
            }
        };
    }
	
	public static Specification<BartagMaster> styleContain(final String style) {
        return new Specification<BartagMaster>() {
            @Override
            public Predicate toPredicate(Root<BartagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("style"), "%" + style + "%");
            }
        };
    }
	
	public static Specification<BartagMaster> styleEqual(final String style) {
        return new Specification<BartagMaster>() {
            @Override
            public Predicate toPredicate(Root<BartagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("style"), style);
            }
        };
    }
	
	public static Specification<BartagMaster> colorContain(final String color) {
        return new Specification<BartagMaster>() {
            @Override
            public Predicate toPredicate(Root<BartagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("color"), "%" + color + "%");
            }
        };
    }
	
	public static Specification<BartagMaster> colorEqual(final String color) {
        return new Specification<BartagMaster>() {
            @Override
            public Predicate toPredicate(Root<BartagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("color"), color);
            }
        };
    }
	
	public static Specification<BartagMaster> sizeContain(final String size) {
        return new Specification<BartagMaster>() {
            @Override
            public Predicate toPredicate(Root<BartagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("size"), "%" + size + "%");
            }
        };
    }
	
	public static Specification<BartagMaster> sizeEqual(final String size) {
        return new Specification<BartagMaster>() {
            @Override
            public Predicate toPredicate(Root<BartagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	query.orderBy(cb.asc(root.get("startRfidSeq")));
                return cb.equal(root.get("size"), size);
            }
        };
    }

	public static Specification<BartagMaster> detailProductionCompanyNameContain(final String detailProductionCompanyName) {
        return new Specification<BartagMaster>() {
            @Override
            public Predicate toPredicate(Root<BartagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("detailProductionCompanyName"), "%" + detailProductionCompanyName + "%");
            }
        };
    }
	
	public static Specification<BartagMaster> statGreaterThan(final String stat) {
        return new Specification<BartagMaster>() {
            @Override
            public Predicate toPredicate(Root<BartagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.greaterThan(root.get("stat"), stat);
            }
        };
    }
	
	public static Specification<BartagMaster> generateSeqYnEqual(final String generateSeqYn) {
        return new Specification<BartagMaster>() {
            @Override
            public Predicate toPredicate(Root<BartagMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("generateSeqYn"), generateSeqYn);
            }
        };
    }
	
}
