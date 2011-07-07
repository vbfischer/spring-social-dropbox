package org.springframework.social.dropbox.connect;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.dropbox.api.DropboxApi;
import org.springframework.social.dropbox.api.DropboxUserProfile;

import java.math.BigInteger;

import static junit.framework.Assert.assertEquals;

/**
 * User: Bryce Fischer
 * Date: 5/17/11
 * Time: 11:40 AM
 */
public class DropboxAdapterTest {
    private DropboxAdapter adapter = new DropboxAdapter();
    private DropboxApi dropboxApi = Mockito.mock(DropboxApi.class);

    @Test
    public void fetchProfile() throws Exception {
        final String country = "USA";
        final String displayName = "DisplayName";
        final BigInteger uid = BigInteger.valueOf(1);
        final BigInteger sharedQuota = BigInteger.valueOf(123);
        final BigInteger quota = new BigInteger("62277025792");
        final BigInteger normalQuota = BigInteger.valueOf(323);
        String email = "emailaddress";
        String referralLink = "referralLink";

        Mockito.when(dropboxApi.getUserProfile())
                .thenReturn(new DropboxUserProfile(uid, displayName, email, country, referralLink, sharedQuota, quota, normalQuota));

        UserProfile profile = adapter.fetchUserProfile(dropboxApi);

        assertEquals(displayName, profile.getName());
    }
}
