package acl.test.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionCacheOptimizer;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.DataSource;


/**
 * Created by toni8810 on 14/07/17.
 */
//Enabling PreAuthorize and PostAuthorize.... annotations
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ACLConfig {

    @Autowired
    private DataSource dataSource;

/*    Creating AclCache bean
    Dependency needed:
    	<dependency>
			<groupId>net.sf.ehcache</groupId>
			<artifactId>ehcache</artifactId>
			<version>Latest Version</version>
		</dependency>*/
    @Bean
    public EhCacheBasedAclCache aclCache() {
        return new EhCacheBasedAclCache(aclEhCacheFactoryBean().getObject(), permissionGrantingStrategy(), aclAuthorizationStrategy());
    }
/*    Dependency needed:
        <dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-context-support</artifactId>
		</dependency>*/
    @Bean
    public EhCacheFactoryBean aclEhCacheFactoryBean() {
        EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
        ehCacheFactoryBean.setCacheManager(aclCacheManager().getObject());
        ehCacheFactoryBean.setCacheName("aclCache");
        return ehCacheFactoryBean;
    }

    @Bean
    public EhCacheManagerFactoryBean aclCacheManager() {
        return new EhCacheManagerFactoryBean();
    }

/*    Dependency needed:
        <dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-acl</artifactId>
			<version>4.2.3.RELEASE</version>
		</dependency>*/
    @Bean
    public PermissionGrantingStrategy permissionGrantingStrategy() {
        ConsoleAuditLogger consoleAuditLogger = new ConsoleAuditLogger();
        return new DefaultPermissionGrantingStrategy(consoleAuditLogger);
    }
/*    The 3 roles defined here:
        role 1: authority needed to change ownership
        role 2: authority needed to modify auditing details
        role 3: authority needed to change other ACL and ACE details */
    @Bean
    public AclAuthorizationStrategy aclAuthorizationStrategy() {
        return new AclAuthorizationStrategyImpl(
                new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Bean
    public LookupStrategy lookupStrategy() {
        return new BasicLookupStrategy(dataSource, aclCache(), aclAuthorizationStrategy(), new ConsoleAuditLogger());
    }

    @Bean
    public JdbcMutableAclService aclService() {
        JdbcMutableAclService service = new JdbcMutableAclService(dataSource, lookupStrategy(), aclCache());
        return service;
    }


    @Bean
    public MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler securityExpressionHandler = new DefaultMethodSecurityExpressionHandler();
        securityExpressionHandler.setPermissionEvaluator(new AclPermissionEvaluator(aclService()));
        securityExpressionHandler.setPermissionCacheOptimizer(new AclPermissionCacheOptimizer(aclService()));
        return securityExpressionHandler;
    }

}
