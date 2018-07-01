/**
 * 
 */
package info.jsjackson.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

}
