package acl.test.repository;

import acl.test.domain.AdminPost;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by toni8810 on 15/07/17.
 */

public interface AdminPostRepository extends CrudRepository<AdminPost, Long> {
}
