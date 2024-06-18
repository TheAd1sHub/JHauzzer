package com.skilkihodin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

public final class RolesConfig {

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.fromHierarchy("""
                        CUSTOMER > ROLE_UNAUTHENTICATED
                        VIP_CUSTOMER > CUSTOMER
                        ADMIN > VIP_CUSTOMER
                        ADMIN > WAREHOUSE_ADMIN"""
        );
    }

}
