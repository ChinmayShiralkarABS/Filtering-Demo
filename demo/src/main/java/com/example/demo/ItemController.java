package com.example.demo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

@Controller
@RequestMapping("/api")
public class ItemController {

	@Autowired
	ItemService itemService;

	@Autowired
	PredicateResolver predicateResolver;

	@GetMapping
	public ResponseEntity<Page<Item>> getItems(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestBody(required = false) Map<String, String> filters) {

		Pageable pageable = PageRequest.of(page, size);
		Page<Item> items;
		if (filters != null) {
			items = itemService.getItems(page, size, filters);

		} else {
			items = itemService.getItems(pageable);
		}

		return ResponseEntity.ok(items);
	}

}
