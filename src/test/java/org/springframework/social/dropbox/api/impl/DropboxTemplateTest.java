package org.springframework.social.dropbox.api.impl;

import org.junit.Test;
import org.springframework.social.dropbox.api.DropboxUserProfile;

import java.math.BigInteger;

import static junit.framework.Assert.assertEquals;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.social.test.client.RequestMatchers.method;
import static org.springframework.social.test.client.RequestMatchers.requestTo;
import static org.springframework.social.test.client.ResponseCreators.withResponse;

/**
 * User: Bryce Fischer
 * Date: 5/17/11
 * Time: 9:41 AM
 */
public class DropboxTemplateTest extends AbstractDropboxApiTest {
    @Test
    public void getUserProfileValid() throws Exception {
        mockServer
                .expect(requestTo("https://api.dropbox.com/0/account/info"))
                .andExpect(method(GET))
                .andRespond(withResponse(jsonResource("/profileValid"), responseHeaders));

        DropboxUserProfile profile = dropbox.getUserProfile();
        assertEquals("US", profile.getCountry());
        assertEquals("Some User", profile.getDisplayName());
        assertEquals("some email", profile.getEmail());
        assertEquals(BigInteger.valueOf(10), profile.getUid());
        assertEquals("referralLink", profile.getReferralLink());
        assertEquals(BigInteger.valueOf(108179488768L), profile.getQuota());
        assertEquals(BigInteger.valueOf(44962990383L), profile.getSharedQuota());
        assertEquals(BigInteger.valueOf(46970996076L), profile.getNormalQuota());
    }
}
