package com.example.demo;

import org.springframework.stereotype.Component;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Component
public class PredicateResolver {

	public Predicate categoryResolver(Root<Item> root, String value, Predicate predicate, CriteriaBuilder cb) {
		Join<Item, Category> categoryJoin = root.join("category", JoinType.INNER);	
		Expression<String> exp =  categoryJoin.get("name");
		predicate = cb.and(predicate,cb.like(cb.lower(exp), "%"+value.toLowerCase()+"%"));
		return predicate;
	}

}
