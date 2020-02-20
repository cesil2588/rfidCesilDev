package com.systemk.spyder.Repository.Main.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.systemk.spyder.Entity.Main.ReleaseScheduleLog;
import com.systemk.spyder.Entity.Main.StorageScheduleDetailLog;
import com.systemk.spyder.Entity.Main.StorageScheduleLog;

public class StorageScheduleLogSpecification {
	
	public static Specification<StorageScheduleLog> createDateEqual(final String createDate) {
        return new Specification<StorageScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<StorageScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("createDate"), createDate);
            }
        };
    }
	
	public static Specification<StorageScheduleLog> createDateBetween(final String startDate, final String endDate) {
        return new Specification<StorageScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<StorageScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("createDate"), startDate, endDate);
            }
        };
    }
	
	public static Specification<StorageScheduleLog> workLineEqual(final String workLine) {
        return new Specification<StorageScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<StorageScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("workLine"), workLine);
            }
        };
    }
	
	public static Specification<StorageScheduleLog> arrivalDateBetween(final String startDate, final String endDate) {
        return new Specification<StorageScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<StorageScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("arrivalDate"), startDate, endDate);
            }
        };
    }
	
	public static Specification<StorageScheduleLog> arrivalDateEqual(final String arrivalDate) {
        return new Specification<StorageScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<StorageScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("arrivalDate"), arrivalDate);
            }
        };
    }
	
	public static Specification<StorageScheduleLog> startCompanySeqEqual(final Long companySeq) {
        return new Specification<StorageScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<StorageScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("boxInfo").get("startCompanyInfo").get("companySeq"), companySeq);
            }
        };
    }
	
	public static Specification<StorageScheduleLog> endCompanySeqEqual(final Long companySeq) {
        return new Specification<StorageScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<StorageScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("boxInfo").get("endCompanyInfo").get("companySeq"), companySeq);
            }
        };
    }
	
	public static Specification<StorageScheduleLog> confirmYnEqual(final String confirmYn) {
        return new Specification<StorageScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<StorageScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("confirmYn"), confirmYn);
            }
        };
    }
	
	public static Specification<StorageScheduleLog> batchYnEqual(final String batchYn) {
        return new Specification<StorageScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<StorageScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("batchYn"), batchYn);
            }
        };
    }
	
	public static Specification<StorageScheduleLog> completeYnEqual(final String completeYn) {
        return new Specification<StorageScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<StorageScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("completeYn"), completeYn);
            }
        };
    }
	
	public static Specification<StorageScheduleLog> completeBatchYnEqual(final String completeBatchYnEqual) {
        return new Specification<StorageScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<StorageScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("completeBatchYn"), completeBatchYnEqual);
            }
        };
    }
	
	public static Specification<StorageScheduleLog> erpCompleteYnEqual(final String erpCompleteYn) {
        return new Specification<StorageScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<StorageScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("erpCompleteYn"), erpCompleteYn);
            }
        };
    }

	public static Specification<StorageScheduleLog> disuseYnEqual(final String disuseYn) {
        return new Specification<StorageScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<StorageScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("disuseYn"), disuseYn);
            }
        };
    }
	
	public static Specification<StorageScheduleLog> orderTypeEqual(final String orderType) {
        return new Specification<StorageScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<StorageScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("orderType"), orderType);
            }
        };
    }
	
	//반품 정보 안들어가도록.. order_type이 10-R와 같지 않은 데이터 조회 위해
	public static Specification<StorageScheduleLog> orderTypeNotEqual(final String orderType) {
        return new Specification<StorageScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<StorageScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.notEqual(root.get("orderType"), orderType);
            }
        };
    }
	
	public static Specification<StorageScheduleLog> styleSearchEqual(final String style, final String color, final String size) {
        return new Specification<StorageScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<StorageScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	
            	List<Predicate> predicates = new ArrayList<Predicate>();
            	
            	Join<StorageScheduleLog, StorageScheduleDetailLog> joins = root.join("storageScheduleDetailLog", JoinType.INNER);
            	
            	if(!style.equals("")){
            		predicates.add(cb.equal(joins.get("style"), style));
            	} 
            	
            	if(!color.equals("")){
            		predicates.add(cb.equal(joins.get("color"), color));
            	}
            	
            	if(!size.equals("")){
            		predicates.add(cb.equal(joins.get("size"), size));
            	}
            	
            	query.distinct(true);
            	
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
	
	public static Specification<StorageScheduleLog> barcodeBetween(final String startBarcode, final String endBarcode) {
        return new Specification<StorageScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<StorageScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("boxInfo").get("barcode"), startBarcode, endBarcode);
            }
        };
    }
	
	public static Specification<StorageScheduleLog> barcodeContain(final String barcode) {
        return new Specification<StorageScheduleLog>() {
            @Override
            public Predicate toPredicate(Root<StorageScheduleLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("boxInfo").get("barcode"), "%" + barcode + "%");
            }
        };
    }
}
