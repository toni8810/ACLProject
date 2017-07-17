package acl.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * Created by toni8810 on 15/07/17.
 */
public class GenericController<T> {

    @Autowired
    protected CrudRepository<T,Long> repository;

    //Not secured as pre/post authorize annotations do not work with no id
    @PostMapping
    public T create(@RequestBody T entity) {
        return repository.save(entity);
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
