/**
 * 
 */
package info.jsjackson.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import info.jsjackson.domain.Vendor;

/**
 * @author jsjackson
 *
 */
public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {

}
