package com.junbetterway.security.springauthzero.validator;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

import lombok.RequiredArgsConstructor;

/**
 * 
 * This validates the JWT if intended for the registered 
 * API by checking the audience claim within the JWT
 * 
 * @author junbetterway
 *
 */
@RequiredArgsConstructor
public class JwtAudienceValidator implements OAuth2TokenValidator<Jwt> {

	private final String audience;
	
	@Override
	public OAuth2TokenValidatorResult validate(final Jwt jwt) {
		
        OAuth2Error error = new OAuth2Error("invalid_token", "The required audience is missing!", null);
        
        if (jwt.getAudience().contains(audience)) {
            return OAuth2TokenValidatorResult.success();
        }
        
        return OAuth2TokenValidatorResult.failure(error);
        
	}

}
