/**
 * 
 */
package info.jsjackson.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import info.jsjackson.domain.Category;

/**
 * @author jsjackson
 *
 */
public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {

}
