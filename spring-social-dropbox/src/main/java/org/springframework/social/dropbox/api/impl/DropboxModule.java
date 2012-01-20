package main.java.org.springframework.social.dropbox.api.impl;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.module.SimpleModule;
import main.java.org.springframework.social.dropbox.api.DropboxUserProfile;

/**
 * User: Bryce Fischer
 * Date: 5/17/11
 * Time: 11:06 AM
 */
public class DropboxModule extends SimpleModule {
    public DropboxModule() {
        super("DropboxModule", new Version(1, 0, 0, null));
    }

    @Override
    public void setupModule(SetupContext context) {
        context.setMixInAnnotations(DropboxUserProfile.class, DropboxUserProfileMixin.class);
    }
}
