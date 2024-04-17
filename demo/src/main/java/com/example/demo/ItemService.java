package com.example.demo;

import org.springframework.data.domain.Pageable;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;

@Service
public class ItemService {
	
	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	PredicateResolver predicateResolver;

	public Page<Item> getItems(int page, int size, Map<String, String> filters) {
		Pageable pageable = PageRequest.of(page, size);
		
		Specification<Item> spec = (root, query, criteriaBuilder) -> {
			Predicate predicate = criteriaBuilder.conjunction();
			
			for (Map.Entry<String, String> entry : filters.entrySet()) {
				String key = entry.getKey();
				
				if (key.equals("category")) {
					predicate = predicateResolver.categoryResolver(root, entry.getValue(), predicate,
							criteriaBuilder);
				}
				if (key.equals("name")) {
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(
							criteriaBuilder.lower(root.get(key)), "%" + entry.getValue().toLowerCase() + "%"));
				}
			}
			return predicate;
		};
		return itemRepository.findAll(spec, pageable);
	}
	
	public Page<Item> getItems(Pageable pageable) {
		return itemRepository.findAll(pageable);
	}
	
	
}
