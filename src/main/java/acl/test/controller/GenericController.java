package acl.test.controller;

import acl.test.domain.Farm;
import acl.test.domain.MyEntity;
import acl.test.domain.Farmer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;


/**
 * Created by toni8810 on 15/07/17.
 */
public class GenericController<T extends MyEntity> {

    @Autowired
    protected CrudRepository<T,Long> repository;

    @Autowired
    protected JdbcMutableAclService aclService;

    //Checks if user has read permission on returned object
    @GetMapping("/{id}")
    @PostAuthorize("hasPermission(returnObject, 'ADMINISTRATION')")
    public T getOne(@PathVariable Long id) {
        System.out.println(repository.findOne(id));
        return repository.findOne(id);
    }
    //Check if user has read permission on each object in the list
    //if not the individual object will not be returned
    @GetMapping("")
    @PostFilter("hasPermission(filterObject, 'ADMINISTRATION')")
    public Iterable<T> getAll() {
        return repository.findAll();
    }

    //Check if user has write permission on the passed object
    //if not we will never step into the method body
    @PutMapping
    @PreAuthorize("hasPermission(#entity, 'ADMINISTRATION')")
    public T edit(@RequestBody T entity) {
        return repository.save(entity);
    }


}
