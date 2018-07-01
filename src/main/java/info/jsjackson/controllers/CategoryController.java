/**
 * 
 */
package info.jsjackson.controllers;

import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import info.jsjackson.domain.Category;
import info.jsjackson.repositories.CategoryRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author jsjackson
 *
 */
@RestController
public class CategoryController {
	
	/**
	 * NOTE: here we're returning back the reactive types
	 * TODO:externalise the urls
	 */
	private final CategoryRepository categoryRepository;

	/**
	 * @param categoryRepository
	 */
	public CategoryController(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	
	@GetMapping("/api/v1/categories")
	Flux<Category> list() {
		
		return categoryRepository.findAll();
		
	}
	
	
	@GetMapping("/api/v1/categories/{id}")
	Mono<Category> getById(@PathVariable String id) {
	
		return categoryRepository.findById(id);
	}
	
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/api/v1/categories")
	Mono<Void> create(@RequestBody Publisher<Category> categoryStream) {
		
		return categoryRepository.saveAll(categoryStream).then();
		
	}
	
	@PutMapping("/api/v1/categories/{id}")
	Mono<Category> update(@PathVariable String id, @RequestBody Category category) {  //not reactive
		category.setId(id);
		return categoryRepository.save(category);
		
	}

}
