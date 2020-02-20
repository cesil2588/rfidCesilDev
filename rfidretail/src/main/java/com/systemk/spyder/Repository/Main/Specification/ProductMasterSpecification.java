package com.systemk.spyder.Repository.Main.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.BartagMaster;
import com.systemk.spyder.Entity.Main.ProductMaster;
import com.systemk.spyder.Entity.Main.ProductionStorage;

public class ProductMasterSpecification {

	public static Specification<ProductMaster> productYyEqual(final String productYy) {
        return new Specification<ProductMaster>() {
            @Override
            public Predicate toPredicate(Root<ProductMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("productYy"), productYy);
            }
        };
    }
	
	public static Specification<ProductMaster> productSeasonEqual(final String productSeason) {
        return new Specification<ProductMaster>() {
            @Override
            public Predicate toPredicate(Root<ProductMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("productSeason"), productSeason);
            }
        };
    }
	
	public static Specification<ProductMaster> styleEqual(final String style) {
        return new Specification<ProductMaster>() {
            @Override
            public Predicate toPredicate(Root<ProductMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("style"), style);
            }
        };
    }
	
	public static Specification<ProductMaster> erpKeyContain(final String erpKey) {
        return new Specification<ProductMaster>() {
            @Override
            public Predicate toPredicate(Root<ProductMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("erpKey"), "%" + erpKey + "%");
            }
        };
    }
	
	public static Specification<ProductMaster> colorContain(final String color) {
        return new Specification<ProductMaster>() {
            @Override
            public Predicate toPredicate(Root<ProductMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("color"), "%" + color + "%");
            }
        };
    }
	
	public static Specification<ProductMaster> sizeContain(final String size) {
        return new Specification<ProductMaster>() {
            @Override
            public Predicate toPredicate(Root<ProductMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("size"), "%" + size + "%");
            }
        };
    }

}
