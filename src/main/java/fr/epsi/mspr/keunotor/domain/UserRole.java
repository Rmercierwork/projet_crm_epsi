package fr.epsi.mspr.keunotor.domain;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ROLE_ADMIN, ROLE_STAFF, ROLE_SALESREP;

    @Override
    public String getAuthority() {
        return this.name();
    }
}