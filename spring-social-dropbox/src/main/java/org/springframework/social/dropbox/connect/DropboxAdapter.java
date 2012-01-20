package main.java.org.springframework.social.dropbox.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import main.java.org.springframework.social.dropbox.api.DropboxApi;
import main.java.org.springframework.social.dropbox.api.DropboxUserProfile;
import org.springframework.web.client.HttpClientErrorException;

/**
 * User: Bryce Fischer
 * Date: 5/17/11
 * Time: 11:33 AM
 */
public class DropboxAdapter implements ApiAdapter<DropboxApi> {
    @Override
    public boolean test(DropboxApi dropboxApi) {
        try {
            dropboxApi.getUserProfile();
            return true;
        } catch (HttpClientErrorException e) {
            return false;
        }
    }

    @Override
    public void setConnectionValues(DropboxApi dropboxApi, ConnectionValues values) {
        DropboxUserProfile profile = dropboxApi.getUserProfile();
        values.setProviderUserId(profile.getUid().toString());
        values.setDisplayName(profile.getDisplayName());
        values.setProfileUrl(profile.getReferralLink());
    }

    @Override
    public UserProfile fetchUserProfile(DropboxApi dropboxApi) {
        DropboxUserProfile profile = dropboxApi.getUserProfile();
        return new UserProfileBuilder().setName(profile.getDisplayName()).setUsername(profile.getEmail()).setEmail(profile.getEmail()).build();
    }

    @Override
    public void updateStatus(DropboxApi dropboxApi, String s) {
        // Not Supported
    }
}
