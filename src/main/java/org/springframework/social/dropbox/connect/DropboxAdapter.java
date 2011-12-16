package org.springframework.social.dropbox.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.dropbox.api.Dropbox;
import org.springframework.social.dropbox.api.DropboxUserProfile;
import org.springframework.web.client.HttpClientErrorException;

/**
 * User: Bryce Fischer
 * Date: 5/17/11
 * Time: 11:33 AM
 */
public class DropboxAdapter implements ApiAdapter<Dropbox> {
    @Override
    public boolean test(Dropbox dropboxApi) {
        try {
            dropboxApi.getUserProfile();
            return true;
        } catch (HttpClientErrorException e) {
            return false;
        }
    }

    @Override
    public void setConnectionValues(Dropbox dropboxApi, ConnectionValues values) {
        DropboxUserProfile profile = dropboxApi.getUserProfile();
        values.setProviderUserId(profile.getUid().toString());
        values.setDisplayName(profile.getDisplayName());
        values.setProfileUrl(profile.getReferralLink());
    }

    @Override
    public UserProfile fetchUserProfile(Dropbox dropboxApi) {
        DropboxUserProfile profile = dropboxApi.getUserProfile();
        return new UserProfileBuilder().setName(profile.getDisplayName()).setUsername(profile.getEmail()).setEmail(profile.getEmail()).build();
    }

    @Override
    public void updateStatus(Dropbox dropboxApi, String s) {
        // Not Supported
    }
}
