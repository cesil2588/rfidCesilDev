package com.systemk.spyder.Repository.Main.Specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.BartagOrder;

public class BartagOrderSpecification {
	
	public static Specification<BartagOrder> createDateEqual(final String createDate) {
        return new Specification<BartagOrder>() {
            @Override
            public Predicate toPredicate(Root<BartagOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("createDate"), createDate);
            }
        };
    }
	
	public static Specification<BartagOrder> regDateBetween(final Date startDate, final Date endDate) {
        return new Specification<BartagOrder>() {
            @Override
            public Predicate toPredicate(Root<BartagOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("regDate"), startDate, endDate);
            }
        };
    }
	
	public static Specification<BartagOrder> updDateBetween(final Date startDate, final Date endDate) {
        return new Specification<BartagOrder>() {
            @Override
            public Predicate toPredicate(Root<BartagOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("updDate"), startDate, endDate);
            }
        };
    }

	public static Specification<BartagOrder> companySeqEqual(final Long companySeq) {
        return new Specification<BartagOrder>() {
            @Override
            public Predicate toPredicate(Root<BartagOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("productionCompanyInfo").get("companySeq"), companySeq);
            }
        };
    }
	
	public static Specification<BartagOrder> detailProductionCompanyNameEqual(final String detailProductionCompanyName) {
        return new Specification<BartagOrder>() {
            @Override
            public Predicate toPredicate(Root<BartagOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("detailProductionCompanyName"), detailProductionCompanyName);
            }
        };
    }
	
	public static Specification<BartagOrder> statEqual(final String stat) {
        return new Specification<BartagOrder>() {
            @Override
            public Predicate toPredicate(Root<BartagOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("stat"), stat);
            }
        };
    }
	
	public static Specification<BartagOrder> productYyContain(final String productYy) {
        return new Specification<BartagOrder>() {
            @Override
            public Predicate toPredicate(Root<BartagOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("productYy"), "%" + productYy + "%");
            }
        };
    }
	
	public static Specification<BartagOrder> productYyEqual(final String productYy) {
        return new Specification<BartagOrder>() {
            @Override
            public Predicate toPredicate(Root<BartagOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("productYy"), productYy);
            }
        };
    }
	
	public static Specification<BartagOrder> productSeasonContain(final String productSeason) {
        return new Specification<BartagOrder>() {
            @Override
            public Predicate toPredicate(Root<BartagOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("productSeason"), "%" + productSeason + "%");
            }
        };
    }
	
	public static Specification<BartagOrder> productSeasonEqual(final String productSeason) {
        return new Specification<BartagOrder>() {
            @Override
            public Predicate toPredicate(Root<BartagOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("productSeason"), productSeason);
            }
        };
    }
	
	public static Specification<BartagOrder> erpKeyContain(final String erpKey) {
        return new Specification<BartagOrder>() {
            @Override
            public Predicate toPredicate(Root<BartagOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("erpKey"), "%" + erpKey + "%");
            }
        };
    }
	
	public static Specification<BartagOrder> styleContain(final String style) {
        return new Specification<BartagOrder>() {
            @Override
            public Predicate toPredicate(Root<BartagOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("style"), "%" + style + "%");
            }
        };
    }
	
	public static Specification<BartagOrder> styleEqual(final String style) {
        return new Specification<BartagOrder>() {
            @Override
            public Predicate toPredicate(Root<BartagOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("style"), style);
            }
        };
    }
	
	public static Specification<BartagOrder> colorContain(final String color) {
        return new Specification<BartagOrder>() {
            @Override
            public Predicate toPredicate(Root<BartagOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("color"), "%" + color + "%");
            }
        };
    }
	
	public static Specification<BartagOrder> colorEqual(final String color) {
        return new Specification<BartagOrder>() {
            @Override
            public Predicate toPredicate(Root<BartagOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("color"), color);
            }
        };
    }
	
	public static Specification<BartagOrder> sizeContain(final String size) {
        return new Specification<BartagOrder>() {
            @Override
            public Predicate toPredicate(Root<BartagOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("size"), "%" + size + "%");
            }
        };
    }
	
	public static Specification<BartagOrder> sizeEqual(final String size) {
        return new Specification<BartagOrder>() {
            @Override
            public Predicate toPredicate(Root<BartagOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("size"), size);
            }
        };
    }

	public static Specification<BartagOrder> detailProductionCompanyNameContain(final String detailProductionCompanyName) {
        return new Specification<BartagOrder>() {
            @Override
            public Predicate toPredicate(Root<BartagOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("detailProductionCompanyName"), "%" + detailProductionCompanyName + "%");
            }
        };
    }
	
}
