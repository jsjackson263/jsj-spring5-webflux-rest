/**
 * 
 */
package info.jsjackson.controllers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;

import info.jsjackson.domain.Category;
import info.jsjackson.repositories.CategoryRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author jsjackson
 *
 */
public class CategoryControllerTest {

	/*
	 * Note: a different way of dealing with mock objects
	 */
	WebTestClient webTestClient;
	
	CategoryRepository categoryRepository;
	
	
	CategoryController categoryController;
	
	
	@Before
	public void setUp() throws Exception {
		categoryRepository = Mockito.mock(CategoryRepository.class);
		categoryController = new CategoryController(categoryRepository);
		
		webTestClient = WebTestClient.bindToController(categoryController).build();
	}

	@Test
	public void testList() {

		BDDMockito.given(categoryRepository.findAll())
		.willReturn(Flux.just(Category.builder().description("Category 1").build(), 
				Category.builder().description("Category 2").build(), 
				Category.builder().description("Category 3").build()));
		
		webTestClient.get()
			.uri("/api/v1/categories/")
			.exchange()
			.expectBodyList(Category.class)
			.hasSize(3)
			.returnResult();
		
	}

	@Test
	public void testGetById() {

		BDDMockito.given(categoryRepository.findById("someid"))
		.willReturn(Mono.just(Category.builder().description("Category 1").build()));
		
		webTestClient.get()
		.uri("/api/v1/categories/someid")
		.exchange()
		.expectBody(Category.class)
		.returnResult();
		
	
	
	}

}
