package acl.test.repository;

import acl.test.domain.Farmer;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by toni8810 on 17/07/17.
 */
public interface FarmerRepository extends CrudRepository<Farmer, Long> {
}
