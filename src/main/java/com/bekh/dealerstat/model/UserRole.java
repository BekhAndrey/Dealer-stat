package com.bekh.dealerstat.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum UserRole implements GrantedAuthority {
    ROLE_ADMIN("ADMIN"),
    ROLE_TRADER("TRADER"),
    ROLE_ANONYMOUS("ANONYMOUS");

    private String roleName;

    UserRole(String roleName){
        this.roleName=roleName;
    }
    @Override
    public String getAuthority() {
        return getRoleName();
    }
}
