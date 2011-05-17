package org.springframework.social.dropbox.api.impl;

import org.springframework.social.dropbox.api.DropboxApi;
import org.springframework.social.dropbox.api.DropboxItemMetadata;
import org.springframework.social.dropbox.api.DropboxUserProfile;
import org.springframework.social.oauth1.AbstractOAuth1ApiTemplate;

import java.math.BigInteger;
import java.util.List;

/**
 * User: Bryce Fischer
 * Date: 5/17/11
 * Time: 9:22 AM
 */
public class DropboxTemplate extends AbstractOAuth1ApiTemplate implements DropboxApi {
    public DropboxTemplate(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
        super(consumerKey, consumerSecret, accessToken, accessTokenSecret);

    }

    public DropboxTemplate() {
        super();
    }

    public DropboxUserProfile getUserProfile() {
        return getRestTemplate().getForObject("https://api.dropbox.com/0/account/info", DropboxUserProfile.class);
    }

    public List<DropboxItemMetadata> getItemMetadata(String path, boolean list, BigInteger maxFiles) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
