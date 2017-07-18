package acl.test.controller;

import acl.test.domain.Farmer;
import acl.test.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;


/**
 * Created by toni8810 on 15/07/17.
 */
public class GenericController<T extends User> {

    @Autowired
    protected CrudRepository<T,Long> repository;

    @Autowired
    protected JdbcMutableAclService aclService;

    //Not secured as pre/post authorize annotations do not work with no id
    @PostMapping
    @Transactional
    public T create(@RequestBody T entity, Principal principal) {
        // Prepare the information we'd like in our access control entry (ACE)
        T u = repository.save(entity);
        ObjectIdentity oi = new ObjectIdentityImpl(u.getClass(), u.getId());
        Sid sid = new PrincipalSid(principal.getName());
        Permission read = BasePermission.READ;
        Permission write = BasePermission.WRITE;
        // Create the relevant ACL
        MutableAcl mutableAcl = aclService.createAcl(oi);
        //If it is a Farmer permissions are inheriting from Distributor
        if (entity.getClass().getSimpleName().contentEquals("Farmer")) {
            mutableAcl.setEntriesInheriting(true);
            //finding parent object
            ObjectIdentity oiParent = new ObjectIdentityImpl(((Farmer) u).getDistributor().getClass(), ((Farmer) u).getDistributor().getId());
            mutableAcl.setParent(aclService.readAclById(oiParent));
        }
        else {
            mutableAcl.setEntriesInheriting(false);
        }
        //granting read and write permission
        mutableAcl.insertAce(mutableAcl.getEntries().size(), read, sid, true);
        mutableAcl.insertAce(mutableAcl.getEntries().size(), write, sid, true);
        // Now grant some permissions via an access control entry (ACE)
        aclService.updateAcl(mutableAcl);
        return u;
    }

    //Checks if user has read permission on returned object
    @GetMapping("/{id}")
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public T getOne(@PathVariable Long id) {
        System.out.println(repository.findOne(id));
        return repository.findOne(id);
    }
    //Check if user has read permission on each object in the list
    //if not the individual object will not be returned
    @GetMapping("")
    @PostFilter("hasPermission(filterObject, 'READ')")
    public Iterable<T> getAll() {
        return repository.findAll();
    }

    //Check if user has write permission on the passed object
    //if not we will never step into the method body
    @PutMapping
    @PreAuthorize("hasPermission(#entity, 'WRITE')")
    public T edit(@RequestBody T entity) {
        return repository.save(entity);
    }


}
