package com.algaworks.algafood.core.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public @interface Security {

    public @interface Kitchens {

        @PreAuthorize("isAuthenticated() "
                + "and hasAuthority('SCOPE_READ')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedConsult { }

        @PreAuthorize("isAuthenticated() "
                + "and hasAuthority('EDITAR_COZINHAS') "
                + "and hasAuthority('SCOPE_WRITE')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedEdit { }

    }

    public @interface Restaurants {

        @PreAuthorize("isAuthenticated() "
                + "and hasAuthority('SCOPE_READ')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedConsult { }

        @PreAuthorize("isAuthenticated() "
                + "and hasAuthority('EDITAR_RESTAURANTES') "
                + "and hasAuthority('SCOPE_WRITE')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedEdit { }

        @PreAuthorize("isAuthenticated() "
                + "and (hasAuthority('EDITAR_RESTAURANTES') or @algaSecurity.isUserManager(#restaurantId)) "
                + "and hasAuthority('SCOPE_WRITE')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedUserManager { }

    }

    public @interface Orders {

        @PreAuthorize("isAuthenticated() "
                + "and (hasAuthority('CONSULTAR_PEDIDOS') or @algaSecurity.getUserId() == #orderFilter.customerId or @algaSecurity.isUserManager(#orderFilter.restaurantId)) "
                + "and hasAuthority('SCOPE_READ')")     
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedConsultAll { }

        @PreAuthorize("isAuthenticated() "
                + "and hasAuthority('SCOPE_READ')")
        @PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') "
                + "or @algaSecurity.getUserId() == returnObject.customer.id "
                + "or @algaSecurity.isUserManager(returnObject.restaurant.id)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedConsultUnique { }

        @PreAuthorize("isAuthenticated() "
                + "and hasAuthority('SCOPE_WRITE')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedEdit { }

        @PreAuthorize("isAuthenticated() "
                + "and (hasAuthority('GERENCIAR_PEDIDOS') or @algaSecurity.managedRestaurantOfOrder(#code))")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedManageOrder { }

    }

    public @interface PaymentForms {

        @PreAuthorize("isAuthenticated() "
                + "and hasAuthority('SCOPE_READ')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedConsult { }

        @PreAuthorize("isAuthenticated() "
                + "and hasAuthority('EDITAR_FORMAS_PAGAMENTO') "
                + "and hasAuthority('SCOPE_WRITE')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedEdit { }

    }

    public @interface Cities {

        @PreAuthorize("isAuthenticated() "
                + "and hasAuthority('SCOPE_READ')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedConsult { }

        @PreAuthorize("isAuthenticated() "
                + "and hasAuthority('EDITAR_CIDADES') "
                + "and hasAuthority('SCOPE_WRITE')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedEdit { }

    }

    public @interface States {

        @PreAuthorize("isAuthenticated() "
                + "and hasAuthority('SCOPE_READ')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedConsult { }

        @PreAuthorize("isAuthenticated() "
                + "and hasAuthority('EDITAR_ESTADOS') "
                + "and hasAuthority('SCOPE_WRITE')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedEdit { }

    }

}
