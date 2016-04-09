package com.selazzouzi.intranet.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.selazzouzi.intranet.model.Profile;

public class ProfileSpecification implements Specification<Profile> {

	private Profile filter;

	public ProfileSpecification(Profile filter) {
        super();
        this.filter = filter;
    }

	@Override
	public Predicate toPredicate(Root<Profile> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		
		Predicate p = cb.disjunction();

        if (filter.getFirstName()!=null && !filter.getFirstName().isEmpty()) {
            p.getExpressions()
                    .add(cb.equal(root.get("firstName"), filter.getFirstName()));
        }
        if (filter.getLastName()!=null && !filter.getLastName().isEmpty()) {
            p.getExpressions()
                    .add(cb.equal(root.get("lastName"), filter.getLastName()));
        }
        return p;
	}

}
