package net.backlogic.persistence.client.auth;

import java.util.Properties;

/**
 * <p>The default implementation of JwtProvider based on DefaultJwtCredential.</p>
 */
public class SimpleJwtProvider implements JwtProvider {
    private static final String JWT = "jwt";
    private String jwt = "";
    @Override
    public String get() {
        return null;
    }

    @Override
    public void refresh() {
    }

    @Override
    public void set(Properties properties) {
        this.jwt = properties.getProperty(JWT);
    }

    public JwtProvider setJwt(String jwt) {
        this.jwt = jwt;
        return this;
    }
}
