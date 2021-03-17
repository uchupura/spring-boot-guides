package com.guide.oauth2.resource.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

@WithToken
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockOAuth2User.WithMockOAuth2UserSecurityContextFactory.class)
public @interface WithMockOAuth2User {
    WithMockOAuth2Client client() default @WithMockOAuth2Client();

    WithMockUser user() default @WithMockUser();

    /**
     * Return the contained {@link AttachClaims} annotations.
     *
     * @return the claims
     */
    AttachClaims claims() default @AttachClaims({});

    class WithMockOAuth2UserSecurityContextFactory implements WithSecurityContextFactory<WithMockOAuth2User> {

        @Override
        public SecurityContext createSecurityContext(final WithMockOAuth2User annotation) {
            final SecurityContext ctx = SecurityContextHolder.createEmptyContext();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("uchupura", "N/A");
            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(
                    WithMockOAuth2Client.WithMockOAuth2ClientSecurityContextFactory.getOAuth2Request(annotation.client()),
                    MockUserUtils.getAuthentication(annotation.user()));
            Map<String, Object> claims = ClaimUtils.extractClaims(annotation.claims());
            if (!claims.isEmpty()) {
                oAuth2Authentication.setDetails(claims);
            }
            ctx.setAuthentication(oAuth2Authentication);
            SecurityContextHolder.setContext(ctx);
            return ctx;
        }

    }
}
