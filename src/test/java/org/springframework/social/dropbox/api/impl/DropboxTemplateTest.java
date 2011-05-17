package org.springframework.social.dropbox.api.impl;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.social.dropbox.api.DropboxUserProfile;
import org.springframework.social.test.client.MockRestServiceServer;

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
public class DropboxTemplateTest {
    private DropboxTemplate dropbox;
    private MockRestServiceServer mockServer;
    private HttpHeaders responseHeaders;

    @Before
    public void setUp() throws Exception {
        dropbox = new DropboxTemplate("API_KEY", "API_SECRET", "ACCESS_TOKEN", "ACCESS_TOKEN_SECRET");
        mockServer = MockRestServiceServer.createServer(dropbox.getRestTemplate());
        responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void getUserProfileValid() throws Exception {
        final ClassPathResource classPathResource = new ClassPathResource("profile-valid.json", getClass());

        mockServer.expect(requestTo("https://api.dropbox.com/0/account/info")).andExpect(method(GET))
                .andRespond(withResponse(classPathResource, responseHeaders));

        DropboxUserProfile profile = dropbox.getUserProfile();
        assertEquals("USA", profile.getCountry());
        assertEquals("Test Q User", profile.getDisplayName());
        assertEquals(37378890, profile.getSharedQuota());
        assertEquals(new BigInteger("62277025792"), profile.getQuota());
        assertEquals(263758550, profile.getNormalQuota());
    }
}
