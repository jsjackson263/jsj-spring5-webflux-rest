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

import info.jsjackson.domain.Vendor;
import info.jsjackson.repositories.VendorRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author jsjackson
 *
 */
public class VendorControllerTest {

	WebTestClient webTestClient;
	
	
	VendorRepository vendorRepository;
	
	VendorController vendorController;
	
	
	@Before
	public void setUp() throws Exception {
	
		vendorRepository = Mockito.mock(VendorRepository.class);
		vendorController = new VendorController(vendorRepository);
		
		webTestClient = WebTestClient.bindToController(vendorController).build();
	}

	@Test
	public void testListAll() throws Exception {

		BDDMockito.given(vendorRepository.findAll())
		.willReturn(Flux.just(Vendor.builder().firstName("VendorFirstName1").lastName("VendorLastName1").build(),
				Vendor.builder().firstName("VendorFirstName2").lastName("VendorLastName2").build(),
				Vendor.builder().firstName("VendorFirstName3").lastName("VendorLastName3").build(),
				Vendor.builder().firstName("VendorFirstName4").lastName("VendorLastName4").build()));
	
		webTestClient.get().uri("/api/v1/vendors")
		.exchange()
		.expectBodyList(Vendor.class)
		.hasSize(4)
		.returnResult();
	}

	@Test
	public void testFindById() throws Exception {

		Vendor vendor  = Vendor.builder().firstName("VendorFirstName1").lastName("VendorLastName1").build();
		
		BDDMockito.given(vendorRepository.findById("someid"))
				.willReturn(Mono.just(vendor));
	
		webTestClient.get().uri("/api/v1/vendors/someid")
		.exchange()
		.expectStatus()
		.isOk()
		.expectBody(Vendor.class)
		.isEqualTo(vendor)
		.returnResult();
		
	}
	
	@Test
	public void testCreateVendor() throws Exception {

		BDDMockito.given(vendorRepository.saveAll(any(Publisher.class)))
		.willReturn(Flux.just(Vendor.builder().build()));
		
		Mono<Vendor> vendorMonoToSave = Mono.just(Vendor.builder()
				.firstName("SomeFirstName")
				.lastName("SomeLastName")
				.build());
		
		webTestClient.post()
		.uri("/api/v1/vendors")
		.body(vendorMonoToSave, Vendor.class)
		.exchange()
		.expectStatus()
		.isCreated();
		
	}

	
	@Test
	public void testUpdateVendor() throws Exception {
		
		BDDMockito.given(vendorRepository.save(any(Vendor.class)))
		.willReturn(Mono.just(Vendor.builder().build()));
		
		Mono<Vendor> vendorMonoToUpdate = Mono.just(Vendor.builder()
				.firstName("SomeFirstName")
				.lastName("SomeLastName")
				.build());
		
		webTestClient.put().uri("/api/v1/vendors/abcdef")
		.body(vendorMonoToUpdate, Vendor.class)
		.exchange().expectStatus()
		.isOk();
	}
	
	@Test
	public void testPatchVendorWithChanges() throws Exception {

		given(vendorRepository.findById(anyString()))
		.willReturn(Mono.just(Vendor.builder()
				.firstName("OldFirstName")
				.lastName("OldLastName").build()));
		
		
		given(vendorRepository.save(any(Vendor.class)))
		.willReturn(Mono.just(Vendor.builder().build()));
		
		Mono<Vendor> vendorMonoToUpdate = Mono.just(Vendor.builder()
				.firstName("NewFirstName")
				.lastName("NewLastName").build());
		
		webTestClient.patch()
		.uri("/api/v1/vendors/someid")
		.body(vendorMonoToUpdate, Vendor.class)
		.exchange()
		.expectStatus()
		.isOk();
		
		
		//make sure 'save' was run
		verify(vendorRepository).save(any());
	}
	
	@Test
	public void testPatchVendorNoChanges() throws Exception {

		given(vendorRepository.findById(anyString()))
		.willReturn(Mono.just(Vendor.builder()
				.firstName("OldFirstName")
				.lastName("OldLastName")
				.build()));
		
		
		given(vendorRepository.save(any(Vendor.class)))
		.willReturn(Mono.just(Vendor.builder().build()));
		
		Mono<Vendor> vendorMonoToUpdate = Mono.just(Vendor.builder()
				.firstName("OldFirstName")
				.lastName("OldLastName")
				.build());
		
		webTestClient.patch()
		.uri("/api/v1/vendors/someid")
		.body(vendorMonoToUpdate, Vendor.class)
		.exchange()
		.expectStatus()
		.isOk();
		
		
		//no changes, hence no save
		verify(vendorRepository, never()).save(any());
	}
	
	
}
