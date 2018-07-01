/**
 * 
 */
package info.jsjackson.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import info.jsjackson.domain.Category;
import info.jsjackson.domain.Vendor;
import info.jsjackson.repositories.CategoryRepository;
import info.jsjackson.repositories.VendorRepository;

/**
 * @author jsjackson
 *
 */
@Component
public class Bootstrap implements CommandLineRunner {

	private final CategoryRepository categoryRepository;
	private final VendorRepository vendorRepository;
	
	/**
	 * @param categoryRepository
	 * @param vendorRepository
	 */
	public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
		this.categoryRepository = categoryRepository;
		this.vendorRepository = vendorRepository;
	}


	@Override
	public void run(String... args) throws Exception {

		//load data
		System.err.println("### LOADING DATA ON BOOTSTRAP ###");
		
		loadCategories();
		loadVendors();
		
	}

	
	
	private void loadCategories() {
		if (categoryRepository.count().block() == 0) {
			
			categoryRepository.save(Category.builder()
					.description("Fruits").build()).block();
			
			categoryRepository.save(Category.builder()
					.description("Nuts").build()).block();
			
			categoryRepository.save(Category.builder()
					.description("Breads").build()).block();
			
			categoryRepository.save(Category.builder()
					.description("Meats").build()).block();
			
			categoryRepository.save(Category.builder()
					.description("Eggs").build()).block();
			
			System.err.println("Loaded Categories: " + categoryRepository.count().block());
			
		}
	}
	
	
	private void loadVendors() {
		if (vendorRepository.count().block() == 0) {
			
			vendorRepository.save(Vendor.builder()
					.firstName("Fruits")
					.lastName("Buck")
					.build()).block();
			vendorRepository.save(Vendor.builder()
					.firstName("Michael")
					.lastName("Weston")
					.build()).block();
			vendorRepository.save(Vendor.builder()
					.firstName("Jessie")
					.lastName("Waters")
					.build()).block();
			vendorRepository.save(Vendor.builder()
					.firstName("Bill")
					.lastName("Nershi")
					.build()).block();
			vendorRepository.save(Vendor.builder()
					.firstName("Jimmy")
					.lastName("Buffet")
					.build()).block();
			
			
			System.err.println("Loaded Vendors: " + vendorRepository.count().block());
			
		}
	}
	
	
}
