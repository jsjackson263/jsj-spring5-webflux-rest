/**
 * 
 */
package info.jsjackson.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
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
	public void testList() throws Exception {

		BDDMockito.given(categoryRepository.findAll())
		.willReturn(Flux.just(Category.builder().description("Category 1").build(), 
				Category.builder().description("Category 2").build(), 
				Category.builder().description("Category 3").build()));
		
		webTestClient.get()
			.uri("/api/v1/categories/")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBodyList(Category.class)
			.hasSize(3)
			.returnResult();
		
	}

	@Test
	public void testGetById() throws Exception {

		BDDMockito.given(categoryRepository.findById("someid"))
		.willReturn(Mono.just(Category.builder().description("Category 1").build()));
		
		webTestClient.get()
		.uri("/api/v1/categories/someid")
		.exchange()
		.expectStatus()
		.isOk()
		.expectBody(Category.class)
		.returnResult();
		
	}
	
	
	@Test
	public void testCreateCategory() throws Exception {

		BDDMockito.given(categoryRepository.saveAll(any(Publisher.class)))
		.willReturn(Flux.just(Category.builder().build()));
		
		Mono<Category> categoryMonoToSave = Mono.just(Category.builder().description("Some category description").build());
		
		webTestClient.post()
		.uri("/api/v1/categories")
		.body(categoryMonoToSave, Category.class)
		.exchange()
		.expectStatus()
		.isCreated();
		
	}
	
	@Test
	public void testUpdateCategory() throws Exception {

		BDDMockito.given(categoryRepository.save(any(Category.class)))
		.willReturn(Mono.just(Category.builder().build()));
		
		Mono<Category> categoryMonoToUpdate = Mono.just(Category.builder().description("Some category description").build());
		
		webTestClient.put()
		.uri("/api/v1/categories/abcdef")
		.body(categoryMonoToUpdate, Category.class)
		.exchange()
		.expectStatus()
		.isOk();
		
	}
	
	@Test
	public void testPatchCategoryWithChanges() throws Exception {

		given(categoryRepository.findById(anyString()))
		.willReturn(Mono.just(Category.builder().build()));
		
		
		given(categoryRepository.save(any(Category.class)))
		.willReturn(Mono.just(Category.builder().build()));
		
		Mono<Category> categoryMonoToUpdate = Mono.just(Category.builder().description("New description").build());
		
		webTestClient.patch()
		.uri("/api/v1/categories/someid")
		.body(categoryMonoToUpdate, Category.class)
		.exchange()
		.expectStatus()
		.isOk();
		
		
		//make sure 'save' was run
		verify(categoryRepository).save(any());
	}
	
	@Test
	public void testPatchCategoryNoChanges() throws Exception {

		given(categoryRepository.findById(anyString()))
		.willReturn(Mono.just(Category.builder().build()));
		
		
		given(categoryRepository.save(any(Category.class)))
		.willReturn(Mono.just(Category.builder().build()));
		
		Mono<Category> categoryMonoToUpdate = Mono.just(Category.builder().build());
		
		webTestClient.patch()
		.uri("/api/v1/categories/someid")
		.body(categoryMonoToUpdate, Category.class)
		.exchange()
		.expectStatus()
		.isOk();
		
		
		//no changes, hence no save
		verify(categoryRepository, never()).save(any());
	}
	

}
