package com.selazzouzi.intranet.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.selazzouzi.intranet.core.utils.SearchOperation;
import com.selazzouzi.intranet.core.utils.SpecSearchCriteria;
import com.selazzouzi.intranet.model.User;

import org.springframework.data.jpa.domain.Specifications;

abstract public class abstractSpecificationsBuilder<T> extends AbstractSpecification{

	private final List<SpecSearchCriteria> params;

	 static SpecSearchCriteria criteria;
	
	public abstractSpecificationsBuilder() {
		super(criteria);
		params = new ArrayList<SpecSearchCriteria>();
	}

	public abstractSpecificationsBuilder with(final String key, final String operation, final Object value,
			final String prefix, final String suffix) {
		SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
		if (op != null) {
			if (op == SearchOperation.EQUALITY) // the operation may be complex
												// operation
			{
				final boolean startWithAsterisk = prefix.contains("*");
				final boolean endWithAsterisk = suffix.contains("*");
				System.out.println("prefix: "+prefix);
				System.out.println("suffix: "+suffix);
				if (startWithAsterisk && endWithAsterisk) {
					op = SearchOperation.CONTAINS;
				} else if (startWithAsterisk) {
					op = SearchOperation.ENDS_WITH;
				} else if (endWithAsterisk) {
					op = SearchOperation.STARTS_WITH;
				}
			}
			params.add(new SpecSearchCriteria(key, op, value));
		}
		return this;
	}

	public Specification<T> build() {

		if (params.size() == 0) {
			return null;
		}

		final List<Specification<T>> specs = new ArrayList<Specification<T>>();
		for (final SpecSearchCriteria param : params) {
			specs.add(new AbstractSpecification(param));
			//specs.add(new Specification(param));
		}

		Specification<T> result = specs.get(0);
		for (int i = 1; i < specs.size(); i++) {
			result = Specifications.where(result).and(specs.get(i));
		}
		return result;
	}
}
