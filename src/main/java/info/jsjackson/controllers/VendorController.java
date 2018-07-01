/**
 * 
 */
package info.jsjackson.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import info.jsjackson.domain.Vendor;
import info.jsjackson.repositories.VendorRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author jsjackson
 *
 */
@RestController
public class VendorController {

	private final VendorRepository vendorRepository;

	/**
	 * @param vendorRepository
	 */
	public VendorController(VendorRepository vendorRepository) {
		super();
		this.vendorRepository = vendorRepository;
	}
	
	
	@GetMapping("/api/v1/vendors")
	public Flux<Vendor> listAll() {
		return vendorRepository.findAll();
	}
	
	
	@GetMapping("/api/v1/vendors/{id}")
	public Mono<Vendor> findById(@PathVariable String id) {
		
		return vendorRepository.findById(id);
		
	}
	
}
