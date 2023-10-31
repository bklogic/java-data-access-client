package net.backlogic.persistence.client.auth;

import java.util.Properties;

/**
 * Supply JWT token to data access client. Implementation class must include a no-arg constructor.
 */
public interface JwtProvider {
    /**
     * Return a JWT token.
     * @return
     */
    String get();

    /**
     * Refreshed JWT cache so that a new token will be returned on next get().
     */
    void refresh();

    /**
     * Set JWT provider properties.
     * @param properties the properties needed by the JWT provider.
     */
    void set(Properties properties);
}
