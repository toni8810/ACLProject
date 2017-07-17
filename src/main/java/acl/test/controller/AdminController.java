package acl.test.controller;

import acl.test.domain.AdminPost;
import acl.test.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sun.security.acl.PermissionImpl;

import java.security.Principal;
import java.security.acl.Permission;

/**
 * Created by toni8810 on 15/07/17.
 */
@RestController()
@RequestMapping("/admin/posts")
public class AdminController extends GenericController<AdminPost> {

    @Autowired
    JdbcMutableAclService aclService;

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasPermission(#id, 'WRITE')")
    public void delete(@PathVariable Long id, Authentication authentication) {
        AdminPost adminPost = new AdminPost();
        adminPost.setId(id);
        AclPermissionEvaluator aclPermissionEvaluator = new AclPermissionEvaluator(aclService);
        final Permission permission = new PermissionImpl("WRITE");
        System.out.println(aclPermissionEvaluator.hasPermission(authentication, adminPost, permission));
        //repository.delete(id);
    }
}
