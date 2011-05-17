package org.springframework.social.dropbox.connect;

import org.springframework.social.connect.support.OAuth1ConnectionFactory;
import org.springframework.social.dropbox.api.DropboxApi;
import org.springframework.social.oauth1.OAuthToken;

/**
 * User: Bryce Fischer
 * Date: 5/17/11
 * Time: 11:38 AM
 */
public class DropboxConnectionFactory extends OAuth1ConnectionFactory<DropboxApi> {
    public DropboxConnectionFactory(String consumerKey, String consumerSecret) {
        super("dropbox", new DropboxServiceProvider(consumerKey, consumerSecret), new DropboxAdapter());
    }

    @Override
    protected String extractProviderUserId(OAuthToken accessToken) {
        return accessToken.getValue();
    }
}
