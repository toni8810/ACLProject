package acl.test.controller;

import acl.test.domain.Distributor;
import acl.test.domain.Farmer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by toni8810 on 17/07/17.
 */
@RestController()
@RequestMapping("/distributors")
public class DistributorController extends GenericController<Distributor> {

}
