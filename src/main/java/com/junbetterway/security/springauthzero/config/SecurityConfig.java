package com.junbetterway.security.springauthzero.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import com.junbetterway.security.springauthzero.validator.JwtAudienceValidator;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Value("${auth0.audience}")
    private String audience;
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        	.mvcMatchers("/api/account/public").permitAll()
	        .mvcMatchers("/api/account/private").authenticated()
	        .mvcMatchers("/api/account/private-scoped").hasAuthority("SCOPE_read:accounts")
	        .and().cors()
	        .and().oauth2ResourceServer().jwt();
    }
    
    @Bean
    public JwtDecoder jwtDecoder(final OAuth2ResourceServerProperties properties) {
    	
        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromOidcIssuerLocation(
        		properties.getJwt().getIssuerUri());

        OAuth2TokenValidator<Jwt> audienceValidator = new JwtAudienceValidator(audience);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(properties.getJwt().getIssuerUri());
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

        jwtDecoder.setJwtValidator(withAudience);

        return jwtDecoder;
        
    }
    
}
