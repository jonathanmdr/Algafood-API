package com.algaworks.algafood.core.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public @interface Security {

    public @interface Kitchens {

        @PreAuthorize("@algaSecurity.canConsultingKitchens()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedConsult {
            
        }

        @PreAuthorize("isAuthenticated() and hasAuthority('EDITAR_COZINHAS') and hasAuthority('SCOPE_WRITE')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedEdit {
            
        }

    }

    public @interface Restaurants {

        @PreAuthorize("@algaSecurity.canConsultingRestaurants()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedConsult {
            
        }

        @PreAuthorize("@algaSecurity.canEditingRestaurants()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedEdit {
            
        }

        @PreAuthorize("@algaSecurity.canManageRestaurants(#restaurantId)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedUserManager {
            
        }

    }

    public @interface Orders {

        @PreAuthorize("@algaSecurity.canConsultingAllOrders(#orderFilter.customerId, #orderFilter.restaurantId)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedConsultAll {
            
        }

        @PreAuthorize("@algaSecurity.canConsultingOrders()")
        @PostAuthorize("@algaSecurity.canConsultingAllOrders(returnObject.customer.id, returnObject.restaurant.id)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedConsultUnique {
            
        }

        @PreAuthorize("isAuthenticated() and hasAuthority('SCOPE_WRITE')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedEdit {
            
        }

        @PreAuthorize("@algaSecurity.canManageOrders(#code)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedManageOrder {
            
        }

    }

    public @interface PaymentForms {

        @PreAuthorize("@algaSecurity.canConsultingPaymentForms()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedConsult {
            
        }

        @PreAuthorize("@algaSecurity.canEditingPaymentForms()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedEdit {
            
        }

    }

    public @interface Cities {

        @PreAuthorize("@algaSecurity.canConsultingCities()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedConsult {
            
        }

        @PreAuthorize("isAuthenticated() and hasAuthority('EDITAR_CIDADES') and hasAuthority('SCOPE_WRITE')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedEdit {
            
        }

    }

    public @interface States {

        @PreAuthorize("@algaSecurity.canConsultingStates()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedConsult {
            
        }

        @PreAuthorize("isAuthenticated() and hasAuthority('EDITAR_ESTADOS') and hasAuthority('SCOPE_WRITE')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface AllowedEdit {
            
        }

    }

    public @interface UsersGroupsPermissions {

        @PreAuthorize("@algaSecurity.canConsultingUsersGroupsPermissions()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface allowedConsult {
            
        }

        @PreAuthorize("@algaSecurity.canEditingUsersGroupsPermissions()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface allowedEdit {
            
        }

        @PreAuthorize("isAuthenticated() "
                + "and (hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or @algaSecurity.athenticatedUserIsEquals(#userId)) "
                + "and hasAuthority('SCOPE_WRITE')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface allowedUpdateUser {
            
        }

        @PreAuthorize("isAuthenticated() " 
                + "and @algaSecurity.athenticatedUserIsEquals(#userId) "
                + "and hasAuthority('SCOPE_WRITE')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface allowedUpdatePassword {
            
        }

    }

    public @interface Statistics {

        @PreAuthorize("@algaSecurity.canConsultingStatistics()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface allowedGenerateReports {
            
        }

    }

}
