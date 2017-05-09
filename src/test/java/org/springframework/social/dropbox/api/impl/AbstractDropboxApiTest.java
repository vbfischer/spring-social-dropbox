package org.springframework.social.dropbox.api.impl;

import org.junit.Before;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.web.client.MockRestServiceServer;

import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * @author Bryce Fischer
 * @author Robert Drysdale
 */
public abstract class AbstractDropboxApiTest {
    protected DropboxTemplate dropbox;
    protected MockRestServiceServer mockServer;

    @Before
    public void setUp() throws Exception {
        dropbox = new DropboxTemplate("API_KEY", "API_SECRET", "ACCESS_TOKEN", "ACCESS_TOKEN_SECRET", false);
        mockServer = MockRestServiceServer.createServer(dropbox.getRestTemplate());
    }

	protected Resource jsonResource(String filename) {
		return new ClassPathResource(filename + ".json");
	}
	
	protected Date fromDropboxDate(String date) {
		try {
			return new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z").parse(date);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
