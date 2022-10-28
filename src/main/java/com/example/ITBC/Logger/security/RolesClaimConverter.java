package com.example.ITBC.Logger.security;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

/**
 * Custom converter for converting <pre>roles</pre> claim into {@link org.springframework.security.core.GrantedAuthority}.
 *
 * @author Stefan Golubovic
 */
@RequiredArgsConstructor
public class RolesClaimConverter implements Converter<Jwt, AbstractAuthenticationToken> {

	private static final String ROLE_AUTHORITY_PREFIX = "ROLE_";

	private final JwtGrantedAuthoritiesConverter wrappedConverter;

	@Override
	public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
		var grantedAuthorities = new ArrayList<>(wrappedConverter.convert(jwt));
		//noinspection unchecked
		var roles = (List<String>) jwt.getClaims().get("roles");
		if (roles != null) {
			for (String role : roles) {
				grantedAuthorities.add(new SimpleGrantedAuthority(ROLE_AUTHORITY_PREFIX + role));
			}
		}

		return new JwtAuthenticationToken(jwt, grantedAuthorities);
	}
}