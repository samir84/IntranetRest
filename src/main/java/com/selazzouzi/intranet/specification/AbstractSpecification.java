package com.selazzouzi.intranet.specification;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.ParameterizedType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.selazzouzi.intranet.core.utils.SearchCriteria;
import com.selazzouzi.intranet.core.utils.SpecSearchCriteria;
import com.selazzouzi.intranet.model.User;

abstract public class AbstractSpecification<T> implements Specification<T> {

	private SpecSearchCriteria criteria;

    public AbstractSpecification(final SpecSearchCriteria criteria) {
        super();
        this.criteria = criteria;
    }

    public SpecSearchCriteria getCriteria() {
        return criteria;
    }

    public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
        switch (criteria.getOperation()) {
        case EQUALITY:
            return builder.equal(root.get(criteria.getKey()), criteria.getValue());
        case NEGATION:
            return builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
        case GREATER_THAN:
            return builder.greaterThan(root.<String> get(criteria.getKey()), criteria.getValue().toString());
        case LESS_THAN:
            return builder.lessThan(root.<String> get(criteria.getKey()), criteria.getValue().toString());
        case LIKE:
            return builder.like(root.<String> get(criteria.getKey()), criteria.getValue().toString());
        case STARTS_WITH:
            return builder.like(root.<String> get(criteria.getKey()), criteria.getValue() + "%");
        case ENDS_WITH:
            return builder.like(root.<String> get(criteria.getKey()), "%" + criteria.getValue());
        case CONTAINS:
            return builder.like(root.<String> get(criteria.getKey()), "%" + criteria.getValue() + "%");
        default:
            return null;
        }
    }

}
