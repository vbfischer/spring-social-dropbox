package org.springframework.social.dropbox.connect;

import org.springframework.social.dropbox.api.Dropbox;
import org.springframework.social.dropbox.api.impl.DropboxTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * @author Bryce Fischer
 * @author Robert Drysdale
 */
public class DropboxServiceProvider extends AbstractOAuth2ServiceProvider<Dropbox> {
	private final boolean appFolder;
	
    public DropboxServiceProvider(String appKey, String appSecret, boolean appFolder) {
        super(getOAuth2Template(appKey, appSecret));
        this.appFolder = appFolder;
    }
	
	private static OAuth2Template getOAuth2Template(String appKey, String clientSecret) {
		OAuth2Template oauth2 = new OAuth2Template(appKey, clientSecret, 
				"https://www.dropbox.com/oauth2/authorize", 
				"https://api.dropboxapi.com/oauth2/token");
		return oauth2;
	}

    @Override
    public Dropbox getApi(String accessToken) {
        return new DropboxTemplate(accessToken, appFolder);
    }
}
