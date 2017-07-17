package acl.test.repository;

import acl.test.domain.PublicPost;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by toni8810 on 15/07/17.
 */
public interface PublicPostRepository extends CrudRepository<PublicPost, Long> {
}
