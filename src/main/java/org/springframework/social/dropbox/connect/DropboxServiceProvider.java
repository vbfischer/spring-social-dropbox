package org.springframework.social.dropbox.connect;

import org.springframework.social.dropbox.api.DropboxApi;
import org.springframework.social.dropbox.api.impl.DropboxTemplate;
import org.springframework.social.oauth1.AbstractOAuth1ServiceProvider;
import org.springframework.social.oauth1.OAuth1Template;
import org.springframework.social.oauth1.OAuth1Version;

/**
 * User: Bryce Fischer
 * Date: 5/17/11
 * Time: 11:29 AM
 */
public class DropboxServiceProvider extends AbstractOAuth1ServiceProvider<DropboxApi> {

    public DropboxServiceProvider(String consumerKey, String consumerSecret) {
        super(consumerKey,  consumerSecret, new OAuth1Template(consumerKey, consumerSecret,
                "https://api.dropbox.com/1/oauth/request_token",
                "https://www.dropbox.com/1/oauth/authorize",
                "https://api.dropbox.com/1/oauth/access_token",
                OAuth1Version.CORE_10));
    }

    @Override
    public DropboxApi getApi(String accessToken, String secret) {
        return new DropboxTemplate(getConsumerKey(), getConsumerSecret(), accessToken, secret);
    }
}
