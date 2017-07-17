package acl.test.repository;

import acl.test.domain.PersonalPost;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by toni8810 on 15/07/17.
 */
public interface PersonalPostRepository extends CrudRepository<PersonalPost, Long> {
}
