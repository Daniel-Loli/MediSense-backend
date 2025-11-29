package com.medisense.medisense_back.security;

import com.medisense.medisense_back.Enum.RolEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Access {

    public boolean isAdmin() { return hasRole(RolEnum.ADMIN.name());}
    public boolean isEspecialista() { return  hasRole(RolEnum.ESPECIALISTA.name());}
    public boolean isAgente() {return hasRole(RolEnum.AGENTE.name());}

    public boolean isAll(){
        return isAdmin() || isEspecialista() || isAgente();
    }


    private boolean hasRole(String role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return auth.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals(role));
    }
}
