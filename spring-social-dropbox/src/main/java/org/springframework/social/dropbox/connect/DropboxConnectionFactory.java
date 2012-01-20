package main.java.org.springframework.social.dropbox.connect;

import org.springframework.social.connect.support.OAuth1ConnectionFactory;
import main.java.org.springframework.social.dropbox.api.DropboxApi;

/**
 * User: Bryce Fischer
 * Date: 5/17/11
 * Time: 11:38 AM
 */
public class DropboxConnectionFactory extends OAuth1ConnectionFactory<DropboxApi> {
    public DropboxConnectionFactory(String consumerKey, String consumerSecret) {
        super("dropbox", new DropboxServiceProvider(consumerKey, consumerSecret), new DropboxAdapter());
    }
}
