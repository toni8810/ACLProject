package acl.test.repository;

import acl.test.domain.Farm;
import org.springframework.data.repository.CrudRepository;

public interface FarmRepository extends CrudRepository<Farm, Long> {
}
