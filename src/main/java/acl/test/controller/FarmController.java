package acl.test.controller;

import acl.test.domain.Farm;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/farms")
public class FarmController extends GenericController<Farm> {
}
