package com.algaworks.algafood.core.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

public @interface Security {

	public @interface Kitchens {

		@PreAuthorize("isAuthenticated() and hasAuthority('SCOPE_READ')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface AllowedConsult {
		}

		@PreAuthorize("hasAuthority('EDITAR_COZINHAS') and hasAuthority('SCOPE_WRITE')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface AllowedEdit {
		}

	}

	public @interface Restaurants {

		@PreAuthorize("isAuthenticated() and hasAuthority('SCOPE_READ')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface AllowedConsult {
		}

		@PreAuthorize("hasAuthority('EDITAR_RESTAURANTES') and hasAuthority('SCOPE_WRITE')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface AllowedEdit {
		}

	}

}
