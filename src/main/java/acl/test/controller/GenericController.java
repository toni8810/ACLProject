package acl.test.controller;

import acl.test.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


/**
 * Created by toni8810 on 15/07/17.
 */
public class GenericController<T extends Post> {

    @Autowired
    protected CrudRepository<T,Long> repository;



    @PostMapping
    public T create(@RequestBody T entity) {
        return repository.save(entity);
    }

    @GetMapping("/{id}")
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public T getOne(@PathVariable Long id) {
        return repository.findOne(id);
    }

    @GetMapping("")
    @PostFilter("hasPermission(filterObject, 'READ')")
    public Iterable<T> getAll() {
        return repository.findAll();
    }

    @PutMapping
    @PreAuthorize("hasPermission(#entity, 'WRITE')")
    public T edit(@RequestBody T entity) {
        return repository.save(entity);
    }


}
